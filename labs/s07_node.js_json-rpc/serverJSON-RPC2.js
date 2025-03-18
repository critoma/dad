// https://www.npmjs.com/package/json-rpc-2.0
// npm install --save json-rpc-2.0 express
/*
ism_ase_ro@cloudshell:~/blockchain/json-rpc$ npm ls
json-rpc@ /home/ism_ase_ro/blockchain/json-rpc
├── express@4.21.2
└── json-rpc-2.0@1.7.0
*/

const express = require("express");
const bodyParser = require("body-parser");
const { JSONRPCServer } = require("json-rpc-2.0");

const server = new JSONRPCServer();

// First parameter is a method name.
// Second parameter is a method itself.
// A method takes JSON-RPC params and returns a result.
// It can also return a promise of the result.
server.addMethod("echo", ({ text }) => text);
server.addMethod("log", ({ message }) => console.log(message));

// Register the XOR function as a method in the server
server.addMethod('xorArrays', ({arr1, arr2}) => {
  // Ensure both arrays have the same length
  if (arr1.length !== arr2.length) {
    throw new Error('Arrays must have the same length');
  }

  // XOR operation on each corresponding pair of elements
  // return arr1.map((value, index) => value ^ arr2[index]);
  var arr = new Array(arr1.length);
  for (var i = 0; i < arr.length; i++)
      arr[i] = arr1[i] ^ arr2[i];
  return arr;
});

const app = express();
app.use(bodyParser.json());

app.post("/json-rpc", (req, res) => {
  const jsonRPCRequest = req.body;
  // server.receive takes a JSON-RPC request and returns a promise of a JSON-RPC response.
  // It can also receive an array of requests, in which case it may return an array of responses.
  // Alternatively, you can use server.receiveJSON, which takes JSON string as is (in this case req.body).
  server.receive(jsonRPCRequest).then((jsonRPCResponse) => {
    if (jsonRPCResponse) {
      res.json(jsonRPCResponse);
    } else {
      // If response is absent, it was a JSON-RPC notification method.
      // Respond with no content status (204).
      res.sendStatus(204);
    }
  });
});

app.listen(80);
