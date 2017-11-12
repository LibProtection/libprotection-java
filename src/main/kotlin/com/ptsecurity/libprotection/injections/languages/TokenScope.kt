package com.ptsecurity.libprotection.injections.languages

import com.ptsecurity.libprotection.injections.Range

internal class TokenScope(val range: Range) {
    val tokens = arrayListOf<Token>()
}