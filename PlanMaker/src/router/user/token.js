var exrpess = require('express')
var router = exrpess.Router()
var conn = require('../../config/mysql')

router.post('/', (req, res)=>{
  res.json({result:"TODO"})
})

module.exports = router
