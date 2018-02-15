var crypto = require('crypto');
var fs = require('fs');
var zlib = require('zlib');

var password = new Buffer(process.env.PASS || 'password');
var encryptStream = crypto.createCipher('aes-256-cbc', password);

var gzip = zlib.createGzip();
//var readStream = fs.createReadStream(**filename); // current file
var readStream = fs.createReadStream('original.txt'); // current file
//var writeStream = fs.createWriteStream(**dirname + '/out.gz');
var writeStream = fs.createWriteStream('.' + '/out.gz');

readStream   // reads current file
  .pipe(encryptStream) // encrypts
  .pipe(gzip)  // compresses
  .pipe(writeStream)  // writes to out file
  .on('finish', function () {  // all done
    console.log('done');
  });
