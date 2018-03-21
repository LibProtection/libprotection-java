package org.libprotection.injections

import org.libprotection.injections.languages.LanguageProvider
import org.libprotection.injections.languages.Token
import org.libprotection.injections.languages.TokenScope
import java.util.*

class LanguageService{

    companion object {

        private fun String.substring(range : Range) = substring(range.lowerBound, range.length)

        fun trySanityze(languageProvider : LanguageProvider, text : String, taintedRanges : List<Range>) : Optional<String>{

            val sanitizedRanges = mutableListOf<Range>()
            val tokens = languageProvider.tokenize(text)
            val fragments = mutableMapOf<Range, String>()

            // Try to sanitize all attacked text's fragments
            for(tokensScope in getTokensScopes(tokens, taintedRanges))
            {
                val range = tokensScope.range
                val fragment = text.substring(range)

                val sanitizedFragment = languageProvider.trySanitize(fragment, tokensScope.tokens[0])
                fragments[range] = sanitizedFragment.orElse(fragment)
            }

            // Replace all attacked text's fragments with corresponding sanitized values
            var positionAtText = 0
            val sanitizedBuilder = StringBuilder()

            for(range in fragments.keys)            {
                val charsToAppend = range.lowerBound - positionAtText
                sanitizedBuilder.append(text, positionAtText, charsToAppend)
                val lowerBound = sanitizedBuilder.length
                sanitizedBuilder.append(fragments[range])
                sanitizedRanges.add(Range(lowerBound, sanitizedBuilder.length - 1))
                positionAtText = range.upperBound + 1
            }

            if (positionAtText < text.length)            {
                sanitizedBuilder.append(text, positionAtText, text.length - positionAtText)

            }

            val sanitizedText = sanitizedBuilder.toString()
            return if(validate(languageProvider, sanitizedText, sanitizedRanges)) Optional.of(sanitizedText)
                   else Optional.empty()
        }

        fun validate(languageProvider : LanguageProvider, text : String, ranges : List<Range>) : Boolean {
            val tokens = languageProvider.tokenize(text);

            var scopesCount = 0
            var allTrivial = true

            for (scope in getTokensScopes(tokens, ranges)){
                scopesCount++
                allTrivial = allTrivial and scope.isTrivial
                if ((scope.tokens.count() > 1 ||  scopesCount > 1) && !allTrivial) { return false }
            }
            return true
        }

        private fun getTokensScopes(tokens : Iterable<Token>, ranges : List<Range>) : Iterable<TokenScope>
        {
            val scopesMap = mutableMapOf<Range, TokenScope>()

            for(token in tokens){
                for(range in ranges){
                    if (range.overlaps(token.range)){
                        val tokensScope = scopesMap.getOrPut(range) { TokenScope(range) }
                        tokensScope.tokens.add(token)
                    }
                }
            }

            return scopesMap.values
        }
    }
}