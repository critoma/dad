
function add0(arg1, arg2) {
   var r = arg1 + arg2;
   return r;
}

var rval = add0(2, 8);
console.log('rval = ' + rval);



function add1(arg1, arg2, callback) {
   var r = arg1 + arg2;
   callback(r);
   console.log("add1 is finished");
}


function fcalled2(num) {
   // this non-anonymous function will run when the callback is called
   console.log("callback called from add1, num = " + num);
}

add1(5, 15, fcalled2);



function add2(arg1, arg2, callback) {
   var r = arg1 + arg2;
   callback(r);
   console.log("some_function2 is finished");
}


add2(25, 3, function (num) {
   // this is an anonymous function called back
   console.log("callback called  from add2, num = " + num);
});



function add3(arg1, arg2, callback) {
   var r = arg1 + arg2;
   callback(r);
   console.log("add3 is finished");
}


add3(25, 2, (num) => {
   // this is an anonymous call back function clojure as lambda expression which is called back from some_function3
   console.log("callback called from add3, num = " + num);
   console.log("This is an anonymous call back function clojure as lambda expression which is called back from some_function3.");
});


