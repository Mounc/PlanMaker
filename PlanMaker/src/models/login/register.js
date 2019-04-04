var mysql = require('mysql')

var conn = mysql.createConnection({
  host: 'localhost',
  port: 3306,
  user: 'root',
  password: 'root',
  database: 'planmaker'
})
conn.connect();

register = {
  checkid :  (_userid)=>{
    console.log("check Id");
    var result
    return conn.query(`select * from user'`, (err, rows)=>{
      if (err) return err
      if (rows.length) {
        for (var row in rows) {
          console.log(row.userid);
          if (row.userid == _userid) return false
        }
        return true
      }
    })
  } ,
  submit : ()=>{

  }
}

module.exports = register;
