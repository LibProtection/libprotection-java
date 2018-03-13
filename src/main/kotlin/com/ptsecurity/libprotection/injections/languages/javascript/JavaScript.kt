package com.ptsecurity.libprotection.injections.languages.javascript

import com.ptsecurity.libprotection.injections.languages.AntlrLanguageProvider
import com.ptsecurity.libprotection.injections.languages.TokenType
import JavaScriptLexer
import com.ptsecurity.libprotection.injections.languages.Token
import org.antlr.v4.runtime.ANTLRInputStream
import org.apache.commons.lang3.StringEscapeUtils

object JavaScript : AntlrLanguageProvider() {
    override val name = "JavaScript"
    override fun trySanitize(text: String, context: Token) =
            if (context.languageProvider is JavaScript) tryJavaScriptEncode(text, context.type as JavaScriptTokenType)
            else throw IllegalArgumentException("Unsupported JavaScript island: $context")

    override fun convertAntlrTokenType(antlrTokenType: Int) =
            JavaScriptTokenType.fromInt(antlrTokenType) ?: throw IllegalArgumentException("Unexpected java script token type $antlrTokenType")

    override fun createLexer(text: String) = JavaScriptLexer(ANTLRInputStream(text)).apply {
        this.setUseStrictDefault(false)
    }

    override fun isSafeToken(type: TokenType, text: String) = when (type as JavaScriptTokenType) {
        JavaScriptTokenType.RegularExpressionLiteral,
        JavaScriptTokenType.NullLiteral,
        JavaScriptTokenType.BooleanLiteral,
        JavaScriptTokenType.DecimalLiteral,
        JavaScriptTokenType.HexIntegerLiteral,
        JavaScriptTokenType.OctalIntegerLiteral,
        JavaScriptTokenType.StringLiteral -> true
        else -> false
    }

    private fun tryJavaScriptEncode(text: String, type: JavaScriptTokenType) = when (type) {
        JavaScriptTokenType.RegularExpressionLiteral -> StringEscapeUtils.escapeEcmaScript(text).replace("/", "\\/")
        JavaScriptTokenType.StringLiteral -> StringEscapeUtils.escapeEcmaScript(text)
        else -> null
    }
}
