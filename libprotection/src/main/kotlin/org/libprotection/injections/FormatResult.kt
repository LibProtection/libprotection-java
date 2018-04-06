package org.libprotection.injections

import org.libprotection.injections.languages.Token
import java.util.*

class FormatResult private constructor(val tokens : Array<Token>, val isAttackDetected : Boolean, val injectionPointIndex : Int, val formattedString : Optional<String>) {

    companion object {
        fun success(tokens : Array<Token>, formattedString : String) = FormatResult(tokens, false, -1, Optional.of(formattedString))

        fun fail(tokens : Array<Token>, injectionPointIndex : Int) = FormatResult(tokens, true, injectionPointIndex, Optional.empty())
    }

    override fun hashCode(): Int {
        var result = isAttackDetected.hashCode()
        result = 31 * result + Arrays.deepHashCode(tokens)
        result = 31 * result + injectionPointIndex.hashCode()
        result = 31 * result + formattedString.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FormatResult

        if (!Arrays.deepEquals(tokens, other.tokens)) return false
        if (isAttackDetected != other.isAttackDetected) return false
        if (injectionPointIndex != other.injectionPointIndex) return false
        if (formattedString != other.formattedString) return false

        return true
    }
}