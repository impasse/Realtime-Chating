var eventemitter = require('events').EventEmitter;
var event = new eventemitter();
event.setMaxListeners(0);

var msgs = [];

var msg = {
    "owner":"robot",
    "content":"content",
    time:null
};

Date.prototype.toString = function(){
    return this.getFullYear() + "-" + this.getMonth() + "-" + this.getDate() + " " +
        (this.getHours() > 9 ? this.getHours() : "0" + this.getHours())
        + ":" +
        (this.getMinutes()>9?this.getMinutes():"0"+this.getMinutes());
};

function addMsg(owner,content,time){
    var tmp = Object.create(msg);
    tmp.owner = owner;
    tmp.content = content;
    tmp.time = time.toString();
    msgs.push(tmp);
    event.emit('new',tmp);
}

event.on("msg",addMsg);

addMsg("robot","你好,你可以通过客户端发送信息给我",new Date);

exports.msgs = msgs;
exports.msg = msg;
exports.event = event;