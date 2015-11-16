package com.shyling.onlinechat;

/**
 * Created by shy on 2015/11/15.
 */
public class Msg {
    String owner, content, time;

    public Msg(String content, String owner, String time) {
        this.content = content;
        this.owner = owner;
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOwner() {
        return owner;
    }


    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Msg{" +
                "content='" + content + '\'' +
                ", owner='" + owner + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
