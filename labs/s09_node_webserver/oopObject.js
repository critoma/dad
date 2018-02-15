// 1. Object initializer
console.log("1. Object initializer");
var person = { 
   firstname:"Tom", 
   lastname:"Hanks", 
   func:function(){return "Hello!!"},    
}; 
//access the object values 
console.log(person.firstname)   
console.log(person.lastname) 
console.log(person.func())

// 2. Object() Constructor
console.log("2. Object() Constructor");
var myCar = new Object(); 
myCar.make = "Ford"; //define an object 
myCar.model = "Mustang"; 
myCar.year = 1987;  

console.log(myCar["make"]) //access the object property 
console.log(myCar["model"]) 
console.log(myCar["year"])

// 3. Constructor function
console.log("3. Constructor function");
function Car() { 
   this.make = "Ford" 
   this.model = "F123" 
}  
var obj = new Car() 
console.log(obj.make) 
console.log(obj.model)

// 4. The Object.create Method
console.log("4. The Object.create Method");
var roles = { 
   type: "Admin", // Default value of properties 
   displayType : function() {  
      // Method which will display type of role 
      console.log(this.type); 
   } 
}  
// Create new role type called super_role 
var super_role = Object.create(roles); 
super_role.displayType(); // Output:Admin  

// Create new role type called Guest 
var guest_role = Object.create(roles); 
guest_role.type = "Guest"; 
guest_role.displayType(); // Output:Guest

// 5. The Object.assign() Function
"use strict" 
console.log("5. The Object.assign() Function - cloning an object");
var det = { name:"Tom", ID:"E1001" }; 
var copy = Object.assign({}, det); 
console.log(copy);  
for (let val in copy) { 
   console.log(copy[val]) 
}

// 6. Deleting properties
console.log("6. Deleting properties");
// Creates a new object, myobj, with two properties, a and b. 
var myobj = new Object; 
myobj.a = 5; 
myobj.b = 12; 

// Removes the ‘a’ property 
delete myobj.a; 
console.log ("a" in myobj) // yields "false"

// 7. Comparing objects
console.log("7. Comparing objects");
var vOb1 = {name: "Tom"}; 
var vOb2 = {name: "Tom"}; 
console.log(vOb1 == vOb2)  // return false 
console.log(vOb2 === vOb2)  // return false

var vOb3 = {name: "Tom"}; 
var vOb4 = vOb3  

console.log(vOb3 == vOb4) // return true 
console.log(vOb3 === vOb4) // return true

// 8. Object De-structuring
console.log("8. Object De-structuring");
var emp = { name: 'John', Id: 3 } 
console.log("emp = " + JSON.stringify(emp));
var {name, Id} = emp 
console.log(name) 
console.log(Id)
