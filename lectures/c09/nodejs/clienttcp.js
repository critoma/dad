var net = require('net');

//var client = net.connect({port: 8090}, '127.0.0.1', function () {
var client = net.connect(8090, '172.20.10.3', function () {
   console.log("client conncted to the server!");
   client.write('Hello, server! I am the client Chuck Norris...');
});

client.on('data', function (data) {
    console.log("Received from server = \n" + data.toString());
    client.end();
    //client.destroy();
});

client.on('end', function() {
    console.log("client is disconnected from the server!");
});

// Add a 'close' event handler for the client socket
client.on('closed', function() {
    console.log('Connection closed');
});

