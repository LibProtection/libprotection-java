package com.ptsecurity.libprotection.injections.languages.filepath

import com.ptsecurity.libprotection.injections.languages.TokenType

enum class FilePathTokenType: TokenType {
    Error,
    DisallowedSymbol,
    DeviceID,
    FSEntryName,
    NTFSAttribute,
    Separator,
}