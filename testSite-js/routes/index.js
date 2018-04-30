const express = require('express');
const router = express.Router();

const LP = require("../lib/libprotection/index.js")

function Example(operation, formatFunc, format, parameters, tagBuilder) {
	return {
		get Operation() { return operation; },
		get FormatFunc() { return formatFunc; },
		get Format() { return format; },
		get Parameters() { return parameters; },
		get TagBuilder() { return tagBuilder; }
	};
}

function FormatResult(successfully, formattedValue){
	return {
		get Successfully() { return successfully; },
		get FormattedValue() { return formattedValue; }
	}
}

function Result(formatResult, operationResult) {
	return {
		get FormatResult() { return formatResult; },
		get OperationResult() { return operationResult; }
	}
}

const examples = {
	Html: Example(
		"Renders the given HTML markup on the client side.",
		LP.format.bind(null, LP.Html),
		"<a href='{0}' onclick='alert(\"{1}\");return false'>{2}</a>",
		"Default.aspx\r\nHello from embedded JavaScript code!\r\nThis site's home page",
		s => s
	)
};

function getLowerCasedProperty(obj, key){
	
	if(key in obj){
		return obj[key];
	}
	
	for(let objKey in obj){
		if(objKey.toLowerCase() === key.toLowerCase()){
			return obj[objKey];
		}
	}
	return undefined;
}

function getResultsFor(example, format, parameters) {
	let formatResult = example.FormatFunc(
		format,
		... parameters.replace("\r\n", "\n").replace("\r", "\n").split("\n")
	);

	return Result(formatResult, example.TagBuilder(formatResult));
}

function rootHandler(req, res, next) {
  const currentExample = examples[getLowerCasedProperty(req.query, "Id")];
  res.render('index',
  { 
	Title: 'LibProtection Test Page', 
	Examples: examples,
	get Id() { 
		let id = getLowerCasedProperty(req.query, "id");
		return id ? id : Object.keys(examples)[0];
	},
	get Parameters() { return getLowerCasedProperty(req.query, "Parameters"); },
	get Format() { return getLowerCasedProperty(req.query, "Format"); },
	get InputsAreDisabled() { return false; },
	get ShowDisclaimer() {
		res.cookie("showDisclaimer", "false");
		return (req.cookies.showDisclaimer === undefined || req.cookies.showDisclaimer != "false");
	},
	getResultsFor: getResultsFor
  });
}


/* GET home page. */
router.get('/', rootHandler);

module.exports = router;
