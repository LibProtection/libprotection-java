package org.libprotection.injections.languages.url

import org.libprotection.injections.utils.*
import org.libprotection.injections.languages.RegexLanguageProvider
import org.libprotection.injections.languages.RegexRule
import org.libprotection.injections.languages.Token
import org.libprotection.injections.languages.TokenType
import encodeUriComponent

object Url : RegexLanguageProvider() {

    override val errorTokenType: TokenType = UrlTokenType.Error

    /**
     * Could throw NPE if this method called from ctor or init super class methods!
     */
    override val mainModeRules: Iterable<RegexRule> by lazy{
        listOf(
                RegexRule.noTokenPushMode("""[^:/?#]+:""", SchemeModeRules),
                RegexRule.noTokenPushMode("""//[^/?#]*""", AuthorityModeRules),
                RegexRule.noTokenPushMode("""[^?#]*""", PathModeRules),
                RegexRule.noTokenPushMode("""\?[^#]*""", QueryModeRules),
                RegexRule.noTokenPushMode("""#.*""", FragmentModeRules)) }


    private val SchemeModeRules =  listOf(
            RegexRule.token("[^:]+", UrlTokenType.Scheme),
            RegexRule.tokenPopMode(":", UrlTokenType.Separator))

    private val AuthorityModeRules =  listOf(
            RegexRule.token("//", UrlTokenType.Separator),
            RegexRule.token("[^/@:]+", UrlTokenType.AuthorityEntry),
            RegexRule.token("[:@]", UrlTokenType.Separator),
            RegexRule.noTokenPopMode("/"))

    private val PathModeRules =  listOf(
            RegexRule.token("/", UrlTokenType.Separator),
            RegexRule.token("[^/?#]+", UrlTokenType.PathEntry),
            RegexRule.noTokenPopMode("[?#]"))

    private val QueryModeRules =  listOf(
            RegexRule.token("\\?", UrlTokenType.Separator),
            RegexRule.token("[^?/=&#]+", UrlTokenType.QueryEntry),
            RegexRule.token("[=&]", UrlTokenType.Separator),
            RegexRule.noTokenPopMode("#"))

    private val FragmentModeRules =  listOf(
            RegexRule.token("#", UrlTokenType.Separator),
            RegexRule.tokenPopMode("[^#]*", UrlTokenType.Fragment))

    override fun trySanitize(text: String, context: Token): MayBe<String> = when(context.languageProvider) {
        Url -> tryUrlEncode(text, context.type as UrlTokenType)
        else -> throw IllegalArgumentException("Unsupported URL island: $context")
    }

    override fun isTrivial(type: TokenType, text: String): Boolean = when(type as UrlTokenType){
        UrlTokenType.QueryEntry,
        UrlTokenType.Fragment -> true
        UrlTokenType.PathEntry -> !text.contains("..")
        else -> false
    }

    private fun tryUrlEncode(text : String, tokenType : UrlTokenType) : MayBe<String> = when(tokenType) {
        UrlTokenType.PathEntry -> {
            val fragments = text.split('/').toTypedArray()
            for (i in 0 until fragments.count())
            {
                if (fragments[i] != "") {
                    fragments[i] = encodeUriComponent(fragments[i])
                }
            }
            Some(fragments.joinToString("/"))
        }
        UrlTokenType.QueryEntry,
        UrlTokenType.Fragment -> Some(encodeUriComponent(text))
        else -> None
    }


}