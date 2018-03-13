package org.librpotection.injections.languages

import org.librpotection.injections.Range

internal class TokenScope(val range: Range) {
    val tokens = arrayListOf<Token>()
}