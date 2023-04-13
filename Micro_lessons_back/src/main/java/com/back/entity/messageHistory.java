package com.back.entity;

public class messageHistory {
    private String type;
    private String message;

    @Override
    public String toString() {
        return "messageHistory{" +
                "type='" + type + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
