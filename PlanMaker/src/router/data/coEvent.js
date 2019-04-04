var exrpess = require('express')
var router = exrpess.Router()
var conn = require('../../config/mysql')
var FCM = require('fcm-node')
var fcm = new FCM('AAAAHSlRFfQ:APA91bFzuI_ShXnviyHTmdg04l5n0AUA8DVCaP9VQ_MlmRNCHgCvQeeWcsNrZDtrkSywJWxAIeH_oH5wyPVeSk1zhyIg8syKkZqc3qw0yEIeRl-VA1_FxpupodLeP1xTepqP_lGai4PE')
var id = 0
var coEventList = []

var message = { //this may vary according to the message type (single recipient, multicast, topic, et cetera)
  // to: 'registration_token',
  // to: 'doAtNP-8KGU:APA91bH8CV38S-smDoIXPp3PeBjMEPaazCEPM4Jyz3piFdRE9cG5JDZHdALZ8vP35ZLsFoC_Iysgtv80xFqEbTplJZfvkma8QzRl4PbYK911tfjLjdY_0mtCPzRXIZNc6uVRQ_dNJvPv',
  to: '/topics/newCoEvent',

  // notification: {
  //   title: '이벤트 초대+',
  //   body: '새 이벤트에 초대되었습니다.',
  //   mode: 'invitation'
  // },

  data: { //you can send only notification or only data(or include both)
    title: '새 이벤트 초대',
    content: '초대된 이벤트를 확인하세요.',
    mode: 'invitation'
  }
}
router.post('/invitation', (req, res) => {
  var userid = req.body.userid
  var friendsList = req.body.friendsList
  var response = req.body.response
  var place = req.body.place

  coEventList.push({
    id: id++,
    userid,
    friendsList,
    friendsList2: friendsList,
    response,
    place
  })
  console.log("Pust>> ")
  console.log(coEventList)

  fcm.send(message, function (err, response) {
    if (err) {
      console.log("Something has gone wrong!")
      res.json({
        result: false,
        date: err
      })
    } else {
      console.log("Successfully sent with response: ", response)
      res.json({
        result: true,
        data: response
      })
    }
  })
})

router.post('/coEventList', (req, res) => {
  var userid = req.body.userid
  var resultList = []

  console.log("CoEventList : " + userid)

  coEventList.forEach((item, index) => {
    item.friendsList2.forEach(friendID => {
      if (friendID == userid) {
        resultList.push(coEventList[index])
        // coEventList.splice(index, 1)
      }
    })
  })
  res.json({
    result: true,
    data: resultList
  })
  return;
})

router.post('/removeInvitation', (req, res) => {
  var userid = req.body.userid
  var coEventID = req.body.id
  coEventList.forEach((item, index) => {
    if (item.id == coEventID) {
      var temp = coEventList[index].friendsList2.indexOf(userid)
      coEventList[index].friendsList2.splice(temp, 1)
    }
  })
  res.json({result:true})
})

module.exports = router;
