package org.libprotection.injections.formatting

import org.libprotection.injections.Range
import java.text.MessageFormat
import java.util.*

internal class Formatter{

    class FormatAndRanges(val format: String, val taintedRanges : List<Range>, val associatedToRangeIndexes : List<Int>)

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
        fun format(locale : Locale, format : String, args : Array<out Any?>) : FormatAndRanges{

            val taintedRanges = mutableListOf<Range>()
            val associatedToRangeIndexes = mutableListOf<Int>()

            val messageFormat = MessageFormat(format, locale)

            val iterator = messageFormat.formatToCharacterIterator(args)

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

                        val range = Range(i - extraShift, j - extraShift)
                        taintedRanges.add(range)
                        associatedToRangeIndexes.add(argumentIndex)
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
                        taintedRanges.removeAt(taintedRanges.lastIndex)
                        associatedToRangeIndexes.removeAt(associatedToRangeIndexes.lastIndex)
                        extraShift += safeMatcher.text.length
                        formatStatus = Status.InFormatter
                    }
                }
                i++
            }

            return FormatAndRanges(sb.toString(), taintedRanges, associatedToRangeIndexes)
        }
    }
}