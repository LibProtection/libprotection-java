package org.libprotection.injections.languages

import org.libprotection.injections.Range
import javax.validation.constraints.NotEmpty

class Token(
        val languageProvider: LanguageProvider,
        val type: TokenType,
        lowerBound: Int,
        upperBound: Int,
        @NotEmpty val text: String,
        val isTrivial : Boolean) {
    val range = Range(lowerBound, upperBound)
    override fun toString() = "$type:\"$text\":[$range]"
}