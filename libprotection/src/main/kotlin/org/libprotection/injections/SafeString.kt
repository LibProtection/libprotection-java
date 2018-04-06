package org.libprotection.injections

import org.libprotection.injections.caching.FormatCacheItem
import org.libprotection.injections.caching.RandomizedLRUCache
import org.libprotection.injections.languages.LanguageProvider
import java.text.MessageFormat
import java.util.*
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

        private val providerCaches = HashMap<LanguageProvider, RandomizedLRUCache<FormatCacheItem, Optional<String>>>()
        private fun getProviderCache(provider: LanguageProvider) = providerCaches.getOrPut(provider) { RandomizedLRUCache(1024) }

        @JvmStatic
        @Throws(AttackDetectedException::class)
        fun format(@NotNull provider : LanguageProvider, @NotNull format : String, vararg args : Any?) : String =
                tryFormat(provider, format, *args).orElseThrow{ throw AttackDetectedException() }

        @JvmStatic
        fun tryFormat(@NotNull provider : LanguageProvider, @NotNull format : String, vararg args : Any?): Optional<String> {
            val locale = Locale.getDefault(Locale.Category.FORMAT)
            val keyItem = FormatCacheItem(locale, format, args)
            return getProviderCache(provider).get(keyItem) { tryFormatImpl(provider, it) }
        }

        @JvmStatic
        @Throws(AttackDetectedException::class)
        fun format(@NotNull provider : LanguageProvider, @NotNull locale : Locale, @NotNull format : String, vararg args : Any?) : String =
                tryFormat(provider, locale, format, *args).orElseThrow{ throw AttackDetectedException() }

        @JvmStatic
        fun tryFormat(@NotNull provider : LanguageProvider, @NotNull locale : Locale, @NotNull format : String, vararg args : Any?): Optional<String> {
            val keyItem = FormatCacheItem(locale, format, args)
            return getProviderCache(provider).get(keyItem) { tryFormatImpl(provider, it) }
        }

        private fun getFormatAndRanges(locale : Locale, format : String, arguments : Array<out Any?>) : Pair<String, List<Range>>{

            val ranges = mutableListOf<Range>()

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
                        ranges.add(Range(i - extraShift, j - 1 - extraShift))
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
                        extraShift += safeMatcher.text.length
                        formatStatus = Status.InFormatter
                    }
                }
                i++
            }

            return Pair(sb.toString(), ranges)
        }

        private fun tryFormatImpl(provider : LanguageProvider, formatItem : FormatCacheItem): Optional<String>{
            val formatResult = getFormatAndRanges(formatItem.locale, formatItem.format, formatItem.args)
            return LanguageService.trySanityze(provider, formatResult.first, formatResult.second)
        }
    }
}