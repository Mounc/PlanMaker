var exrpess = require('express')
var router = exrpess.Router()
var conn = require('../../config/mysql')

router.post('/checkid', (req, res)=>{
  _userid = req.body.userid
  conn.query(`select userid from user`, (err, rows)=>{
    var result = true
    if (err) {result = err}
    rows.forEach((row)=>{
      if (row.userid == _userid) {
        result = false
      }
    })
    res.json({checkid:result})
  })
})

router.post('/register', (req, res)=>{
  registerUser = req.body
  conn.query(`insert into user values (null, '${registerUser.userid}', '${registerUser.password}', '${registerUser.name}')`, (err, rows)=>{
    var result = true
    if (err) {result = false}
    console.log(rows)
    res.json({result:result})
  })
})


module.exports = router
