package org.librpotection.injections.languages

abstract class LanguageProvider {
    abstract val name: String
    abstract fun tokenize(text: String, offset: Int = 0): Collection<Token>

    open fun trySanitize(text: String, context: Token) =
            if (isSafeToken(context.type, context.text)) context.text
            else null

    protected fun createToken(type: TokenType, lowerBound: Int, upperBound: Int, text: String) =
            Token(this, type, lowerBound, upperBound, text, isSafeToken(type, text))

    abstract fun isSafeToken(type: TokenType, text: String): Boolean
}