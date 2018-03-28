package org.libprotection.injections.languages.sql

import SQLLexer
import com.sun.javaws.exceptions.InvalidArgumentException
import org.libprotection.injections.languages.AntlrLanguageProvider
import org.libprotection.injections.languages.Token
import org.libprotection.injections.languages.TokenType
import org.antlr.v4.runtime.ANTLRInputStream
import org.antlr.v4.runtime.Lexer
import java.util.*

object Sql : AntlrLanguageProvider() {

    override fun convertAntlrTokenType(antlrTokenType: Int): TokenType = SqlTokenType.fromInt(antlrTokenType)

    override fun createLexer(text: String): Lexer = SQLLexer(ANTLRInputStream(text))

    override fun isTrivial(type: TokenType, text: String): Boolean = when(type as SqlTokenType){
        SqlTokenType.Space,
        SqlTokenType.CommentInput,
        SqlTokenType.LineComment,
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
        else -> throw InvalidArgumentException(arrayOf("Unsupported SQL island: $context"))
    }

    private fun trySqlEncode(text : String, tokenType : SqlTokenType) = when(tokenType) {
            SqlTokenType.StringLiteral -> Optional.of(text.replace("''", "'").replace("'", "''"))
            else -> Optional.empty()
    }
}