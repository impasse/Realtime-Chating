var express = require('express');
var router = express.Router();
var data = require('../data');

/* GET home page. */
router.get('/', function (req, res, next) {
    res.render('index', {msgs: data.msgs, title: "聊天服务器"});
});

router.get('/dynamic', function (req,res) {
    res.render('dynamic',{title:"Dynamic",msgs:JSON.stringify(data.msgs)});
});

router.post('/post', function (req, res) {
    var content = req.body.msg;
    if (!content) {
        res.render('error', {message: "请输入内容"});
    } else {
        data.event.emit("msg", "robot", content, new Date);
        res.redirect("/");
    }
})

module.exports = router;
