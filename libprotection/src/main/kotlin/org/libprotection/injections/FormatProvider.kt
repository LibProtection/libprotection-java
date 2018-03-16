package org.libprotection.injections

import org.libprotection.injections.languages.LanguageProvider
import java.util.*
import javax.validation.constraints.NotNull

internal class FormatProvider{
    companion object {
        fun tryFormat(@NotNull provider : LanguageProvider, @NotNull format : String, vararg args : Any?): Optional<String>{
            return Optional.empty()
        }
    }

}