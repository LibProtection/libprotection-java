package org.librpotection.injections.languages

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
                if (matchResult != null && matchResult.value.isNotEmpty()) {
                    isMatched = true
                    val tokenText = matchResult.value
                    val token = createToken(
                            tokenDefinition.type,
                            currentPosition + offset,
                            currentPosition + offset + tokenText.length,
                            tokenText
                    )
                    remainingText = remainingText.substring(matchResult.value.length)
                    currentPosition += matchResult.value.length
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
                remainingText = remainingText.substring(1)
                currentPosition++
                res.add(token)
            }
        }
        return res
    }
}

