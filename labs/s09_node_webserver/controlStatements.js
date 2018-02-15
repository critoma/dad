// if statement
var  num = 5 
if (num>0) { 
   console.log("number is positive") 
}

// if-else
num = 12; 
if (num % 2==0) { 
   console.log("Even"); 
} else { 
   console.log("Odd"); 
}

// if nested
num = 2 
if(num > 0) { 
   console.log(num+" is positive") 
} else if(num < 0) { 
   console.log(num+" is negative") 
} else { 
   console.log(num+" is neither positive nor negative") 
}

// switch statement
var grade = "A"; 
switch(grade) { 
   case "A": { 
      console.log("Excellent"); 
      break; 
   } 
   case "B": { 
      console.log("Good"); 
      break; 
   }
   case "C": { 
      console.log("Fair"); 
      break;    
   } 
   case "D": { 
      console.log("Poor"); 
      break; 
   }  
   default: { 
      console.log("Invalid choice"); 
      break;              
   } 
} 

// for loop statement
for (var idx = 0; idx < 5; idx++) {
   console.log(" idx = " + idx);
}

//for...in loop
var obj = {a:1, b:2, c:3};  
for (var prop in obj) { 
   console.log(obj[prop]); 
}

//for...of loop
for (let val of[12 , 13 , 123]) {   
   console.log(val) 
}

// while loop
var i = 0
while(i < 5) { 
   console.log(" i = " + i);
   i++; 
} 

// do - while loop
var n = 4;   
do { 
   console.log("n = " + n); 
   n--; 
} 
while(n>=0); 
