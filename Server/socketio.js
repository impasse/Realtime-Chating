var socket = require('socket.io');
var data = require('./data');

var server;

function initServer(callback) {

    server = socket(callback);

    server.on('connection', function (sock) {
        console.log('Socket.IO client connection');

        var recvMsg = function (msg) {
            sock.emit('new', msg);
        };

        data.event.on('new', recvMsg);

        sock.on('disconnect', function () {
            data.event.removeListener('new', recvMsg);
            console.log('Socket.IO client disconnected');
        })
        sock.on('msg', function (content) {
            console.log("Socket.IO recv msg");
            data.event.emit('msg', "robot", content, new Date);
        });
    });

    return server;
}


module.exports = exports = initServer;