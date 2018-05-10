var LP = require('../index.js');

function getDataPoint(provider, result, format){
	return {
		provider: provider,
		result: result,
		format: format,
		arguments: Array.prototype.slice.call(arguments, 3),
		get isAttack() { return result === null; }
	}
}

dataPoints = [
            //Valid
            getDataPoint(LP.Html,
				"<a href=\'Default.aspx\' onclick=\'alert(\"Hello&#92;x20from&#92;x20embedded&#92;x20JavaScript&#92;x20code&#92;x21\");return false\'>This site&#39;s home page</a>",
				"<a href=\'{0}\' onclick=\'alert(\"{1}\");return false\'>{2}</a>", "Default.aspx", "Hello from embedded JavaScript code!", "This site\'s home page"),
            getDataPoint(LP.JavaScript,
				"operationResult.innerText = \'operationResult.innerText\\x20\\x3D\\x20\\x27Hello\\x20from\\x20internal\\x20JavaScript\\x20code\\x21\\x27\\x3B\';",
				"operationResult.innerText = \'{0}\';", "operationResult.innerText = \'Hello from internal JavaScript code!\';"),
            getDataPoint(LP.Sql,
				"SELECT * FROM myTable WHERE id = 1 AND myColumn = 'value1'",
				"SELECT * FROM myTable WHERE id = {0} AND myColumn = \'{1}\'",  1, "value1"),
            getDataPoint(LP.Url,
				"Assets/jsFile.js",
				"{0}/{1}", "Assets", "jsFile.js"),
            getDataPoint(LP.FilePath,
				"C:\\inetpub\\playground.libprotection.org\\Assets\\textFile.txt",
				"C:\\inetpub\\playground.libprotection.org\\Assets\\{0}", "textFile.txt"),
            //Attacks
            getDataPoint(LP.Html, null, "<a href={0} />", "<br>"),
            getDataPoint(LP.JavaScript, null, "operationResult.innerText = {0};", "\' <br>"),
            getDataPoint(LP.Sql, null, "SELECT * FROM myTable WHERE id = {0}", "1 OR 1==1 --"),
            getDataPoint(LP.Url, null, "{0}/{1}", "../Asserts", "jsFile.js"),
            getDataPoint(LP.FilePath, null, "C:\\Assets\\{0}", "..\\jsFile.js"),
            //safe modifier
            getDataPoint(LP.Html, ":safe", ":safe"),
            getDataPoint(LP.Html, "&#58;safe&lt;br&gt;", "{0}", ":safe<br>"),
            getDataPoint(LP.Html, "&lt;br&gt;xxx:safe", "{0}xxx:safe", "<br>"),
            getDataPoint(LP.Html, "<br>", "{0:safe}", "<br>"),
            getDataPoint(LP.Html, "<br>", "{0:SaFe}", "<br>"),
            getDataPoint(LP.Html, "<br>:safe", "{0:safe}:safe", "<br>"),
            getDataPoint(LP.Html, "<br>&lt;br&gt;", "{0:safe}{1}", "<br>", "<br>"),
            getDataPoint(LP.Html, "&lt;br&gt;<br>&lt;br&gt;", "{0}{1:safe}{2}", "<br>", "<br>", "<br>")
];

function tester(test, dataPoint){
	const tryFormatResult = LP.tryFormat(dataPoint.provider, dataPoint.format, ...dataPoint.arguments);

	if(dataPoint.isAttack){
		test.ok(tryFormatResult === undefined);
		
		test.throws( () => { LP.format(dataPoint.provider, dataPoint.format, ...dataPoint.arguments); }, LP.AttackDetectedException );
		
	}else{
		test.ok(tryFormatResult !== undefined);
		test.equal(tryFormatResult, dataPoint.result);

		const formatResult = LP.format(dataPoint.provider, dataPoint.format, ...dataPoint.arguments);
		test.equal(tryFormatResult, dataPoint.result);

	}

	test.done();
}

module.exports = {};

dataPoints.forEach((dataPoint, index) => { module.exports["Test case #" + (index + 1)] = test => tester(test, dataPoint); });
