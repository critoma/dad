// 
// npm install typescript
// rm -rf node_modules
// rm -rf *.json
// npm install -g typescript
// tsc hello.ts
// cat hello.js # generated file
// node hello.js
//
// alternatively:
// npm install typescript --save-dev --global && npm i ts-node -g
// ts-node hello.ts

function helloF(person: string){
    return "Hello, " + person;
}
 
let user: string = "My Test User";
const result = helloF(user);
console.log("Result ", result);
