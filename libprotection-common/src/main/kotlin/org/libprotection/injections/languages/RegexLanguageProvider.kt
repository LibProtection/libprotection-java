package org.libprotection.injections.languages

import org.libprotection.injections.utils.Stack
import org.libprotection.injections.utils.*

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

                if (match is Some && match.value != 0)
                {
                    isMatched = true
                    if (rule.isToken)
                    {
                        val tokenText = currentText.substring(0, match.value)

                        val token = createToken(rule.type, currentPosition + offset,
                                currentPosition + offset + tokenText.length, tokenText)

                        currentText = currentText.substring(match.value)
                        currentPosition += match.value
                        res.add(token)
                    }

                    if (rule.isPopMode) { modeRulesStack.pop(); }
                    if (rule.isPushMode) { modeRulesStack.push(rule.modeRules!!) }

                    break
                }
            }

            // Simply error-tolerance strategy: consider current char as error-token and move to next
            if (!isMatched)
            {
                val token = createToken(
                        errorTokenType,
                        currentPosition + offset,
                        currentPosition + offset + 1,
                        text[0].toString())

                currentText = currentText.substring(1)
                currentPosition++
                res.add(token)
            }
        }
        return res
    }
}

