var Animal = (function () {
    function Animal(name) {
        this.name = name;
    }
    // Methods
    Animal.prototype.doSomething = function () {
        console.log("I'm a " + this.name);
    };
    return Animal;
})();


var lion = new Animal("Lion");
lion.doSomething();


class AnimalES6S {
    constructor(name) {
        this.name = name;
    }

    doSomething() {
        console.log("I'm a ES6 simple " + this.name);
    }
}

var lionES6s = new AnimalES6S("Lion");
lionES6s.doSomething();



class AnimalES6A {
    constructor(name) {
        this.name = name;
        this._age = 0;
    }

    get age() {
        return this._age;
    }

    set age(value) {
        if (value < 0) {
            console.log("We do not support undead animals");
        }

        this._age = value;
    }

    doSomething() {
        console.log("I'm a ES6 quite advanced " + this.name);
    }
}

var lionES6a = new AnimalES6A("Lion");
lionES6a.doSomething();

lionES6a.age = 5;
console.log("lion.age = " + lionES6a.age);
console.log("lion._age = " + lionES6a._age);

lionES6a._age = 6;
console.log("lion.age = " + lionES6a.age);
console.log("lion._age = " + lionES6a._age);




var ageSymbol = Symbol();

class AnimalES6 {
    constructor(name) {
        this.name = name;
        this[ageSymbol] = 0;
    }

    get age() {
        return this[ageSymbol];
    }

    set age(value) {
        if (value < 0) {
            console.log("We do not support undead animals");
        }

        this[ageSymbol] = value;
    }

    doSomething() {
        console.log("I'm a " + this.name);
    }
}

var lionES6 = new AnimalES6("Lion");
lionES6.doSomething();

lionES6.age = 7;
console.log("lion.age = " + lionES6.age);
console.log("lion.ageSymbol = " + lionES6.ageSymbol);

lionES6a.ageSymbol = 8;
console.log("lion.age = " + lionES6.age);
console.log("lion.ageSymbol = " + lionES6.ageSymbol);



'use strict' 
class Person{ } 
var obj = new Person() 
var isPerson = obj instanceof Person; 
console.log(" obj is an instance of Person " + isPerson); 



'use strict' 
class PrinterClass { 
   doPrint() {
      console.log("doPrint() from Parent called…") 
   } 
}  
class StringPrinter extends PrinterClass { 
   doPrint() { 
      super.doPrint() 
      console.log("doPrint() is printing a string…") 
   } 
} 
var obj = new StringPrinter() 
obj.doPrint()
