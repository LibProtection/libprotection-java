'use strict'

const LP = require('./index.js');

let result;
result = LP.format(LP.Html, "<a href=\'{0}\' onclick=\'alert(\"{1}\");return false\'>{2}</a>", "Default.aspx", "Hello from embedded JavaScript code!", "This site's home page");
console.log(result);
result = LP.format(LP.JavaScript, "operationResult.innerText = \'{0}\';", "operationResult.innerText = 'Hello from internal JavaScript code!';");
console.log(result);
result = LP.format(LP.Sql, "SELECT * FROM myTable WHERE id = {0} AND myColumn = \'{1}\'",  1, "value1");
console.log(result);
result = LP.format(LP.Url, "{0}/{1}", "Assets", "jsFile.js");
console.log(result);
result = LP.format(LP.FilePath, "C:\\inetpub\\playground.libprotection.org\\Assets\\{0}", "textFile.txt");
console.log(result);
