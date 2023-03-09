// https://codeforgeek.com/nodejs-mysql-tutorial/
// npm install express@4.18.2 mysql@2.18.1
const express = require("express");
const app = express();
const mysql = require('mysql');
/*
const connection = mysql.createConnection({
  host     : 'localhost',
  user     : 'stud',
  password : 'stud',
  database : 'mysql'
});

connection.connect((err) => {
    if(err) throw err;
    console.log('Connected to MySQL Server!');
});

app.get("/",(req,res) => {
    connection.query('SELECT * from user LIMIT 1', (err, rows) => {
        if(err) throw err;
        console.log('The data from users table are: \n', rows);
        connection.end();
    });
});
*/

const pool = mysql.createPool({
  host     : 'localhost',
  user     : 'root', // user     : 'stud',
  password : 'stud',
  database : 'mysql'
});

app.get("/",(req,res) => {
    pool.getConnection((err, connection) => {
        if(err) throw err;
        console.log('connected as id ' + connection.threadId);
        //connection.query('SELECT * from users LIMIT 1', (err, rows) => {
        connection.query('SELECT * from user LIMIT 1', (err, rows) => {
            connection.release(); // return the connection to pool
            if(err) throw err;
            console.log('The data from users table are: \n', rows);
            res.status(200).json(rows)
        });
    });
});


app.listen(3000, () => {
    console.log('Server is running at port 3000');
});
