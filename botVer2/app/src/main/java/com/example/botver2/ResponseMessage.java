package com.example.botver2;

public class ResponseMessage {
    String textMessage;
    boolean isMe;

    public ResponseMessage(String text, boolean isMe) {
        this.textMessage = text;
        this.isMe = isMe;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String text) {
        this.textMessage = text;
    }

    public boolean isMe() {
        return isMe;
    }

    public void setMe(boolean me) {
        isMe = me;
    }
}
