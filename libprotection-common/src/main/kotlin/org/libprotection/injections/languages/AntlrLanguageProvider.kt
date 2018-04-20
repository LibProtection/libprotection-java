package org.libprotection.injections.languages

import antlr4Lexer

abstract class AntlrLanguageProvider : LanguageProvider() {
    private val eofAntlrTokenType = -1

    override fun tokenize(text: String, offset: Int): Iterable<Token> {
        val res = arrayListOf<Token>()

        val lexer = createLexer(text)
        var antlrToken = lexer.nextToken()

        while (antlrToken.getType() != eofAntlrTokenType) {
            val token = createToken(
                    convertAntlrTokenType(antlrToken.getType()),
                    antlrToken.getStartIndex() + offset,
                    antlrToken.getStopIndex() + offset, antlrToken.getText())

            res.add(token)

            antlrToken = lexer.nextToken()
        }
        return res
    }

    protected abstract fun convertAntlrTokenType(antlrTokenType: Int): TokenType
    protected abstract fun createLexer(text: String): antlr4Lexer
}