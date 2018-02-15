var http = require('http');

/*
//version 1
//http.get("http://www.google.com/index.html", function(res) {
http.get("http://www.ism.ase.ro", function(res) {
  console.log("Got response: " + res.statusCode);
  res.on("data", function(chunk) {
    console.log("BODY: " + chunk);
  });
}).on('error', function(e) {
  console.log("Got error: " + e.message);
});
*/

// version 2
/*
var options = {
  host: 'www.google.com',
  port: 80,
  path: '/index.html'
};
*/
var options = {
  host: 'www.ism.ase.ro',
  port: 80,
  path: '/'
};

http.get(options, function(res) {
  var body = '';
  res.on('data', function(chunk) {
    body += chunk;
  });
  res.on('end', function() {
    console.log(body);
  });
}).on('error', function(e) {
  console.log("Got error: " + e.message);
}); 

