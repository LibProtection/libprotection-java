package org.libprotection.injections

import org.libprotection.injections.languages.Token
import org.libprotection.injections.utils.Optional

class FormatResult private constructor(val tokens : Array<Token>, val isAttackDetected : Boolean, val injectionPointIndex : Int, val formattedString : Optional<String>) {

    companion object {
        fun success(tokens : Array<Token>, formattedString : String) = FormatResult(tokens, false, -1, Optional.of(formattedString))

        fun fail(tokens : Array<Token>, injectionPointIndex : Int) = FormatResult(tokens, true, injectionPointIndex, Optional.empty())
    }

    override fun hashCode(): Int {
        var result = isAttackDetected.hashCode()
        result = 31 * result + tokens.contentHashCode()
        result = 31 * result + injectionPointIndex.hashCode()
        result = 31 * result + formattedString.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other === null) return false
        if (this::class != other::class) return false

        other as FormatResult

        if (!tokens.contentDeepEquals(other.tokens)) return false
        if (isAttackDetected != other.isAttackDetected) return false
        if (injectionPointIndex != other.injectionPointIndex) return false
        if (formattedString != other.formattedString) return false

        return true
    }
}