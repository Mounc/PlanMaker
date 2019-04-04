var exrpess = require('express')
var router = exrpess.Router()
var location = require('./location')
var event = require('./event')
var coEvent = require('./coEvent')

router.use('/location', location)
router.use('/event', event)
router.use('/coEvent', coEvent)

module.exports = router;
