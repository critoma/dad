/*
var events = require('events');

var eventEmitter = new events.EventEmitter();

var connectHandler = function connected() {
   console.log('connection with success!');
}
eventEmitter.on('connection', connectHandler); 

eventEmitter.emit('connection');

console.log("Program Finished!");
*/

/*
var events = require('events');

var eventEmitter = new events.EventEmitter();

var connectHandler = function connected() {
   console.log('connection with success!');

   eventEmitter.emit('data_received');
}
eventEmitter.on('connection', connectHandler); 

eventEmitter.on('data_received', function () {
  console.log("Data received with success!");
});

eventEmitter.emit('connection');

console.log("Program Finished!");
*/

/*
let myEventsModule = require("events");
let objEventEmitter = new myEventsModule.EventEmitter();
let fcnHandler = function connectionNow() {
   setTimeout(() => {
     console.log("Event handler with success");
   }, 1000);
};
objEventEmitter.on("myEventNow01", fcnHandler);
objEventEmitter.emit("myEventNow01");
console.log("prg events finished!");
*/

let events = require("events");
let eventEmitter = new events.EventEmitter();
let connectHandler = function connected() {
  console.log("connection with success!");
  setTimeout(() => {
    console.log("wait 1 sec");
    eventEmitter.emit("data_received");
  }, 1000);
};
eventEmitter.on("connection", connectHandler);
eventEmitter.on("data_received", () => {
  console.log("Data received with success!");
});
eventEmitter.emit("connection");
console.log("Program Finished!");


