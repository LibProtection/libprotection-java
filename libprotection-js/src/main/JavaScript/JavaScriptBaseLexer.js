'use strict';

/**
 * All lexer methods that used in grammar (IsStrictMode)
 * should start with Upper Case Char similar to Lexer rules.
 */

const Lexer = require("antlr4/Lexer").Lexer;

function JavaScriptBaseLexer(input)
{
	Lexer.call(this, input)

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

JavaScriptBaseLexer.prototype = Object.create(Lexer.prototype);
JavaScriptBaseLexer.prototype.constructor = JavaScriptBaseLexer;

JavaScriptBaseLexer.prototype.getStrictDefault = function() {
        return this.useStrictDefault;
}

JavaScriptBaseLexer.prototype.setUseStrictDefault = function(value) {
        this.useStrictDefault = value;
        this.useStrictCurrent = value;
}

JavaScriptBaseLexer.prototype.IsSrictMode = function() {
        return this.useStrictCurrent;
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
JavaScriptBaseLexer.prototype.nextToken = function() {

	const next = Lexer.prototype.nextToken.call(this);

	if (next.type === JavaScriptLexer.OpenBrace)
	{
		this.useStrictCurrent = (this.scopeStrictModes.length > 0 && this.scopeStrictModes[this.scopeStrictModes.length-1]) ? true : this.useStrictDefault;
		this.scopeStrictModes.push(this.useStrictCurrent);
	}
	else if (next.type === JavaScriptLexer.CloseBrace)
	{
		this.useStrictCurrent = this.scopeStrictModes.length > 0 ? this.scopeStrictModes.pop() : this.useStrictDefault;
	}
	else if (next.type === JavaScriptLexer.StringLiteral &&
			(this.lastToken == null || this.lastToken.type === JavaScriptLexer.OpenBrace) &&
			(next.text.substring(1, next.text.length() - 1)).equals("use strict"))
	{
		if (this.scopeStrictModes.length > 0){
			this.scopeStrictModes.pop();
		}
		this.useStrictCurrent = true;
		this.scopeStrictModes.push(useStrictCurrent);
	}

	if (next.channel == Token.DEFAULT_CHANNEL) {
		// Keep track of the last token on the default channel.
		this.lastToken = next;
	}

	return next;
}

/**
 * Returns {@code true} if the lexer can match a regex literal.
 */

JavaScriptBaseLexer.prototype.RegexPossible = function() {

	if (this.lastToken === null) {
		// No token has been produced yet: at the start of the input,
		// no division is possible, so a regex literal _is_ possible.
		return true;
	}

	switch (this.lastToken.type) {
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


exports.JavaScriptBaseLexer = JavaScriptBaseLexer;