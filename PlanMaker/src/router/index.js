var exrpess = require('express')
var router = exrpess.Router()

var user = require('./user/index')
var data = require('./data/index')

router.post('/check', (req, res)=>{
  console.log(req.body.check)
  if (req.body.check) res.json({result:true})
  else res.json({result:false})
})

router.get('/check', (req, res)=>{
res.send("Server is running")
})

router.use('/user', user)
router.use('/data', data)

module.exports = router;
