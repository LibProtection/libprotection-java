package org.libprotection.injections

import org.libprotection.injections.languages.LanguageProvider
import java.text.MessageFormat
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import javax.validation.constraints.NotNull

class SafeString{
    companion object {

        @JvmStatic
        fun format(@NotNull provider : LanguageProvider, @NotNull format : String, vararg args : Any?) : String =
                tryFormat(provider, format, *args).orElseThrow{ throw AttackDetectedException() }

        @JvmStatic
        fun tryFormat(@NotNull provider : LanguageProvider, @NotNull format : String, vararg args : Any?): Optional<String>{

            val taintedRanges = mutableListOf<Range>()

            val messageFormat = MessageFormat(format.replace("'", "''"))

            val iterator = messageFormat.formatToCharacterIterator(args)

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