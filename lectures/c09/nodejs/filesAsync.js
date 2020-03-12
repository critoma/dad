/*
//blocking mode of reading a file
//Synchronous
var fs = require("fs");

var data = fs.readFileSync('./input.txt');
console.log("\n data = \n" + data.toString());
console.log("Program finished");
*/

//Non-blocking - asynchronous mode of reading a file
var fs = require("fs");

fs.readFile("./input.txt", function(err, data) {
   if (err) return console.error(err);

   console.log(data.toString());
});

console.log("Program finished");
