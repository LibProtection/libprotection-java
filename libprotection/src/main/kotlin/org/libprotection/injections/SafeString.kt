package org.libprotection.injections

import org.libprotection.injections.languages.LanguageProvider
import javax.validation.constraints.NotNull

class SafeString{
    companion object {
        @JvmStatic
        fun format(@NotNull provider : LanguageProvider, @NotNull format : String, vararg args : Any?) : String =
                tryFormat(provider, format, args).orElseThrow{ throw AttackDetectedException() }

        @JvmStatic
        fun tryFormat(@NotNull provider : LanguageProvider, @NotNull format : String, vararg args : Any?) =
                FormatProvider.tryFormat(provider, format, args)
    }
}