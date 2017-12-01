package com.ptsecurity.libprotection.injections.languages.url

import com.ptsecurity.libprotection.injections.languages.RegexLanguageProvider
import com.ptsecurity.libprotection.injections.languages.RegexTokenDefinition
import com.ptsecurity.libprotection.injections.languages.Token
import com.ptsecurity.libprotection.injections.languages.TokenType
import java.net.URLEncoder

object Url : RegexLanguageProvider() {
    override val name = "Url"
    override val errorTokenType = UrlTokenType.Error
    override val tokenDefinitions = arrayListOf(
            RegexTokenDefinition("""[^:/?#]+:""", UrlTokenType.Scheme),
            RegexTokenDefinition("""//[^/?#]*""", UrlTokenType.AuthorityCtx),
            RegexTokenDefinition("""[^?#]*""", UrlTokenType.PathCtx),
            RegexTokenDefinition("""\?[^#]*""", UrlTokenType.QueryCtx),
            RegexTokenDefinition("""#.*""", UrlTokenType.Fragment)
    )

    override fun tokenize(text: String, offset: Int): Collection<Token> {
        val res = arrayListOf<Token>()
        for (token in super.tokenize(text, offset)) {
            var tokenText = token.text
            var lowerBound = token.range.lowerBound
            when (token.type as UrlTokenType) {
                UrlTokenType.AuthorityCtx -> {
                    tokenText = tokenText.substring(2, tokenText.length)
                    lowerBound += 2
                    res.addAll(splitToken(tokenText, lowerBound, ":@", UrlTokenType.AuthorityEntry))
                }
                UrlTokenType.PathCtx -> res.addAll(splitToken(tokenText, lowerBound, "\\/", UrlTokenType.PathEntry))
                UrlTokenType.QueryCtx -> res.addAll(splitToken(tokenText, lowerBound, "&=", UrlTokenType.QueryEntry))
                else -> res.add(token)
            }
        }
        return res
    }

    override fun trySanitize(text: String, context: Token): String? {
        if (context.languageProvider is Url) return tryUrlEncode(text, context.type as UrlTokenType) ?: super.trySanitize(text, context)
        else throw IllegalArgumentException("Unsupported URL island: $context")
    }

    override fun isSafeToken(type: TokenType, text: String) = when (type as UrlTokenType) {
        UrlTokenType.QueryEntry, UrlTokenType.Fragment -> true
        UrlTokenType.PathEntry -> !text.contains("..")
        else -> false
    }

    private fun splitToken(text: String, lb: Int, splitChars: String, tokenType: UrlTokenType): Collection<Token> {
        if (text.isEmpty()) return emptyList()
        val res = arrayListOf<Token>()
        var lowerBound = lb
        var sb = StringBuilder()
        for (c in text) {
            if (c in splitChars) {
                if (sb.isNotEmpty()) {
                    val tokenText = sb.toString()
                    sb = StringBuilder()
                    val upperBound = lowerBound + tokenText.length
                    res.add(createToken(tokenType, lowerBound, upperBound, tokenText))
                    lowerBound = upperBound + 1
                } else {
                    lowerBound++
                }
            } else {
                sb.append(c)
            }
        }
        if (sb.isNotEmpty()) {
            val lastTokenText = sb.toString()
            res.add(createToken(tokenType, lowerBound, lowerBound + lastTokenText.length, lastTokenText))
        }
        return res
    }

    private fun tryUrlEncode(text: String, urlTokenType: UrlTokenType): String? = when (urlTokenType) {
        UrlTokenType.PathEntry -> {
            val fragments = text.split("/").toMutableList()
            for (i in 0 until fragments.size) {
                if (fragments[i].isNotEmpty()) fragments[i] = URLEncoder.encode(fragments[i], "UTF-8")
            }
            fragments.reduce { a, b -> "$a/$b" }
        }
        UrlTokenType.QueryEntry, UrlTokenType.Fragment -> URLEncoder.encode(text, "UTF-8")
        else -> null
    }

}