package org.libprotection.injections.languages.javascript

import org.libprotection.injections.languages.AntlrLanguageProvider
import org.libprotection.injections.languages.TokenType
import org.libprotection.injections.languages.Token
import org.libprotection.injections.utils.*

import antlr4InputStream
import antlr4Lexer
import antlr4JavaScriptLexer
import encodeJavaScript

object JavaScript : AntlrLanguageProvider() {

    override fun trySanitize(text: String, context: Token): MayBe<String> = when (context.languageProvider) {
        JavaScript -> tryJavaScriptEncode(text, context.type as JavaScriptTokenType)
        else -> throw IllegalArgumentException("Unsupported JavaScript island: $context")
    }

    override fun convertAntlrTokenType(antlrTokenType: Int): TokenType = JavaScriptTokenType.fromInt(antlrTokenType)

    override fun createLexer(text: String): antlr4Lexer = antlr4JavaScriptLexer(antlr4InputStream(text)).apply { setUseStrictDefault(false) }

    override fun isTrivial(type: TokenType, text: String): Boolean = when (type as JavaScriptTokenType) {
        JavaScriptTokenType.LineTerminator,
        JavaScriptTokenType.MultiLineComment,
        JavaScriptTokenType.SingleLineComment,
        JavaScriptTokenType.RegularExpressionLiteral,
        JavaScriptTokenType.NullLiteral,
        JavaScriptTokenType.BooleanLiteral,
        JavaScriptTokenType.DecimalLiteral,
        JavaScriptTokenType.HexIntegerLiteral,
        JavaScriptTokenType.OctalIntegerLiteral,
        JavaScriptTokenType.StringLiteral -> true
        else -> false
    }

    private fun tryJavaScriptEncode(text: String, tokenType: JavaScriptTokenType): MayBe<String> = when (tokenType) {
        JavaScriptTokenType.RegularExpressionLiteral ->
            Some(encodeJavaScript(text).replace("/", "\\/"))
        JavaScriptTokenType.StringLiteral ->
            Some(encodeJavaScript(text))
        else -> None
    }
}