package org.librpotection.injections.languages.filepath

import org.librpotection.injections.languages.TokenType

enum class FilePathTokenType: TokenType {
    Error,
    DisallowedSymbol,
    DeviceID,
    FSEntryName,
    NTFSAttribute,
    Separator,
}