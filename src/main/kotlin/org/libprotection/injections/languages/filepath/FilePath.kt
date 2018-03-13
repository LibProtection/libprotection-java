package org.librpotection.injections.languages.filepath

import org.librpotection.injections.languages.RegexLanguageProvider
import org.librpotection.injections.languages.RegexTokenDefinition
import org.librpotection.injections.languages.TokenType

object FilePath : RegexLanguageProvider() {
    override val name = "FilePath"
    private val disallowedSymbols = """<>:""/\\\|\?\*\x00-\x1f""";
    override val errorTokenType = FilePathTokenType.Error
    override val tokenDefinitions = arrayListOf(
            RegexTokenDefinition("""[\\/]+""", FilePathTokenType.Separator),
            RegexTokenDefinition("""[a-zA-Z]+[\$:](?=[\\/])""", FilePathTokenType.DeviceID),
            RegexTokenDefinition("""[^$disallowedSymbols]+""", FilePathTokenType.FSEntryName),
            RegexTokenDefinition(""":+\$[^$disallowedSymbols]+""", FilePathTokenType.NTFSAttribute),
            RegexTokenDefinition("[$disallowedSymbols]", FilePathTokenType.DisallowedSymbol)
    )

    override fun isSafeToken(type: TokenType, text: String) = type == FilePathTokenType.FSEntryName && !text.contains("..")
}