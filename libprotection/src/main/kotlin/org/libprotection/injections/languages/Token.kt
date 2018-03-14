package org.librpotection.injections.languages

import org.librpotection.injections.Range
import javax.validation.constraints.NotEmpty

class Token(
        val languageProvider: LanguageProvider,
        val type: TokenType,
        lowerBound: Int,
        upperBound: Int,
        @NotEmpty val text: String,
        val isSafe: Boolean
) {
    val range = Range(lowerBound, upperBound)
    override fun toString() = "$type:\"$text\":[$range]"
}