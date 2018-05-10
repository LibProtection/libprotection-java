package org.libprotection.injections.languages

import org.libprotection.injections.Range

class Token(
        val languageProvider: LanguageProvider,
        val type: TokenType,
        lowerBound: Int,
        upperBound: Int,
        val text: String,
        val isTrivial : Boolean) {
    val range = Range(lowerBound, upperBound)
    override fun toString() = "$type:\"$text\":$range"


    override fun hashCode(): Int {
        var result = languageProvider.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + text.hashCode()
        result = 31 * result + isTrivial.hashCode()
        result = 31 * result + range.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other === null) return false
        if (this::class != other::class) return false

        other as Token

        if (languageProvider != other.languageProvider) return false
        if (type != other.type) return false
        if (text != other.text) return false
        if (isTrivial != other.isTrivial) return false
        if (range != other.range) return false

        return true
    }
}