var net = require('net');
var data = require('./data');

var retSuccesss = {
    "type": "result",
    "value": "ok"
};

var retFailed = {
    "type": "result",
    "value": "error"
};

var server = net.createServer(function (sock) {
    sock.connected = true;
    sock.setEncoding("utf-8");
    
    console.log("Client connect at "+sock.remoteAddress);

    var recvmsg = function (msg) {
        console.log("recv msg" + JSON.stringify(msg));
        sock.write(JSON.stringify({
            "owner":msg.owner,
            "type": "msg",
            "content": msg.content,
            "time": msg.time
        })+"\n");
    }

    data.event.on("new", recvmsg);

    sock.on('data', function (recv) {
        try {
            console.log("Read Data: "+recv);
            var js = JSON.parse(recv);
            if (js.type == "msg" && js.content) {
                data.event.emit("msg","user", js.content, new Date);
                sock.write(JSON.stringify(retSuccesss)+"\n");
            } else {
                sock.write(JSON.stringify(retFailed)+"\n");
            }
        } catch (e) {
            sock.write(JSON.stringify(retFailed)+"\n");
        }
    });

    sock.on('close', function () {
        console.log("Client closed at "+sock.remoteAddress);
        data.event.removeListener('new', recvmsg);
        sock.end();
    });

    sock.on('error', function () {
        console.log("Client closed at "+sock.remoteAddress);
        data.event.removeListener('new', recvmsg);
        sock.end();
    });
});

server.on('error', function (err) {
    console.error(err);
});

server.listen(8088);

module.exports = exports = server;
