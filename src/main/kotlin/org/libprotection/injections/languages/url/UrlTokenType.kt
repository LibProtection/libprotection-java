package org.librpotection.injections.languages.url

import org.librpotection.injections.languages.TokenType

enum class UrlTokenType: TokenType {
    Error,
    Scheme,
    AuthorityEntry,
    PathEntry,
    QueryEntry,
    Fragment,

    // Non-terminals
    AuthorityCtx,
    PathCtx,
    QueryCtx,
}