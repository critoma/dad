buf = new Buffer(26);

for (var i = 0; i < 26; i++) {
   buf[i] = i + 97;
}

console.log( buf.toString() );
console.log( buf.toString('ascii', 0, 5) );
// console.log( buf.toJSON() );

var buffer1 = new Buffer("node.js recap ");
var buffer2 = new Buffer("It is simple to remeber!");
var buffer3 = Buffer.concat([buffer1, buffer2]);
console.log("buffer3 = " + buffer3.toString());

//var buff = new Buffer(2);
//buff[0] = 15;
//buff[1] = 25;

//var buff3 = Buffer.concat([buf, buff]);
//console.log(buff3);

var buf1 = new Buffer('ABC');
var buf2 = new Buffer('ABCD');
var result = buf1.compare(buf2);
//var result = Buffer.compare(buf1, buf2);

if (result < 0) {
   console.log(buf1 + "is less than " + buf2);
} else if (result == 0) {
   console.log(buf1 + "is same as " + buf2);
} else {
   console.log(buf1 + "is same greater than " + buf2);
}

var bufferR = new Buffer("Cyber Security master");
var bufferR2 = bufferR.slice(0, 14); //startIdx, stopIdx
console.log("bufferR2 = " + bufferR2.toString());

var s1 = "Ana"; var s2 = "Ana"
if (s1 == s2) {
   console.log("==");  
} 
if (s1 === s2) {
   console.log("===");
}

