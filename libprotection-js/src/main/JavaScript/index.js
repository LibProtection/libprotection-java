kotlin = require("./lib/kotlin");

'use strict'

Token = require("antlr4/Token").Token;
InputStream = require("antlr4/InputStream").InputStream;
Lexer = require("antlr4/Lexer").Lexer;
CharStream = require("antlr4/CharStreams").CharStream;
HTMLLexer = require("./lib/HTMLLexer").HTMLLexer;
JavaScriptBaseLexer = require("./lib/JavaScriptBaseLexer").JavaScriptBaseLexer;
JavaScriptLexer = require("./lib/JavaScriptLexer").JavaScriptLexer;
SQLLexer = require("./lib/SQLLexer").SQLLexer;

encodeHtmlText = x => x;//require('htmlencode').htmlEncode;
encodeHtmlAttribute = (a) => { console.log("encodeHtmlText --- " + a); return a; }
encodeJavaScript = (a) => { console.log("encodeHtmlText --- " + a); return a; }
encodeUriComponent = (a) => { console.log("encodeHtmlText --- " + a); return a; }

Token.prototype.getType = function() { return this.type; }
Token.prototype.getStartIndex = function() { return this.start; }
Token.prototype.getStopIndex = function() { return this.stop; }
Token.prototype.getText = function() { return this.text; }

const injections = require("./libprotection-js")['libprotection-js'].org.libprotection.injections;

const SafeString = injections.SafeString.Companion;
const Html = injections.languages.html.Html;
const JavaScript = injections.languages.html.JavaScript;
const LanguageService = injections.LanguageService.Companion;
const Range = injections.Range;
const FormatResult = injections.FormatResult.Companion;
const AttackDetectedException = injections.AttackDetectedException;
const sf = require("sf");
const assert = require('assert');

const LRU = require("lru-cache")
  , options = { max: 1024 }
  , cache = LRU(options)
  , otherCache = LRU(1024) // sets just the max size

function makeKtIterable(array) {
    var nextIndex = 0;

    return {
	  hasNext: function() { return array.length > nextIndex; },
      next : function() { return array[nextIndex++]; }
    };
}

function tryFormatEx(provider){

	let arugmentsWithNoProvider = Array.prototype.slice.call(arguments, 1);
	let keyString = JSON.stringify( { provider: Html.constructor.name, arugmentsWithNoProvider } );

	let cachedResult = cache.get(keyString);
	if(cachedResult !== undefined){
		return cachedResult;
	}

	console.log("Calculating " + keyString);

	let formatResult = sf(... Array.prototype.slice.call(arguments, 1));

	let ranges = formatResult.taintedRanges.map( it => new Range(it.lowerBound, it.upperBound) );
	ranges.iterator = function() { return makeKtIterable(this); };

	let sanitizeResult = LanguageService.trySanityze_5in3ik$(provider, formatResult.format, ranges);

	let result;
	if(sanitizeResult.success) {
		result = FormatResult.success_56j766$(sanitizeResult.tokens, sanitizeResult.sanitizedText.value)
	}else{
		let attackArgumentIndex = ranges.findIndex( it => it.overlaps_woksat$(sanitizeResult.attackToken.value.range) );
		//assert.notEqual(attackArgumentIndex, -1, "Cannot find attack argument for attack token.");
		let attackedIndex = Object.keys(args).findIndex( it => it == attackArgumentIndex );
		result = FormatResult.fail_7lvycu$(sanitizeResult.tokens, attackedIndex);
	}

	cache.set(keyString, result);

	return result;
}

function tryFormat(provider){
	let result = tryFormatEx(... arguments);
	if(result.isAttackDetected){
		return undefined;
	}else{
		return result.formattedString.value;
	}
}

function format(provider){
	let result = tryFormatEx(... arguments);
	if(result.isAttackDetected){
		throw new AttackDetectedException();
	}else{
		return result.formattedString.value;
	}
}

module.exports = { format, tryFormat, Html }