/*
// Devoxx May 2019 London UK: https://www.youtube.com/playlist?list=PLRsbF2sD7JVqkOs-GFGxBmNf0KECELaiU
// DNA for Storage: https://www.youtube.com/watch?v=NsfuBSsF1Fk&list=PLRsbF2sD7JVqkOs-GFGxBmNf0KECELaiU&index=3
// Quantum: https://www.youtube.com/watch?v=RMeWqXdBHIg&list=PLRsbF2sD7JVqkOs-GFGxBmNf0KECELaiU&index=82

// # Test REST API with cURL, Postman, etc as plugin in Chrome Browser or command line (CLI) or stand-alone app:
POST /trip {"name": "London, UK, May 2019"}
GET /trips
# POST /expense { trip - id from the prev GET /trips, date, amount, category, description }
POST /expense { "trip":"61..", "date": "2019-05-16, "amount":25, "category":"food", "description":"Breakfast" }
POST /expense { "trip":"61..", "date": "2019-05-16, "amount":50, "category":"conference", "description":"Access to Devoxx Conference" }

GET /expenses

*/
// npm install mongodb
// npm install express

const express = require("express")
const mongo = require("mongodb").MongoClient
const app = express()

// MongoDB Connect
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
  const name = req.body.name
  trips.insertOne({ name: name }, (err, result) => {
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
