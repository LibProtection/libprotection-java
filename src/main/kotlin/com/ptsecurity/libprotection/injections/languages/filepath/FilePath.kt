package com.ptsecurity.libprotection.injections.languages.filepath

import com.ptsecurity.libprotection.injections.languages.RegexLanguageProvider
import com.ptsecurity.libprotection.injections.languages.RegexTokenDefinition
import com.ptsecurity.libprotection.injections.languages.TokenType

object FilePath : RegexLanguageProvider() {
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