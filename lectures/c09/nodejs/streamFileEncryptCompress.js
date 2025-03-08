var crypto = require('crypto');
var fs = require('fs');
var zlib = require('zlib');
//var aesEcb = require('aes-ecb');
//const iv = crypto.randomBytes(16);
var password = new Buffer(process.env.PASS || 'passwordpassword');
var encryptStream = crypto.createCipheriv('aes-128-ecb', password, null);

var gzip = zlib.createGzip();
//var readStream = fs.createReadStream(**filename); // current file
var readStream = fs.createReadStream('input.txt'); // current file
//var writeStream = fs.createWriteStream(**dirname + '/out.gz');
var writeStream = fs.createWriteStream('.' + '/out.gz');

readStream   // reads current file
  .pipe(encryptStream) // encrypts
  .pipe(gzip)  // compresses
  .pipe(writeStream)  // writes to out file
  .on('finish', () => {  // all done
    console.log('done');
  });
