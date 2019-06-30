package com.example.docsapp.chatbotapp.Data.Database;

public class MessageObject {

    private Long timeStamp;
    private String message;
    private String flag;

    public MessageObject(Long mTimeStamp, String mMessage, String mFlag){
        this.timeStamp = mTimeStamp;
        message = mMessage;
        flag = mFlag;
    }

    public MessageObject(){}

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
