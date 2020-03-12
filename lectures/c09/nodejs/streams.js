/*
var fs = require('fs');
var data = '';

// create a readable stream - is an EventEmitter
var readerStream = fs.createReadStream('./input.txt');
readerStream.setEncoding('UTF8');

readerStream.on('data', function(chunk) {
   data += chunk;
   console.log('chunk = ' + chunk);
});
readerStream.on('end', function() {
   console.log('\n final data = \n' + data);
});
readerStream.on('error', function(err) {
   console.log(err.stack);
});

console.log('Program finished!');
*/


var fs = require('fs');
var data = '';

// reading a text file using streams
// create a readable stream - is an EventEmitter
var readerStream = fs.createReadStream('./input.txt');
readerStream.setEncoding('UTF8');

readerStream.on('data', function(chunk) {
   data += chunk;
   console.log('chunk = ' + chunk);
});
readerStream.on('end', function() {
   console.log('\n final data = \n' + data);
});
readerStream.on('error', function(err) {
   console.log(err.stack);
});



// write a string with a stream into a file: output.txt
// using EventEmitter writeable streams
data = 'node.js is easy and simple \n to learn!?';

// create writeable stream
var writerStream = fs.createWriteStream('output.txt');

// write the data into the stream with UTF8 encoding
writerStream.write(data, 'UTF8');

// mark with end event, the end of the data
writerStream.end();

// Handle stream writeable EventEmitter's events: finish and error
writerStream.on('finish', function() {
   console.log('Write is completed!');
});

writerStream.on('error', function(err) {
   console.log(err.stack);
});



var rStream = fs.createReadStream('input.txt');
var wStream = fs.createWriteStream('output2.txt');

rStream.pipe(wStream);

console.log('Program finished!');



