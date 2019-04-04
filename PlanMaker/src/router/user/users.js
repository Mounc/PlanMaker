
var exrpess = require('express')
var router = exrpess.Router()
var conn = require('../../config/mysql')


router.post('/', (req, res)=>{
  mode = req.body.mode
  _userid = req.body.userid
  if(mode!="getusers") return;
  return conn.query(`select id, userid, name  from user where userid != '${_userid}'`, (err, rows)=>{
    result = {
      users: []
    }
    if(rows.length != 0){
      rows.forEach((row)=>{
        result.users.push(row)
      })
    }
    console.log(result)
    res.json(result)
  })
})

module.exports = router;
