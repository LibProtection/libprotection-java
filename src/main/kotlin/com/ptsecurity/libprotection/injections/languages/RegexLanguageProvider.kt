package com.ptsecurity.libprotection.injections.languages

abstract class RegexLanguageProvider : LanguageProvider() {
    protected abstract val errorTokenType: TokenType
    protected abstract val tokenDefinitions: Collection<RegexTokenDefinition>
    override fun tokenize(text: String, offset: Int): Collection<Token> {
        var currentPosition = 0
        var remainingText = text
        val res = arrayListOf<Token>()
        while (remainingText.isNotEmpty()) {
            var isMatched = false
            for (tokenDefinition in tokenDefinitions) {
                val matchResult = tokenDefinition.tryMatch(remainingText)
                if (matchResult != null) {
                    isMatched = true
                    val matchedLength = matchResult.range.endInclusive - matchResult.range.start
                    assert(matchedLength != 0)
                    val tokenText = matchResult.value
                    val token = createToken(
                            tokenDefinition.type,
                            currentPosition + offset,
                            currentPosition + offset + tokenText.length - 1,
                            tokenText
                    )
                    remainingText = remainingText.substring(matchedLength)
                    currentPosition += matchedLength
                    res.add(token)
                    break
                }
            }
            // Simply error-tolerance strategy: consider current char as error-token and move to next
            if (!isMatched) {
                val token = createToken(
                        errorTokenType,
                        currentPosition + offset,
                        currentPosition + offset,
                        remainingText.substring(0, 1)
                )
                res.add(token)
            }
        }
        return res
    }
}

