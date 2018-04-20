package org.libprotection.injections.caching

import java.util.*

class FormatCacheItem(val locale : Locale, val format : String, val args : Array<out Any?>){

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (null === other) return false
        if (this::class != other::class) return false

        other as FormatCacheItem

        if (format != other.format) return false
        if (locale != other.locale) return false
        if (args.contentEquals(other.args)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = format.hashCode()
        result = 31 * result + args.contentHashCode()
        result = 31 * result + locale.hashCode()
        return result
    }
}