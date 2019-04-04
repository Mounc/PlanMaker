
var bodyParser = require('body-parser')
var express = require('express')
var app = express()
var http = require("http").Server(app)

app.set('port', 3000)

// Setting MiddleWare
app.use(bodyParser.urlencoded({extended:true}))
app.use(bodyParser.json())

// Setting Router
app.use(require('./src/router/index'))

app.listen(app.get('port'), ()=>{
  console.log(`\nStart Node.js Server on port ${app.get('port')}.\n`)
})
