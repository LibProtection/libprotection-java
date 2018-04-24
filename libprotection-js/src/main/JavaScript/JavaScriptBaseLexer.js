'use strict';

/**
 * All lexer methods that used in grammar (IsStrictMode)
 * should start with Upper Case Char similar to Lexer rules.
 */

const Lexer = require("antlr4/Lexer").Lexer;

class JavaScriptBaseLexer extends Lexer
{
    constructor(input) {

		Lexer.call(this, input)

        super(input);

		/**
		 * Stores values of nested modes. By default mode is strict or
		 * defined externally (useStrictDefault)
		 */
		this.scopeStrictModes = [];

		this.lastToken = null;
		/**
		 * Default value of strict mode
		 * Can be defined externally by setUseStrictDefault
		 */
		this.useStrictDefault = false;
		/**
		 * Current value of strict mode
		 * Can be defined during parsing, see StringFunctions.js and StringGlobal.js samples
		 */
		this.useStrictCurrent = false;

    }

    getStrictDefault() {
        return useStrictDefault;
    }

    setUseStrictDefault(value) {
        useStrictDefault = value;
        useStrictCurrent = value;
    }

    IsSrictMode() {
        return useStrictCurrent;
    }

    /**
     * Return the next token from the character stream and records this last
     * token in case it resides on the default channel. This recorded token
     * is used to determine when the lexer could possibly match a regex
     * literal. Also changes scopeStrictModes stack if tokenize special
     * string 'use strict';
     *
     * @return the next token from the character stream.
     */
    /*@Override
    public Token nextToken() {
        Token next = super.nextToken();

        if (next.getType() == JavaScriptLexer.OpenBrace)
        {
            useStrictCurrent = scopeStrictModes.length > 0 && scopeStrictModes[scopeStrictModes.length-1]? true : useStrictDefault;
            scopeStrictModes.push(useStrictCurrent);
        }
        else if (next.getType() == JavaScriptLexer.CloseBrace)
        {
            useStrictCurrent = scopeStrictModes.length > 0 ? scopeStrictModes.pop() : useStrictDefault;
        }
        else if (next.getType() == JavaScriptLexer.StringLiteral &&
                (lastToken == null || lastToken.getType() == JavaScriptLexer.OpenBrace) &&
                (next.getText().substring(1, next.getText().length() - 1)).equals("use strict"))
        {
            if (scopeStrictModes.length > 0)
                scopeStrictModes.pop();
            useStrictCurrent = true;
            scopeStrictModes.push(useStrictCurrent);
        }

        if (next.getChannel() == Token.DEFAULT_CHANNEL) {
            // Keep track of the last token on the default channel.
            this.lastToken = next;
        }

        return next;
    }*/

    /**
     * Returns {@code true} if the lexer can match a regex literal.
     */
    RegexPossible() {
                                       
        if (this.lastToken === null) {
            // No token has been produced yet: at the start of the input,
            // no division is possible, so a regex literal _is_ possible.
            return true;
        }
        
        switch (this.lastToken.constructor) {
            case JavaScriptLexer.Identifier:
            case JavaScriptLexer.NullLiteral:
            case JavaScriptLexer.BooleanLiteral:
            case JavaScriptLexer.This:
            case JavaScriptLexer.CloseBracket:
            case JavaScriptLexer.CloseParen:
            case JavaScriptLexer.OctalIntegerLiteral:
            case JavaScriptLexer.DecimalLiteral:
            case JavaScriptLexer.HexIntegerLiteral:
            case JavaScriptLexer.StringLiteral:
            case JavaScriptLexer.PlusPlus:
            case JavaScriptLexer.MinusMinus:
                // After any of the tokens above, no regex literal can follow.
                return false;
            default:
                // In all other cases, a regex literal _is_ possible.
                return true;
        }
    }
}

exports.JavaScriptBaseLexer = JavaScriptBaseLexer;