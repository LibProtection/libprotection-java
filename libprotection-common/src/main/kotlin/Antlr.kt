expect interface antlr4Token {
    fun getType() : Int
    fun getStartIndex() : Int
    fun getStopIndex() : Int
    fun getText(): String
}

expect abstract class antlr4Lexer{
    fun nextToken() : antlr4Token
}

expect interface antlr4CharStream

expect class antlr4InputStream(text : String) : antlr4CharStream

expect class antlr4HTMLLexer(stream : antlr4CharStream) : antlr4Lexer

expect abstract class antlr4JavaScriptBaseLexer(stream : antlr4CharStream) : antlr4Lexer{
    fun setUseStrictDefault(restrict : Boolean)
}

expect class antlr4JavaScriptLexer(stream : antlr4CharStream) : antlr4JavaScriptBaseLexer

expect class antlr4SQLLexer(stream : antlr4CharStream) : antlr4Lexer
