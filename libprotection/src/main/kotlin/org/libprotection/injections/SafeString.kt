package org.libprotection.injections

import org.libprotection.injections.caching.FormatCacheItem
import org.libprotection.injections.caching.RandomizedLRUCache
import org.libprotection.injections.languages.LanguageProvider
import java.text.MessageFormat
import java.util.*
import javax.validation.constraints.NotNull

class SafeString{

    companion object {

        private val cache = RandomizedLRUCache<FormatCacheItem, Optional<String>>(1024)

        @JvmStatic
        @Throws(AttackDetectedException::class)
        fun format(@NotNull provider : LanguageProvider, @NotNull format : String, vararg args : Any?) : String =
                tryFormat(provider, format, *args).orElseThrow{ throw AttackDetectedException() }

        @JvmStatic
        fun tryFormat(@NotNull provider : LanguageProvider, @NotNull format : String, vararg args : Any?): Optional<String> {
            val keyItem = FormatCacheItem(format, args)
            return cache.get(keyItem) { tryFormatImpl(provider, it) }
        }

        private fun tryFormatImpl(provider : LanguageProvider, formatItem : FormatCacheItem): Optional<String>{

            val taintedRanges = mutableListOf<Range>()

            val messageFormat = MessageFormat(formatItem.format)

            val iterator = messageFormat.formatToCharacterIterator(formatItem.args)

            var i = iterator.beginIndex
            var j = i
            val sb = StringBuilder()

            while(i != iterator.endIndex){
                if(i == j){
                    j = iterator.getRunLimit(iterator.allAttributeKeys)
                    if(iterator.attributes.isNotEmpty()){
                        taintedRanges.add(Range(i, j - 1))
                    }
                }

                sb.append(iterator.current())
                i++
                iterator.index = i
            }

            return LanguageService.trySanityze(provider, sb.toString(), taintedRanges)
        }
    }
}