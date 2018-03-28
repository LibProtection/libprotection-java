package org.libprotection.injections.languages

import java.util.*
import kotlin.text.Regex

class RegexRule(regex: String) {

    private enum class NoToken(val value : Int) : TokenType{
        VALUE(0)
    }

    var type : TokenType = NoToken.VALUE
        private set
    var modeRules : Iterable<RegexRule>? = null
        private set
    val isPushMode get() = modeRules != null
    var isPopMode : Boolean = false
        private set
    val isToken : Boolean get() = NoToken.VALUE != type

    private val regex : Regex = Regex("^$regex")

    fun tryMatch(text : String) : Optional<Int> {

        val matchResult = regex.find(text)
        return if(matchResult != null){
            Optional.of(matchResult.value.length)
        }else{
            Optional.empty()
        }
    }

    companion object {
        fun token(regex : String, type : TokenType) : RegexRule {
            return RegexRule (regex).apply { this.type = type }
        }

        fun tokenPushMode(regex : String, type : TokenType, modeRules : Iterable<RegexRule>) : RegexRule{
            return RegexRule (regex).apply { this.type = type; this.modeRules = modeRules }
        }

        fun tokenPopMode(regex : String, type : TokenType) : RegexRule{
            return RegexRule (regex).apply { this.type = type; isPopMode = true }
        }

        fun noToken(regex : String) : RegexRule{
            return RegexRule (regex)
        }

        fun noTokenPushMode(regex : String, modeRules : Iterable<RegexRule>) : RegexRule{
            return RegexRule (regex).apply { this.modeRules = modeRules }
        }

        fun noTokenPopMode(regex : String) : RegexRule{
            return RegexRule (regex).apply { isPopMode = true }
        }
    }
}