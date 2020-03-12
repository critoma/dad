console.log("try-catch mechanism");
var a = 100; 
var b = 0; 
try { 
   if (b == 0 ) { 
      throw("Divide by zero error."); 
   } else { 
      var c = a / b; 
   } 
} 
catch( e ) { 
   console.log("Error: " + e ); 
}

console.log("Simple Array");
var alphas; 
alphas = ["1","2","3","4"] 
console.log(alphas[0]); 
console.log(alphas[1]);

console.log("Single Statement Declaration and Initialization");
var nums = [1,2,3,3] 
console.log(nums[0]); 
console.log(nums[1]); 
console.log(nums[2]); 
console.log(nums[3]);

console.log("Array Object");
var arr_names = new Array(4)  
for(var i = 0;i<arr_names.length;i++) { 
   arr_names[i] = i * 2 
   console.log(arr_names[i]) 
}

console.log("Array Constructor Accepts Comma-separated Values");

var names = new Array("Mary","Tom","Jack","Jill") 
for(var i = 0;i<names.length;i++) { 
   console.log(names[i]) 
}

console.log("Array push and pop methods");
var numbersA = new Array(1, 4, 9);
var length = numbersA.push(10); 
console.log("new numbers is : " + numbersA );  
length = numbersA.push(20); 
console.log("new numbers is : " + numbersA );
numbersA = [1, 4, 9]; 
var element = numbersA.pop(); 
console.log("element is : " + element );  
var element = numbersA.pop(); 
console.log("element is : " + element );
  

console.log("ES6 - Array methods");
var numbers = [1, 2, 3]; 
var oddNumber = numbers.find((x) => x % 2 == 1); 
console.log(oddNumber); // 1

numbers = [1, 2, 3]; 
oddNumber = numbers.findIndex((x) => x % 2 == 1); 
console.log(oddNumber); // 0 

"use strict" 
var nums = [1001,1002,1003,1004] 
for(let j in nums) { 
   console.log(nums[j]) 
}

var arr = [12,13] 
var[x,y] = arr 
console.log(x) 
console.log(y)

console.log("maps ...");
var mmap = new Map(); 
mmap.set(1,true); 
console.log(mmap.has("1")); //false 

mmap.set("1",true); 
console.log(mmap.has("1")); //true

var roles = new Map(); 
roles.set('r1', 'User') 
.set('r2', 'Guest') 
.set('r3', 'Admin'); 
console.log(roles.has('r1'))

'use strict' 
var roles = new Map([ 
   ['r1', 'User'], 
   ['r2', 'Guest'], 
   ['r3', 'Admin'], 
]);

for(let r of roles.entries()) 
   console.log(`${r[0]}: ${r[1]}`);



// You can iterate over entries, keys, values explicitly
var map = new Map([[1,'one'],[2,'two'],[3,'three']]); 
var iterator = map.entries(); 
if (iterator.done != true) {
   console.log(iterator.next());
}
for (var entry of map.entries()) {
  console.dir(entry);
}

map = new Map([[1,'one'],[2,'two'],[3,'three']]);
iterator = map.values(); 
//for (var val of map.values()) {
for (var val of iterator) {
  console.log("val = " + val);
}

map = new Map([[1,'one'],[2,'two'],[3,'three']]); 
var iterator = map.keys(); 
//for (var key of map.keys()) 
for (var key of iterator) {
  console.log("key = " + key);
}


var myMap = {
    partnr1: ['modelA', 'modelB', 'modelC'],
    partnr2: ['modelA', 'modelB', 'modelC']
};
for (var m in myMap){
    for (var i=0; i<myMap[m].length; i++){
        console.log("myMap["+m+"]["+i+"] = " + myMap[m][i]);
    }
}


var x = new Map([['a', 'value A'], ['b', 'value B'], ['c', 'value C']]);

x.forEach(function(value, key, map) {
  console.log('key: "' + key + '", value: "' + value + '"');
});

