package org.librpotection.injections.languages

class RegexTokenDefinition(private val regex: String, val type: TokenType) {
    private val _regex = Regex("^$regex")
    fun tryMatch(text: String) = _regex.find(text)
}