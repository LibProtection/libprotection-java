package org.libprotection.injections

import org.libprotection.injections.caching.FormatCacheItem
import org.libprotection.injections.caching.RandomizedLRUCache
import org.libprotection.injections.languages.LanguageProvider
import java.text.MessageFormat
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import javax.validation.constraints.NotNull

class SafeString{

    private class StringMatcher(val text : String){

        private var matchIndex = -1

        fun matchNext(char: Char){
            matchIndex++
            if(matchIndex >= text.length || !text[matchIndex].equals(char, ignoreCase = true)){
                matchIndex = text.length
            }
        }

        fun matched() = matchIndex == text.length - 1

        fun reset() { matchIndex = -1 }
    }

    private enum class Status{
        InPlaceholder,
        InFormatter,
        JustAfterPlaceHolder,
    }

    companion object {


        private val providerCaches = ConcurrentHashMap<LanguageProvider, RandomizedLRUCache<FormatCacheItem, FormatResult>>()
        private fun getProviderCache(provider: LanguageProvider) = providerCaches.getOrPut(provider) { RandomizedLRUCache(1024) }

        private class FormatAndRanges(val format: String, val ranges : List<Range>, val argumentRanges : List<Pair<Range, Int>>)

        @JvmStatic
        @Throws(AttackDetectedException::class)
        fun format(@NotNull provider : LanguageProvider, @NotNull format : String, vararg args : Any?)
            = tryFormat(provider, format, *args).orElseThrow{ throw AttackDetectedException() }

        @JvmStatic
        fun tryFormat(@NotNull provider : LanguageProvider, @NotNull format : String, vararg args : Any?)
            = tryFormat(provider, Locale.getDefault(Locale.Category.FORMAT), format, *args)

        @JvmStatic
        @Throws(AttackDetectedException::class)
        fun format(@NotNull provider : LanguageProvider, @NotNull locale : Locale, @NotNull format : String, vararg args : Any?)
            = tryFormat(provider, locale, format, *args).orElseThrow{ throw AttackDetectedException() }

        @JvmStatic
        fun tryFormat(@NotNull provider : LanguageProvider, @NotNull locale : Locale, @NotNull format : String, vararg args : Any?)
            = formatEx(provider, locale, format, *args).formattedString

        fun formatEx(@NotNull provider : LanguageProvider, @NotNull locale : Locale, @NotNull format : String, vararg args : Any?) : FormatResult {
            val keyItem = FormatCacheItem(locale, format, args)
            return getProviderCache(provider).get(keyItem) { tryFormatImpl(provider, it) }
        }

        private fun getFormatAndRanges(locale : Locale, format : String, arguments : Array<out Any?>) : FormatAndRanges {

            val ranges = mutableListOf<Range>()
            val argumentRanges = mutableListOf<Pair<Range, Int>>()

            val messageFormat = MessageFormat(format, locale)

            val iterator = messageFormat.formatToCharacterIterator(arguments)

            val safeMatcher = StringMatcher(":safe")
            var extraShift = 0
            val sb = StringBuilder()
            var i = iterator.beginIndex
            var j = i
            var formatStatus = Status.InFormatter

            while(i != iterator.endIndex){
                iterator.index = i

                if(i == j){
                    j = iterator.getRunLimit(iterator.allAttributeKeys)
                    if(!iterator.attributes.isEmpty()){

                        val argumentIndex = iterator.attributes.filter { it.key is MessageFormat.Field }.map { it.value as Int }.firstOrNull()
                            ?: error("Could not find argument index.")

                        val range = Range(i - extraShift, j - 1 - extraShift)
                        ranges.add(range)
                        argumentRanges.add(Pair(range, argumentIndex))
                        safeMatcher.reset()
                        formatStatus = Status.InPlaceholder
                    }else if(i != 0){
                        formatStatus = Status.JustAfterPlaceHolder
                    }
                }

                sb.append(iterator.current())

                if(formatStatus == Status.JustAfterPlaceHolder){
                    safeMatcher.matchNext(iterator.current())
                    if(safeMatcher.matched()){
                        sb.delete(sb.length - safeMatcher.text.length, sb.length)
                        ranges.removeAt(ranges.lastIndex)
                        argumentRanges.removeAt(argumentRanges.lastIndex)
                        extraShift += safeMatcher.text.length
                        formatStatus = Status.InFormatter
                    }
                }
                i++
            }

            return FormatAndRanges(sb.toString(), ranges, argumentRanges)
        }

        private fun tryFormatImpl(provider : LanguageProvider, formatItem : FormatCacheItem): FormatResult{
            val formatResult = getFormatAndRanges(formatItem.locale, formatItem.format, formatItem.args)
            val sanitizeResult = LanguageService.trySanityze(provider, formatResult.format, formatResult.ranges)

            return if(sanitizeResult.success) {
                FormatResult.success(sanitizeResult.tokens, sanitizeResult.sanitizedText.get())
            }else{
                val attackArgument = formatResult.argumentRanges.find { it.first.overlaps(sanitizeResult.attackToken.get().range) }
                        ?: error("Cannot find attack argument for attack token.")
                FormatResult.fail(sanitizeResult.tokens, attackArgument.second)
            }
        }
    }
}