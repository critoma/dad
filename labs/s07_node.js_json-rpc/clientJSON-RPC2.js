// https://www.npmjs.com/package/json-rpc-2.0
// npm install json-rpc-2.0 express
/*
ism_ase_ro@cloudshell:~/blockchain/json-rpc$ npm ls
json-rpc@ /home/ism_ase_ro/blockchain/json-rpc
├── express@4.21.2
└── json-rpc-2.0@1.7.0
*/

import { JSONRPCClient } from "json-rpc-2.0";

// JSONRPCClient needs to know how to send a JSON-RPC request.
// Tell it by passing a function to its constructor. The function must take a JSON-RPC request and send it.
const client = new JSONRPCClient((jsonRPCRequest) =>
  fetch("http://localhost/json-rpc", {
    method: "POST",
    headers: {
      "content-type": "application/json",
    },
    body: JSON.stringify(jsonRPCRequest),
  }).then((response) => {
    if (response.status === 200) {
      // Use client.receive when you received a JSON-RPC response.
      return response
        .json()
        .then((jsonRPCResponse) => client.receive(jsonRPCResponse));
    } else if (jsonRPCRequest.id !== undefined) {
      return Promise.reject(new Error(response.statusText));
    }
  })
);

// Use client.request to make a JSON-RPC request call.
// The function returns a promise of the result.
client
  .request("echo", { text: "Hello, World!" })
  .then((result) => console.log(result));

// Use client.notify to make a JSON-RPC notification call.
// By definition, JSON-RPC notification does not respond.
client.notify("log", { message: "Hello, World!" });

client
  .request("xorArrays", { arr1: [1, 2, 3], arr2: [3, 2, 1] })
  .then((result) => console.log(result));
