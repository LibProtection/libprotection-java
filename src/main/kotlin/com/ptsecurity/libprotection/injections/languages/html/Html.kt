package com.ptsecurity.libprotection.injections.languages.html

import HTMLLexer
import com.ptsecurity.libprotection.injections.languages.AntlrLanguageProvider
import com.ptsecurity.libprotection.injections.languages.IslandDto
import com.ptsecurity.libprotection.injections.languages.Token
import com.ptsecurity.libprotection.injections.languages.TokenType
import com.ptsecurity.libprotection.injections.languages.javascript.JavaScript
import com.ptsecurity.libprotection.injections.languages.url.Url
import org.antlr.v4.runtime.ANTLRInputStream
import org.owasp.encoder.Encode

object Html : AntlrLanguageProvider() {
    override val name = "Html"
    private enum class HtmlTokenizerContext {
        Insignificant,
        EventName,
        EventEqualSign,
        EventValue,
        ResourceName,
        ResourceEqualSign,
        ResourceValue
    }

    override fun convertAntlrTokenType(antlrTokenType: Int) =
            HtmlTokenType.fromInt(antlrTokenType) ?: throw IllegalArgumentException("Unexpected html token type $antlrTokenType")

    override fun createLexer(text: String) = HTMLLexer(ANTLRInputStream(text))

    override fun tokenize(text: String, offset: Int): Collection<Token> {
        val res = arrayListOf<Token>()
        var context = HtmlTokenizerContext.Insignificant
        for (token in super.tokenize(text, offset)) {
            if (token.languageProvider is Html) {
                val htmlTokenType = token.type as HtmlTokenType
                context = when (context) {
                    HtmlTokenizerContext.EventName ->
                        if (htmlTokenType == HtmlTokenType.TagEquals) HtmlTokenizerContext.EventEqualSign
                        else HtmlTokenizerContext.Insignificant
                    HtmlTokenizerContext.EventEqualSign ->
                        if (htmlTokenType == HtmlTokenType.AttvalueValue) HtmlTokenizerContext.EventValue
                        else HtmlTokenizerContext.Insignificant
                    HtmlTokenizerContext.ResourceName ->
                        if (htmlTokenType == HtmlTokenType.TagEquals) HtmlTokenizerContext.ResourceEqualSign
                        else HtmlTokenizerContext.Insignificant
                    HtmlTokenizerContext.ResourceEqualSign ->
                        if (htmlTokenType == HtmlTokenType.AttvalueValue) HtmlTokenizerContext.ResourceValue
                        else HtmlTokenizerContext.Insignificant
                    else ->
                        if (htmlTokenType == HtmlTokenType.TagName) {
                            val htmlLoweredText = token.text.toLowerCase()
                            when {
                                htmlLoweredText.startsWith("on") -> HtmlTokenizerContext.EventName
                                htmlLoweredText in arrayListOf("href", "src", "manifest", "poster", "code", "codebase", "data") -> HtmlTokenizerContext.ResourceName
                                else -> HtmlTokenizerContext.Insignificant
                            }
                        } else HtmlTokenizerContext.Insignificant
                }
                val newIsland = changedContext(token, context)
                if (newIsland != null) res.addAll(newIsland.languageProvider.tokenize(newIsland.text, newIsland.offset))
                else res.add(token)
            }
        }
        return res
    }

    private fun changedContext(htmlToken: Token, context: HtmlTokenizerContext) = when (context) {
        HtmlTokenizerContext.EventValue -> {
            val (islandText, offset) = trimQuotes(htmlToken)
            if (islandText.isNotEmpty()) IslandDto(JavaScript, offset, islandText)
            else null
        }
        HtmlTokenizerContext.ResourceValue -> {
            val (islandText, offset) = trimQuotes(htmlToken)
            if (islandText.isNotEmpty()) IslandDto(Url, offset, islandText)
            else null
        }
        else -> {
            val htmlTokenType = htmlToken.type
            if (htmlTokenType == HtmlTokenType.ScriptBody || htmlTokenType == HtmlTokenType.ScriptShortBody) {
                // `</script>` or `</>` case
                // Remove trailing tag
                var closingTagIndex = htmlToken.text.length - 2
                while (htmlToken.text.substring(closingTagIndex, 2) != "</" && closingTagIndex >= 0) closingTagIndex--
                val islandText = if (closingTagIndex >= 0) htmlToken.text.substring(0, closingTagIndex)
                else htmlToken.text
                IslandDto(JavaScript, htmlToken.range.lowerBound, islandText)
            } else null
        }
    }

    private fun trimQuotes(token: Token): Pair<String, Int> {
        val tokenText = token.text
        val offset = token.range.lowerBound
        if (tokenText.isEmpty()) return Pair("", offset)
        if (tokenText.first() == '\'' && tokenText.last() == '\'' ||
                tokenText.first() == '"' && tokenText.last() == '"') {
            if (tokenText.length > 2) return Pair(tokenText.substring(1, tokenText.length - 2), offset + 1)
        }
        return Pair(tokenText, offset)
    }

    override fun trySanitize(text: String, context: Token) = when (context.languageProvider) {
        Html -> htmlEncode(text, context.type as HtmlTokenType)
        Url -> {
            val sanitized = Url.trySanitize(text, context)
            if (sanitized != null) htmlEncode(sanitized, HtmlTokenType.Attribute)
            else super.trySanitize(text, context)
        }
        JavaScript -> {
            val sanitized = JavaScript.trySanitize(text, context)
            if (sanitized != null) htmlEncode(sanitized, HtmlTokenType.HtmlText)
            else super.trySanitize(text, context)
        }
        else -> super.trySanitize(text, context)
    }

    override fun isSafeToken(type: TokenType, text: String) = when (type as HtmlTokenType) {
        HtmlTokenType.HtmlComment,
        HtmlTokenType.HtmlConditionalComment,
        HtmlTokenType.HtmlText,
        HtmlTokenType.TagName,
        HtmlTokenType.Attribute -> true
        else -> false
    }

    private fun htmlEncode(text: String, tokenType: HtmlTokenType) = when (tokenType) {
        HtmlTokenType.AttvalueValue,
        HtmlTokenType.Attribute,
        HtmlTokenType.ErrorAttvalue -> Encode.forHtmlAttribute(text)
        else -> Encode.forHtml(text)
    }
}