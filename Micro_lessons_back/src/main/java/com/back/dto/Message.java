package com.back.dto;

import com.back.entity.User;

/**
 * 浏览器发送给服务器的websocket数据
 */
public class Message {
    private String toId;
    private String message;

    @Override
    public String toString() {
        return "Message{" +
                "toId=" + toId +
                ", message='" + message + '\'' +
                '}';
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
