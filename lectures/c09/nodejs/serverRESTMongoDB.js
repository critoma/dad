/*
# // Devoxx May 2019 London UK: https://www.youtube.com/playlist?list=PLRsbF2sD7JVqkOs-GFGxBmNf0KECELaiU
# // DNA for Storage: https://www.youtube.com/watch?v=NsfuBSsF1Fk&list=PLRsbF2sD7JVqkOs-GFGxBmNf0KECELaiU&index=3
# // Quantum: https://www.youtube.com/watch?v=RMeWqXdBHIg&list=PLRsbF2sD7JVqkOs-GFGxBmNf0KECELaiU&index=82

# // # Test REST API with cURL, Postman, etc as plugin in Chrome Browser or command line (CLI) or stand-alone app:
GET /trips
# curl -X GET http://127.0.0.1:3000/trips

# in browser http://127.0.0.1:3000/trips because docker has been started with port forwarding
# or command line: curl -X POST http://127.0.0.1:3000/trip -H 'Content-Type: application/json' -d '{"name": "London, UK, May 2019"}'
# for Windows OS: 
# curl -X POST -H "Content-Type: application/json" -d "{\"name\": \"London, UK, May 2019\"}" http://127.0.0.1:3000/trip

POST /trip {"name": "London, UK, May 2019"}
GET /trips

# for Windows OS:
# curl -X POST -H "Content-Type: application/json" -d "{\"trip\": \"655cefd292e20d94eb211fd3\",\"date\":\"2023-11-21\", \"amount\":30,\"category\":\"food\",\"description\":\"Devoxx...\"}" http://127.0.0.1:3000/expense
# POST /expense { trip - id from the prev GET /trips, date, amount, category, description }
POST /expense {"trip": "62822a80236ab9479e834eb6", "date": "2019-05-16", "amount":25, "category":"food", "description":"Breakfast" }
POST /expense {"trip":"62822a80236ab9479e834eb6", "date": "2019-05-16", "amount":50, "category":"conference", "description":"Access 2 Devoxx Conf" }

GET /expenses
*/

// npm install express 
// npm install mongodb

// node.js Express module
const express = require("express")
const app = express()

app.use(
  express.urlencoded({
    extended: true
  })
)

app.use(express.json())

// MongoDB Client module and MongoDB Connect
const mongo = require("mongodb").MongoClient
const mongoDbUrl = "mongodb://localhost:27017"

let db, trips, expenses

mongo.connect(
  mongoDbUrl,
  {
    useNewUrlParser: true,
    useUnifiedTopology: true,
  },
  (err, client) => {
    if (err) {
      console.error(err)
      return
    }
    db = client.db("tripcost")
    trips = db.collection("trips")
    expenses = db.collection("expenses")
  }
)


// REST API Endpoints
app.post("/trip", (req, res) => {
  const nameP = req.body.name
  trips.insertOne({ "name": nameP }, (err, result) => {
    if (err) {
      console.error(err)
      res.status(500).json({ err: err })
      return
    }
    console.log(result)
    res.status(200).json({ ok: true })
  })
})

app.get("/trips", (req, res) => {
  trips.find().toArray((err, items) => {
    if (err) {
      console.error(err)
      res.status(500).json({ err: err })
      return
    }
    res.status(200).json({ trips: items })
  })
})

app.post("/expense", (req, res) => {
    expenses.insertOne(
    {
      trip: req.body.trip,
      date: req.body.date,
      amount: req.body.amount,
      category: req.body.category,
      description: req.body.description,
    },
    (err, result) => {
      if (err) {
        console.error(err)
        res.status(500).json({ err: err })
        return
      }
      res.status(200).json({ ok: true })
    }
  )
})

app.get("/expenses", (req, res) => {
  expenses.find({ trip: req.body.trip }).toArray((err, items) => {
    if (err) {
      console.error(err)
      res.status(500).json({ err: err })
      return
    }
    res.status(200).json({ expenses: items })
  })
})

app.listen(3000, () => console.log("node.js JavaScript HTTP REST Server ready"))
