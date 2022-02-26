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

getSum(22, 1).then(
    function (result) {   
        console.log(result);   
    },   
    function (error) {   
        console.log(error);   
    }
)

async function getSumA(n1, n2) {
    let result = await getSum(n1, n2)
  
    console.log("result = " + result)
}
  
getSumA(23, 7)
    .catch(e => {
      console.log('There has been a problem with your operation: ' + e.message);
    })
