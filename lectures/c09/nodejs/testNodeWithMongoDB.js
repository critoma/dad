// npm install mongodb
const mongo = require('mongodb').MongoClient
const url = 'mongodb://localhost:27017'

mongo.connect(url, {
    useNewUrlParser: true,
    useUnifiedTopology: true
  }, (err, client) => {
  if (err) {
    console.error(err)
    return
  }
  //...
  const db = client.db('uni')
  const collection = db.collection('students')
  collection.insertOne({name: 'Alex'}, (err, result) => {
     collection.insertMany([{name: 'Andreea'}, {name: 'Marius'}], (err, result) => {
       console.log(result)
     })
  })
  collection.find().toArray((err, items) => {
     console.log(items)
  })
  collection.find({name: 'Alex'}).toArray((err, items) => {
     console.log(items)
  })
  collection.findOne({name: 'Alex'}, (err, item) => {
     console.log(item)
  })
  collection.updateOne({name: 'Alex'}, {'$set': {'name': 'Alexandru'}}, (err, item) => {
     console.log(item)
     collection.deleteOne({name: 'Alexandru'}, (err, item) => {
        console.log(item)
     })
  })

  collection.find().toArray((err, items) => {
     console.log(items)
  })


  // use Promises or async/await
  collection.findOne({name: 'Andreea'})
  .then(item => {
      console.log('Document Andreea found using Promise: ', item)
  })
  .catch(err => {
     console.error(err)
  })

  const find = async () => {
    try {
       const item = await collection.findOne({name: 'Andreea'})
       console.log('Document Andreea found using async/await: ', item)
    } catch(err) {
       console.error(err)
    }
  }

  find()


  collection.drop( (err, result) => {
			// handle the error if any
			if (err) throw err;
			console.log("Collection is deleted! " + result);

                        db.dropDatabase( (err, result) => {
		           if (err) {
                              console.log("Error : "+err); 
                              throw err;
                           }
		           console.log("Operation Success ? "+result);
		           // after all the operations with db, close it.
		           client.close();
	                });
		    });
   })
