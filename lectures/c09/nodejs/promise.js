/*
var promise = new Promise(function(resolve , reject) {    
   // do a thing, possibly async , then..  
   if(//everthing turned out fine //)    resolve("stuff worked");  
   else     
   reject(Error("It broke"));  
});  
return promise;
// Give this to someone
*/

function getSum(n1, n2) {   
   var isAnyNegative = function() {   
      return n1 < 0 || n2 < 0;   
   }   
   var promise = new Promise(function(resolve, reject) {   
      if (isAnyNegative()) {   
         reject(Error("Negatives not supported"));   
      }   
      resolve(n1 + n2)
   });   
   return promise;   
} 

getSum(5, 6)
.then(

function (result) {   
   console.log(result);   
},   
function (error) {   
   console.log(error);   
}

);

// Since the return type of the getSum() is a Promise, 
// we can actually have multiple ‘then’ statements. The first 'then' will have a return statement.

getSum(10, 20)   
.then(function(result) {   
   console.log(result);   
   return getSum(30, 40); 
   // this returns another promise   
},   
function(error) {   
   console.log(error);   
})   
.then(function(result) {   
   console.log(result);   
}, 
function(error) {   
   console.log(error);
});  
