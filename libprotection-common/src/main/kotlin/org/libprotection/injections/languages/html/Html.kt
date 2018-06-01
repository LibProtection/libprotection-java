package org.libprotection.injections.languages.html

import org.libprotection.injections.languages.AntlrLanguageProvider
import org.libprotection.injections.languages.IslandDto
import org.libprotection.injections.languages.Token
import org.libprotection.injections.languages.javascript.JavaScript
import org.libprotection.injections.languages.url.Url
import org.libprotection.injections.utils.*
import org.libprotection.injections.languages.TokenType
import antlr4HTMLLexer
import antlr4InputStream
import encodeHtmlAttribute
import encodeHtmlText

object Html : AntlrLanguageProvider() {

    private enum class HtmlTokenizerState {
        Insignificant,
        EventName,
        EventEqualSign,
        EventValue,
        ResourceName,
        ResourceEqualSign,
        ResourceValue
    }

    private val htmlUrlAttributes = setOf("href", "src", "manifest", "poster", "code", "codebase", "data", "xlink:href", "xml:base", "from", "to", "formaction", "action")

    override fun convertAntlrTokenType(antlrTokenType: Int) = HtmlTokenType.fromInt(antlrTokenType)

    override fun createLexer(text: String) = antlr4HTMLLexer(antlr4InputStream(text))

    override fun tokenize(text: String, offset: Int): Iterable<Token> {

        val res = mutableListOf<Token>()

        var state = HtmlTokenizerState.Insignificant
        var insideScriptTag = false

        for (token in super.tokenize(text, offset)) {
            if (token.languageProvider === this) {
                val htmlTokenType = token.type as HtmlTokenType

                state = when(state){
                    HtmlTokenizerState.EventName ->
                        if(htmlTokenType == HtmlTokenType.TagEquals)
                            HtmlTokenizerState.EventEqualSign else HtmlTokenizerState.Insignificant
                    HtmlTokenizerState.EventEqualSign ->
                        if(htmlTokenType == HtmlTokenType.AttributeValue)
                            HtmlTokenizerState.EventValue else HtmlTokenizerState.Insignificant
                    HtmlTokenizerState.ResourceName ->
                        if(htmlTokenType == HtmlTokenType.TagEquals)
                            HtmlTokenizerState.ResourceEqualSign else HtmlTokenizerState.Insignificant
                    HtmlTokenizerState.ResourceEqualSign ->
                        if(htmlTokenType == HtmlTokenType.AttributeValue)
                            HtmlTokenizerState.ResourceValue else HtmlTokenizerState.Insignificant
                    else -> {
                            when (htmlTokenType) {
                                HtmlTokenType.AttributeName -> {
                                    when {
                                        token.text.startsWith("on", true) -> HtmlTokenizerState.EventName
                                        htmlUrlAttributes.contains(token.text.toLowerCase()) -> HtmlTokenizerState.ResourceName
                                        else -> HtmlTokenizerState.Insignificant
                                    }
                                }
                                HtmlTokenType.TagOpen -> {
                                    if ("<script".equals(token.text, true)) {
                                        insideScriptTag = true
                                    }
                                    else if ("</script".equals(token.text, true)) {
                                        insideScriptTag = false
                                    }
                                    HtmlTokenizerState.Insignificant
                                }
                                else -> HtmlTokenizerState.Insignificant
                            }
                        }
                }

                val contextChangedRes = isContextChanged(token, state, insideScriptTag)

                if(contextChangedRes is Some){
                    val islandData = contextChangedRes.value
                    val islandTokens = islandData.languageProvider.tokenize(islandData.text, islandData.offset)
                    for (islandToken in islandTokens)
                    {
                        res.add(islandToken)
                    }
                }
                else res.add(token)

            }
        }
        return res
    }


    private fun isContextChanged(htmlToken : Token, context : HtmlTokenizerState, insideScriptTag : Boolean) : MayBe<IslandDto>
    {
        when (context)
        {
            HtmlTokenizerState.EventValue -> {
                val (islandText, offset) = trimQuotes(htmlToken)
                if (!islandText.isEmpty()){
                    return Some(IslandDto(JavaScript, offset, islandText))
                }
            }

            HtmlTokenizerState.ResourceValue -> {
                val (islandText, offset) = trimQuotes(htmlToken)
                if (!islandText.isEmpty())
                {
                    return Some(IslandDto(Url, offset, islandText))
                }
            }
            else -> {
                val htmlTokenType = htmlToken.type as HtmlTokenType
                if (insideScriptTag) {
                    when(htmlTokenType){
                        HtmlTokenType.HtmlText -> {
                            return Some(IslandDto(JavaScript, htmlToken.range.lowerBound, htmlToken.text))
                        }
                        else -> {}
                    }
                }
            }
        }

        return None
    }

    /**
     * Trimming the string
     * @param token is token
     * @return pair of trimmed string and offset
     */
    private fun trimQuotes(token : Token) : Pair<String, Int>
    {
        var tokenText = token.text
        var offset = token.range.lowerBound

        if (tokenText.isEmpty()) return Pair("", 0)

        if (tokenText[0] == '\'' && tokenText[tokenText.length - 1] == '\'' ||
            tokenText[0] == '"' && tokenText[tokenText.length - 1] == '"')
        {
            if (tokenText.length > 2)
            {
                tokenText = tokenText.substring(1, tokenText.length - 1)
                offset++
            }
        }
        return Pair(tokenText, offset)
    }


    override fun trySanitize(text: String, context: Token): MayBe<String> {
        return when (context.languageProvider)
        {
            Html -> Some(htmlEncode(text, context.type as HtmlTokenType))
            Url -> {
                val urlSanitized = Url.trySanitize(text, context)
                return if (urlSanitized is Some) Some(htmlEncode(urlSanitized.value, HtmlTokenType.AttributeValue)) else None
            }
            JavaScript-> {
                val ecmaScriptSanitized = JavaScript.trySanitize(text, context)
                return if (ecmaScriptSanitized is Some) Some(htmlEncode(ecmaScriptSanitized.value, HtmlTokenType.HtmlText)) else None
            }
            else -> throw IllegalArgumentException("Unsupported HTML island: $context")
        }
    }

    override fun isTrivial(type: TokenType, text: String): Boolean {
        return when (type as HtmlTokenType) {
            HtmlTokenType.HtmlComment,
            HtmlTokenType.HtmlConditionalComment,
            HtmlTokenType.HtmlText,
            HtmlTokenType.Cdata,
            HtmlTokenType.AttributeWhiteSpace,
            HtmlTokenType.AttributeValue -> return true
            else -> false
        }
    }

    private fun htmlEncode(text: String, tokenType: HtmlTokenType) : String {
        return when (tokenType) {
            HtmlTokenType.AttributeValue -> encodeHtmlAttribute(text)
            else -> encodeHtmlText(text)
        }
    }
}