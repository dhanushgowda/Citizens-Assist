var express = require('express');
var connect = require('connect');
var app = express();
var router = express.Router();
var bodyParser = require('body-parser');
var fs = require('fs');
var mongoose = require('mongoose');
mongoose.connect('mongodb://localhost:27017/demoDb');
var model = require("./models/mongo");

app.use(express.static(__dirname + 'public'));
app.use(connect.cookieParser());
app.use(connect.logger('dev'));
app.use(connect.bodyParser());
app.use(connect.json());
app.use(connect.urlencoded());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());

router.get('/', function(req, res) {
    res.end("Citizens Assist Server");
  });

  router.post('/upload', function(req,res) {
    console.log(req.files.image.originalFilename);
    console.log(req.files.image.path);

    fs.readFile(req.files.image.path, function(err, data) {
      var dirname = "/home/swathia/Node/file-upload";
      var newPath = dirname + "/uploads/" + req.files.image.originalFilename;

      fs.writeFile(newPath, data, function(err) {
        if(err) {
          res.json({'response' : "Error"});
        } else {
          res.json({'response' : "Saved"});
        }
      });
    });
  });

  router.get('/uploads/:file', function(req,res) {
    file = req.params.file;

    var dirname = "/home/swathia/Node/file-upload";
    var img = fs.readFileSync(dirname + "/uploads/" + file);
    res.writeHead(200, {'Content-Type': 'image/jpg'});
    res.end(img, 'binary');
  });

  router.post('/validateUser', function(req,res) {
    console.log("Authenticating user: " + req.body.name);
    response = { "error" : true, "message" : "Successful"};
    res.json(response);
    // model.findOne({name: req.params.name}, function(err, data) {
    //   if(err || data == null) {
    //     response = {"error" : true, "message" : "Failed"};
    //     res.json(response);
    //   } else {
    //     var userInfo = data;
    //     if(userInfo.password == req.body.password) {
    //       try {
    //         response = { "error" : false, "message" : "Successful"};
    //         res.json(response);
    //       }
    //       catch(e) {
    //         response = {"error" : true, "message" : "Failed"};
    //         res.json(response);
    //       }
    //     } else {
    //       response = {"error" : true, "message" : "Successful"};
    //       res.json(response);
    //     }
    //   }
    // });
  });

  router.post('/createUser', function(req, res) {
    var response = {};
    var db = new model;
    db.name = req.body.name;
    db.password = req.body.password;

    db.save(function(err) {
      if(err) {
        response = {"error" : true , "message" : "Error adding data"};
      } else {
        console.log("Created user: " + req.body.name);
        response = {"error" : false, "message" : "Data added"};
      }
      res.json(response);
    })
  } )
app.use('/', router);
app.listen(5000);
console.log("Listening to PORT 5000");