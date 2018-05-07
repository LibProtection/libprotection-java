kotlin = require("kotlin");

'use strict'

Token = require("antlr4/Token").Token;
InputStream = require("antlr4/InputStream").InputStream;
Lexer = require("antlr4/Lexer").Lexer;
CharStream = require("antlr4/CharStreams").CharStream;
HTMLLexer = require("./lib/HTMLLexer").HTMLLexer;
JavaScriptBaseLexer = require("./lib/JavaScriptBaseLexer").JavaScriptBaseLexer;
JavaScriptLexer = require("./lib/JavaScriptLexer").JavaScriptLexer;
SQLLexer = require("./lib/SQLLexer").SQLLexer;

const secure_filters = require('secure-filters');

encodeHtmlText = secure_filters.html;
encodeHtmlAttribute = secure_filters.html;
encodeJavaScript = secure_filters.js;
encodeUriComponent = secure_filters.uri;

Token.prototype.getType = function() { return this.type; }
Token.prototype.getStartIndex = function() { return this.start; }
Token.prototype.getStopIndex = function() { return this.stop; }
Token.prototype.getText = function() { return this.text; }

const injections = require("./lib/libprotection-js")['libprotection-js'].org.libprotection.injections;

const FilePath = injections.languages.filepath.FilePath;
const Html = injections.languages.html.Html;
const JavaScript = injections.languages.javascript.JavaScript;
const Sql = injections.languages.sql.Sql;
const Url = injections.languages.url.Url;

const LanguageService = injections.LanguageService.Companion;
const Range = injections.Range;
const FormatResult = injections.FormatResult.Companion;
const AttackDetectedException = injections.AttackDetectedException;
const sf = require("./lib/sf");
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
	let keyString = JSON.stringify( { provider: provider.constructor.name, arugmentsWithNoProvider } );
	
	let cachedResult = cache.get(keyString);
	if(cachedResult !== undefined){
		return cachedResult;
	}
	
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
		let attackedIndex = Object.keys(arugmentsWithNoProvider).findIndex( it => it == attackArgumentIndex );
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

module.exports = { format, tryFormat, FilePath, Html, JavaScript, Sql, Url, AttackDetectedException }