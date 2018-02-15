var fs = require('fs');


console.log("Going to create directory /tmp/test");
fs.mkdir('/tmp/test',function(err){
   if (err) {
      return console.error(err);
   }
   console.log("Directory created successfully!");
});


console.log("Going to read directory /tmp");
fs.readdir("/tmp/",function(err, files){
   if (err) {
      return console.error(err);
   }
   files.forEach( function (file){
      console.log( file );
   });
});


console.log("Going to delete directory /tmp/test");
fs.rmdir("/tmp/test",function(err){
   if (err) {
      return console.error(err);
   }
   console.log("Going to read directory /tmp");
   
   fs.readdir("/tmp/",function(err, files){
      if (err) {
         return console.error(err);
      }
      files.forEach( function (file){
         console.log( file );
      });
   });
});




console.log('write into the existing file test.txt');
var wdata = 'Easy to write\n node.js test text!\n\n';
fs.writeFile('./test.txt', wdata, function(err) {
   if (err) {
      return console.error(err);
   }

   console.log("Data written with success!");
});


console.log("Going to get file info!");
fs.stat('./test.txt', function (err, stats) {
   if (err) {
       return console.error(err);
   }
   console.log(stats);
   console.log("Got file info successfully!");
   
   // Check file type
   console.log("isFile ? " + stats.isFile());
   console.log("isDirectory ? " + stats.isDirectory());    
});


/*
var buf = new Buffer(1024);

fs.open('test.txt', 'r+', function(err, fd) {
   if (err) {
      return console.error(err);
   }

   console.log('File opened with success! proceed with read!');
   fs.read(fd, buf, 0, buf.length, 0, function (err, noBytes) {
      if (err) {
         return console.error(err);
      }
      // display only the read bytes
      if (noBytes > 0) {
         console.log(buf.slice(0, noBytes).toString());
      } 
      // close the opened file by file descriptor
      fs.close(fd, function(err) {
         if (err) {
            return console.error(err);
         }
         console.log("File closed!");
      });
   });
});
*/



let filepath = "./test.txt";

console.log('read from an existing file: test.txt');
fs.open(filepath, 'r', function(err, fd) {
    fs.fstat(fd, function(err, stats) {
        var bufferSize=stats.size,
            chunkSize=512,
            buffer=new Buffer(bufferSize),
            bytesRead = 0;

        while (bytesRead < bufferSize) {
            if ((bytesRead + chunkSize) > bufferSize) {
                chunkSize = (bufferSize - bytesRead);
            }
            fs.read(fd, buffer, bytesRead, chunkSize, bytesRead);
            bytesRead += chunkSize;
        }
        console.log(buffer.toString('utf8', 0, bufferSize));

        //fs.close(fd);
        fs.close(fd, function(err) {
           if (err) {
              return console.error(err);
           }
           console.log("File closed!");
        });
    });
});




