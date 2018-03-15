package org.libprotection.injections.languages.filepath

import org.libprotection.injections.languages.TokenType

enum class FilePathTokenType: TokenType {
    Error,
    DisallowedSymbol,
    DeviceID,
    FSEntryName,
    NTFSAttribute,
    Separator,
}