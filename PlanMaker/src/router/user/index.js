var exrpess = require('express')
var router = exrpess.Router()
var conn = require('../../config/mysql')

var login = require('./login')
var register = require('./register')
var users = require('./users')
var token = require('./token')

router.use('/login', login)
router.use('/register', register)
router.use('/users', users)
router.use('/token', token)

module.exports = router
