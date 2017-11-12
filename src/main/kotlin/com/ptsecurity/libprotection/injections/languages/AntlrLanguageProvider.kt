package com.ptsecurity.libprotection.injections.languages

import org.antlr.v4.runtime.Lexer

abstract class AntlrLanguageProvider : LanguageProvider() {
    private val eofAntlrTokenType = -1
    override fun tokenize(text: String, offset: Int): Collection<Token> {
        val res = arrayListOf<Token>()
        val lexer = createLexer(text)
        var token = lexer.nextToken()
        while (token.type != eofAntlrTokenType) {
            res.add(createToken(
                    convertAntlrTokenType(token.type),
                    token.startIndex + offset,
                    token.stopIndex + offset,
                    token.text
            ))
            token = lexer.nextToken()
        }
        return res
    }

    protected abstract fun convertAntlrTokenType(antlrTokenType: Int): TokenType
    protected abstract fun createLexer(text: String): Lexer
}