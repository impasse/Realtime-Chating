(function () {
    var sock = io();

    var subbtn = document.getElementById("submit");
    var msgarea = document.getElementById("msg");
    var ul = document.getElementById("msgs");

    function addMsgToPage(msg) {
        var m = document.createElement("li");
        m.innerText = (msg.owner == "user" ? "用户" : "服务端") +
            "于" + msg.time + " 发送内容: " + msg.content;
        ul.appendChild(m);
    }

    for (i = 0, l = msgs.length; i < l; i++) {
        addMsgToPage(msgs[i]);
    }

    sock.on('new', function (msg) {
        console.log(msg);
        addMsgToPage(msg);
    });

    document.addEventListener('keydown', function (event) {
        if (event.keyCode == 13) {
            subbtn.click();
        }
    });

    subbtn.addEventListener("click", function () {
        if (msgarea.value.length == 0) {
            alert("输入为空");
        } else {
            sock.emit("msg", msgarea.value);
            msgarea.value = null;
            msgarea.focus();
        }
    });
})();
