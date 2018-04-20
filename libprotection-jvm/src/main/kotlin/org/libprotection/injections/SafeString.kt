package org.libprotection.injections

import org.libprotection.injections.caching.FormatCacheItem
import org.libprotection.injections.caching.RandomizedLRUCache
import org.libprotection.injections.languages.LanguageProvider
import java.util.concurrent.ConcurrentHashMap
import javax.validation.constraints.NotNull
import org.libprotection.injections.formatting.Formatter
import java.util.*

class SafeString{

    companion object {

        private val providerCaches = ConcurrentHashMap<LanguageProvider, RandomizedLRUCache<FormatCacheItem, FormatResult>>()
        private fun getProviderCache(provider: LanguageProvider) = providerCaches.getOrPut(provider) { RandomizedLRUCache(1024) }

        @JvmStatic
        //@Throws(AttackDetectedException::class.java)
        fun format(@NotNull provider : LanguageProvider, @NotNull format : String, vararg args : Any?)
            = tryFormat(provider, format, *args).orElseThrow { throw AttackDetectedException() }

        @JvmStatic
        fun tryFormat(@NotNull provider : LanguageProvider, @NotNull format : String, vararg args : Any?)
            = tryFormat(provider, Locale.getDefault(Locale.Category.FORMAT), format, *args)

        @JvmStatic
        //@Throws(AttackDetectedException::class)
        fun format(@NotNull provider : LanguageProvider, @NotNull locale : Locale, @NotNull format : String, vararg args : Any?)
            = tryFormat(provider, locale, format, *args).orElseThrow{ throw AttackDetectedException() }

        @JvmStatic
        fun tryFormat(@NotNull provider : LanguageProvider, @NotNull locale : Locale, @NotNull format : String, vararg args : Any?)
            = formatEx(provider, locale, format, *args).formattedString

        fun formatEx(@NotNull provider : LanguageProvider, @NotNull locale : Locale, @NotNull format : String, vararg args : Any?) : FormatResult {
            val keyItem = FormatCacheItem(locale, format, args)
            return getProviderCache(provider).get(keyItem) { tryFormatImpl(provider, it) }
        }

        private fun tryFormatImpl(provider : LanguageProvider, formatItem : FormatCacheItem): FormatResult{
            val formatResult = Formatter.format(formatItem.locale, formatItem.format, formatItem.args)
            val sanitizeResult = LanguageService.trySanityze(provider, formatResult.format, formatResult.taintedRanges)

            return if(sanitizeResult.success) {
                FormatResult.success(sanitizeResult.tokens, sanitizeResult.sanitizedText.value)
            }else{
                val attackArgumentIndex = formatResult.taintedRanges.indexOfFirst{ it.overlaps(sanitizeResult.attackToken.value.range) }
                assert(attackArgumentIndex != -1) { "Cannot find attack argument for attack token." }
                FormatResult.fail(sanitizeResult.tokens, formatResult.associatedToRangeIndexes[attackArgumentIndex])
            }
        }
    }
}