package org.libprotection.injections.languages.javascript

import JavaScriptLexer
import org.antlr.v4.runtime.ANTLRInputStream
import org.antlr.v4.runtime.Lexer

import org.libprotection.injections.languages.AntlrLanguageProvider
import org.libprotection.injections.languages.TokenType
import org.libprotection.injections.languages.Token
import org.libprotection.injections.InvalidArgumentException
import org.libprotection.injections.utils.Optional

object JavaScript : AntlrLanguageProvider() {

    override fun trySanitize(text: String, context: Token): Optional<String> = when (context.languageProvider) {
        JavaScript -> tryJavaScriptEncode(text, context.type as JavaScriptTokenType)
        else -> throw InvalidArgumentException("Unsupported JavaScript island: $context")
    }

    override fun convertAntlrTokenType(antlrTokenType: Int): TokenType = JavaScriptTokenType.fromInt(antlrTokenType)

    override fun createLexer(text: String): Lexer = JavaScriptLexer(ANTLRInputStream(text)).apply { setUseStrictDefault(false) }

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

    private fun tryJavaScriptEncode(text: String, tokenType: JavaScriptTokenType): Optional<String> = when (tokenType) {
        JavaScriptTokenType.RegularExpressionLiteral ->
            Optional.of(org.owasp.encoder.Encode.forJavaScript(text).replace("/", "\\/"))
        JavaScriptTokenType.StringLiteral ->
            Optional.of(org.owasp.encoder.Encode.forJavaScript(text))
        else -> Optional.empty()
    }
}