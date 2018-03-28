package org.libprotection.injections.languages

import org.libprotection.injections.Range

internal class TokenScope(val range: Range) {
    val tokens = arrayListOf<Token>()
    val isTrivial get() = tokens.all { it.isTrivial }
}