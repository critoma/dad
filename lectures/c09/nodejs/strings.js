var str1 = new String( "This is string one" );  
var index = str1.indexOf( "string" ); 
console.log("indexOf found String :" + index );   

var index = str1.indexOf( "one" ); 
console.log("indexOf found String :" + index );  

function employee(id, name) { 
   this.id = id; 
   this.name = name; 
} 
var emp = new employee(123, "Smith"); 
employee.prototype.email = "smith@abc.com"; 

console.log("Employee 's Id: " + emp.id); 
console.log("Employee's name: " + emp.name);
console.log("Employee's Email ID: " + emp.email);