
var exrpess = require('express')
var router = exrpess.Router()
var conn = require('../../config/mysql')
var request = require('request')

router.post('/new', (req, res) => {
  _userid = req.body.userid
  _event = req.body.newEvent
  result = {}
  conn.query(`insert into myplan values(null, '${_userid}', ${_event.id}, '${_event.title}', ${_event.allday}, '${_event.startTime}', '${_event.endTime}', ${_event.repeat}, '${_event.repeatEndTime}', ${_event.repeatGroupId}, ${_event.noticeTime}, '${_event.place}', '${_event.memo}')`, (err, rows) => {
    if (err) {
      res.json({
        result: false
      })
      return
    }
    res.json({
      result: true
    })
    return
  })
})

router.post('/delete', (req, res) => {
  _userId = req.body.userid
  _eventId = req.body.eventid

  sql = `delete from myplan where userid='${_userId}' and id=${_eventId}`
  conn.query(sql, (err, rows) => {
    if (err) {
      console.log(err)
      return res.json({
        result: false
      })
    }
    console.log(rows);
    return res.json({
      result: true
    })
  })
})

router.post('/events', (req, res) => {
  _userids = req.body.userids
  strUserids = ''
  _userids.forEach(item => {
    strUserids += ` userid='${item}' or`
  })
  strUserids = strUserids.substring(0, strUserids.length - 2)
  //where userid = 1 or user = 2
  sql = `select * from myplan where ${strUserids}`
  return conn.query(sql, (err, rows) => {
    if (err) {
      res.json({
        err
      })
      console.log(err)
      return
    }
    results = []
    rows.forEach(row => {
      result = {}
      result.sid = row.sid
      result.userid = row.userid
      result.id = row.id
      result.title = row.title
      result.allday = row.allday
      result.startTime = row.starttime
      result.endTime = row.endtime
      result.repeat = row.repeat
      result.repeatEndTime = row.repeatEndTime
      result.repeatGroupId = row.repeatGroupId
      result.noticeTime = row.noticetime
      result.place = row.place
      result.memo = row.memo
      results.push(result)
    })
    res.json({
      results
    })
    console.log(results)
    return
  })
})


module.exports = router;
