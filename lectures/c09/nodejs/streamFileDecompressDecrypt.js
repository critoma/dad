var crypto = require('crypto');
var fs = require('fs');
var zlib = require('zlib');

var password = new Buffer(process.env.PASS || 'passwordpassword');
var decryptStream = crypto.createDecipheriv('aes-128-ecb', password, null);

var gzip = zlib.createGunzip();
//var readStream = fs.createReadStream(__dirname + '/out.gz');
var readStream = fs.createReadStream('.' + '/out.gz');
/*
readStream   // reads current file
  .pipe(gzip)  // uncompresses
  .pipe(decryptStream) // decrypts
  .pipe(process.stdout)  // writes to terminal
  .on('finish', function () {  // finished
    console.log('done');
  });
*/

readStream   // reads current file
  .pipe(gzip)  // uncompresses
  .pipe(decryptStream) // decrypts
  .pipe(process.stdout)  // writes to terminal
  .on('finish', () => {  // finished
    console.log('done');
  });
