'use strict'

const LP = require('./index.js');

//JavaScript
let args = "dasd";//{aaa: "<xxx>"};
let args2 = "xxx"//"<bc>";//{aaa: "<xxx>"};
let result = LP.format(LP.Html, "xxx{0}yyy{1}", args, args2);

result = LP.format(LP.Html, "xxx{0}yyy{1}", args, args2);


