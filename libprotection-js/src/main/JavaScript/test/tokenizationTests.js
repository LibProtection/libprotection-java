'use strict'

const LP = require('../index.js');
const fs = require('fs');
const path = require('path');

function convertToken(lpToken){
	return {
		IsTrivial: lpToken.isTrivial,
		Text: lpToken.text,
		LanguageProvider: lpToken.languageProvider.constructor.name,
		TypeName: lpToken.type.constructor.name,
		TypeValue: lpToken.type.name$,
		RangeLowerBound: lpToken.range.lowerBound,
		RangeUpperBound: lpToken.range.upperBound
	};
}

function tester(test, filename){

	const testCase = JSON.parse(fs.readFileSync(filename, 'utf8'));

	let result = LP.TestPurpose.tryFormatEx(LP[testCase.LanguageProvider], testCase.Format, ...testCase.Arguments);

	const compareResult = {
		IsAttackDetected: result.isAttackDetected,
		InjectionPointIndex: result.injectionPointIndex,
		FormattedString: result.formattedString.isPresent ? result.formattedString.value : null,
		Tokens: result.tokens.map(convertToken)
	};
	

	test.deepEqual(testCase.Result, compareResult);
	test.done();
}


const testPath = path.resolve("test", "format-test-cases");
const testCases = fs.readdirSync(testPath).map( filename => { 
	return {
		Name: "Test case " + filename,
		Path: path.resolve("test", "format-test-cases", filename)
	}
} );

module.exports = {};
testCases.forEach(testCase => { module.exports[testCase.Name] = test => tester(test, testCase.Path); });
