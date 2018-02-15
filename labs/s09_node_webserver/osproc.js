var os = require("os");

// Endianness
console.log('endianness : ' + os.endianness());

// OS type
console.log('type : ' + os.type());

// OS platform
console.log('platform : ' + os.platform());

// Total system memory
console.log('total memory : ' + os.totalmem() + " bytes.");

// Total free memory
console.log('free memory : ' + os.freemem() + " bytes.");

//if (process.argv.length <= 2) {
if (process.argv.length != 0) {
    console.log("Usage: " + __filename + " SOME_PARAM");
    //process.exit(-1);
}

console.log("process argv = " + process.argv);

var param = process.argv[2];
 
console.log('Param: ' + param);