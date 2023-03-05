var net = require('net');

var server = net.createServer(function(sock) {
   console.log("a client is connected");
   
   sock.on('end', function() {
      console.log("client is disconnected");
   });

   sock.on('data', function(recvData) {
      console.log("client sent to the server = " + recvData);
   });

   // Add a 'closed' event handler to this instance of socket
   sock.on('closed', function(data) {
       console.log('CLOSED: ' + sock.remoteAddress +' '+ sock.remotePort);
   });

   sock.write('Hello World from the Server\r\n');

   //pipe makes the socket writes whatever it reads from the client back to him, hence the "Echo server".
   //Socket is a stream, so basically, it connects its input to its output directly.
   // this is for echo server
   sock.pipe(sock);
});

server.listen(8090, '172.17.0.3', function() {
//server.listen(8090, function() {
   console.log("server is listening ...");
});
