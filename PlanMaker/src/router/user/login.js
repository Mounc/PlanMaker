
var exrpess = require('express')
var router = exrpess.Router()
var conn = require('../../config/mysql')


router.post('/name', (req, res)=>{
  _userid = req.body.userid
  return conn.query(`select name from user where userid = '${_userid}'`, (err, rows)=>{
    result = {
      result: ""
    }
    if(rows.length == 1){
      result.result = rows[0].name
    }
    res.json(result)
  })
})


router.post('/check', (req, res)=>{
  _userid = req.body.userid
  _password = req.body.password
  console.log(_userid, _password);

  return conn.query(`select userid, password from user where userid = '${_userid}'`, (err, rows)=>{
    result = false
    console.log(rows.length)
    rows.forEach((row)=>{
      if(row.password == _password){
        result = true
      }
    })
    res.json({result})
    return
  })
  res.json({result:false})
  return
})

module.exports = router;
