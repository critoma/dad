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


