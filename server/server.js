var express = require('express');
var connect = require('connect');
var app = express();
var router = express.Router();
var bodyParser = require('body-parser');
var fs = require('fs');

var mongoose = require('mongoose');
mongoose.Promise = global.Promise;
mongoose.connect('mongodb://localhost:27017/demoDb');

var model = require("./models/mongo");

app.use(express.static(__dirname + 'public'));
app.use(connect.cookieParser());
app.use(connect.logger('dev'));
app.use(connect.bodyParser());
app.use(connect.json());
app.use(connect.urlencoded());
app.use(bodyParser.json());

var imageSchema = mongoose.Schema({
    path: String,
    account_id: Number
});
var Image = mongoose.model('Image', imageSchema);

router.get('/', function (req, res) {
    res.end("Citizens Assist Server");
});

router.post('/upload', function (req, res) {
    console.log(req.files.image.originalFilename);
    console.log(req.files.image.path);

    var dir = './image_store';

    if (!fs.existsSync(dir)) {
        fs.mkdirSync(dir);
    }

    //99 - test account_id
    var uploadedImage = new Image({
        path: req.files.image.originalFilename,
        account_id: 99
    });

    fs.readFile(req.files.image.path, function (err, data) {

        var newPath = dir + "/" + req.files.image.originalFilename;
        console.log(newPath);

        uploadedImage.save(function (dbSaveErr, fluffy) {
            if (!dbSaveErr) {
                fs.writeFile(newPath, data, function (err) {
                    if (err) {
                        res.json({'response': "Local FS write Error", 'success': false});
                    } else {
                        res.json({'response': "Saved", 'success': true});
                    }
                });
            } else {
                res.json({'response': "Db save Error", 'success': false});
            }
        });
    });
});

router.get('/uploads/:file', function (req, res) {
    file = req.params.file;

    var dirname = "/home/swathia/Node/file-upload";
    var img = fs.readFileSync(dirname + "/uploads/" + file);
    res.writeHead(200, {'Content-Type': 'image/jpg'});
    res.end(img, 'binary');
});

router.post()

app.use('/', router);
app.listen(5000);
console.log("Listening to PORT 5000");
