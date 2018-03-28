package org.libprotection.injections.languages

import org.antlr.v4.runtime.Lexer

abstract class AntlrLanguageProvider : LanguageProvider() {
    private val eofAntlrTokenType = -1

    override fun tokenize(text: String, offset: Int): Iterable<Token> {
        val res = arrayListOf<Token>()

        val lexer = createLexer(text)
        var antlrToken = lexer.nextToken()

        while (antlrToken.type != eofAntlrTokenType) {
            val token = createToken(
                    convertAntlrTokenType(antlrToken.type),
                    antlrToken.startIndex + offset,
                    antlrToken.stopIndex + offset, antlrToken.text)

            res.add(token)

            antlrToken = lexer.nextToken()
        }
        return res
    }

    protected abstract fun convertAntlrTokenType(antlrTokenType: Int): TokenType
    protected abstract fun createLexer(text: String): Lexer
}