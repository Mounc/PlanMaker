var mysql = require('mysql')

var conn = mysql.createConnection({
  host: 'localhost',
  port: 3306,
  user: 'root',
  password: 'root',
  database: 'planmaker',
  dateStrings: 'date'
})
conn.connect();

module.exports = conn;
