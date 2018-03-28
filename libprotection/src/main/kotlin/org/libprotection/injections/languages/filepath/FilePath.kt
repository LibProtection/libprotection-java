package org.libprotection.injections.languages.filepath

import org.libprotection.injections.languages.RegexLanguageProvider
import org.libprotection.injections.languages.RegexRule
import org.libprotection.injections.languages.Token
import org.libprotection.injections.languages.TokenType
import java.util.*

object FilePath : RegexLanguageProvider() {

    private const val disallowedSymbols = """<>:""/\\\|\?\*""" + "\u0000-\u001f"

    override val errorTokenType = FilePathTokenType.Error

    override val mainModeRules = arrayListOf(
            RegexRule.token("""[\\/]+""", FilePathTokenType.Separator),
            RegexRule.token("""[a-zA-Z]+[\$:](?=[\\/])""", FilePathTokenType.DeviceID),
            RegexRule.token("[^$disallowedSymbols]+", FilePathTokenType.FSEntryName),
            RegexRule.token(":+\\$[^$disallowedSymbols]+", FilePathTokenType.NTFSAttribute),
            RegexRule.token("[$disallowedSymbols]", FilePathTokenType.DisallowedSymbol)
    )

    override fun trySanitize(text: String, context: Token): Optional<String> = Optional.empty()

    override fun isTrivial(type: TokenType, text: String) = type == FilePathTokenType.FSEntryName && !text.contains("..")
}