package com.apologize.chatroom.MSG;

import java.sql.Date;

/**
 * Created by apologize on 2016/9/24.
 */

public class Message {

    public static final int MSG_RIGHT = 0x1;
    public static final int MSG_LEFT = 0x2;

    private int dir; //方向
    private String content;
    private Date time;

    public Message(int dir, String content, Date time) {
        this.dir = dir;
        this.content = content;
        this.time = time;
    }

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
