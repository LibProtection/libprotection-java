package org.libprotection.injections.languages.sql

import org.libprotection.injections.languages.AntlrLanguageProvider
import org.libprotection.injections.languages.Token
import org.libprotection.injections.languages.TokenType
import org.libprotection.injections.utils.Optional

import antlr4Lexer
import antlr4InputStream
import antlr4SQLLexer

object Sql : AntlrLanguageProvider() {

    override fun convertAntlrTokenType(antlrTokenType: Int): TokenType = SqlTokenType.fromInt(antlrTokenType)

    override fun createLexer(text: String): antlr4Lexer = antlr4SQLLexer(antlr4InputStream(text))

    override fun isTrivial(type: TokenType, text: String): Boolean = when(type as SqlTokenType){
        SqlTokenType.Space,
        SqlTokenType.NullLiteral,
        SqlTokenType.FilesizeLiteral,
        SqlTokenType.StartNationalStringLiteral,
        SqlTokenType.StringLiteral,
        SqlTokenType.DecimalLiteral,
        SqlTokenType.HexadecimalLiteral,
        SqlTokenType.RealLiteral,
        SqlTokenType.NullSpecLiteral -> true
        else -> false
    }

    override fun trySanitize(text: String, context: Token): Optional<String> = when(context.languageProvider){
        Sql -> trySqlEncode(text, context.type as SqlTokenType)
        else -> throw IllegalArgumentException("Unsupported SQL island: $context")
    }

    private fun trySqlEncode(text : String, tokenType : SqlTokenType) = when(tokenType) {
            SqlTokenType.StringLiteral -> Optional.of(text.replace("'", "''"))
            else -> Optional.empty()
    }
}