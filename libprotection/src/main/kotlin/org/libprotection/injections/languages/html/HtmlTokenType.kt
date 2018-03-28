package org.libprotection.injections.languages.html

import org.libprotection.injections.languages.TokenType

enum class HtmlTokenType(val value: Int) : TokenType {
    HtmlComment(1),
    HtmlConditionalComment(2),
    XmlDeclaration(3),
    Cdata(4),
    Dtd(5),
    SpecialTag(6),
    TagOpen(7),
    HtmlText(8),
    ErrorText(9),
    TagClose(10),
    TagSlashClose(11),
    TagSlash(12),
    TagEquals(13),
    TagWhiteSpace(14),
    AttributeName(15),
    ErrorTag(16),
    AttributeWhiteSpace(17),
    AttributeSlash(18),
    AttributeValue(19),
    ErrorAttribute(20);

    companion object {
        private val map = HtmlTokenType.values().associateBy(HtmlTokenType::value)
        fun fromInt(type: kotlin.Int) = map[type]!!
    }
}