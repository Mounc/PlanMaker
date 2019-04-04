var exrpess = require('express')
var router = exrpess.Router()
var conn = require('../../config/mysql')


router.post('/', (req,res)=>{
  console.log(req.body.userid)
  console.log(req.body.latitude)
  console.log(req.body.longitude)

  conn.query(`update point set latitude = ${req.body.latitude}, longitude = ${req.body.longitude} where userid = '${req.body.userid}'`, (err, rows)=>{
    if (err) return res.json({result:false, err})
    return res.json({result:true})
  })
})

router.post('/latitude', (req,res)=>{
  _userid = req.body.userid
  console.log('/location/latitude #'+_userid)
  conn.query(`select latitude from point where userid = '${_userid}'`, (err, rows)=>{
    res.json({result:rows[0].latitude})
    console.log(rows[0].latitude)
  })
})

router.post('/longitude', (req,res)=>{
  _userid = req.body.userid
  console.log('/location/longitude #'+_userid)
  conn.query(`select longitude from point where userid = '${_userid}'`, (err, rows)=>{
    res.json({result:rows[0].longitude})
    console.log(rows[0].longitude)
  })
})



module.exports = router;
