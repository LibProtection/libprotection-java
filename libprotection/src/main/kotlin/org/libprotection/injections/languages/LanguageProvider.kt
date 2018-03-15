package org.libprotection.injections.languages

import java.util.*

abstract class LanguageProvider {

    abstract fun tokenize(text: String, offset: Int = 0): Iterable<Token>

    abstract fun trySanitize(text: String, context: Token) : Optional<String>

    abstract fun isTrivial(type: TokenType, text: String): Boolean

    protected fun createToken(type: TokenType, lowerBound: Int, upperBound: Int, text: String) =
            Token(this, type, lowerBound, upperBound, text, isTrivial(type, text))
}