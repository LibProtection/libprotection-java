
@JsName("Token")
actual external interface antlr4Token {
    actual fun getType() : Int
    actual fun getStartIndex() : Int
    actual fun getStopIndex() : Int
    actual fun getText(): String
}

@JsName("Lexer")
actual abstract external class antlr4Lexer{
    actual fun nextToken() : antlr4Token
}

@JsName("CharStream")
actual external interface antlr4CharStream

@JsName("InputStream")
actual external class antlr4InputStream actual constructor(text : String) : antlr4CharStream

@JsName("HTMLLexer")
actual external class antlr4HTMLLexer actual constructor(stream : antlr4CharStream) : antlr4Lexer

@JsName("JavaScriptBaseLexer")
actual abstract external class antlr4JavaScriptBaseLexer actual constructor(stream : antlr4CharStream) : antlr4Lexer{
    actual fun setUseStrictDefault(restrict : Boolean)
}

@JsName("JavaScriptLexer")
actual external class antlr4JavaScriptLexer actual constructor(stream : antlr4CharStream) : antlr4JavaScriptBaseLexer

@JsName("SQLLexer")
actual external class antlr4SQLLexer actual constructor(stream : antlr4CharStream) : antlr4Lexer
