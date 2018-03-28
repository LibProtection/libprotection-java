package org.libprotection.injections.languages.url

import org.libprotection.injections.languages.TokenType

enum class UrlTokenType : TokenType {
    Error,
    Separator,
    Scheme,
    AuthorityEntry,
    PathEntry,
    QueryEntry,
    Fragment,
}