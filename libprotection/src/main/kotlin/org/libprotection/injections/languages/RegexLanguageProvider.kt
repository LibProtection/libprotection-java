package org.libprotection.injections.languages

import java.util.*

abstract class RegexLanguageProvider : LanguageProvider() {
    protected abstract val errorTokenType: TokenType
    protected abstract val mainModeRules : Iterable<RegexRule>

    override fun tokenize(text: String, offset: Int): Iterable<Token> {
        val res = arrayListOf<Token>()
        var currentText = text

        var currentPosition = 0
        val modeRulesStack = Stack<Iterable<RegexRule>>()
        modeRulesStack.push(mainModeRules)

        while (!currentText.isEmpty())
        {
            var isMatched = false

            for (rule in modeRulesStack.peek())
            {
                val match = rule.tryMatch(currentText)

                if (match.isPresent && match.get() != 0)
                {
                    isMatched = true
                    if (rule.isToken)
                    {
                        val tokenText = currentText.substring(0, match.get())

                        val token = createToken(rule.type, currentPosition + offset,
                                currentPosition + offset + tokenText.length - 1, tokenText)

                        currentText = currentText.substring(match.get())
                        currentPosition += match.get()
                        res.add(token)
                    }

                    if (rule.isPopMode) { modeRulesStack.pop(); }
                    if (rule.isPushMode) { modeRulesStack.push(rule.modeRules) }

                    break
                }
            }

            // Simply error-tolerance strategy: consider current char as error-token and move to next
            if (!isMatched)
            {
                val token = createToken(
                        errorTokenType,
                        currentPosition + offset,
                        currentPosition + offset,
                        text[0].toString())

                currentText = currentText.substring(1)
                currentPosition++
                res.add(token)
            }
        }
        return res
    }
}

