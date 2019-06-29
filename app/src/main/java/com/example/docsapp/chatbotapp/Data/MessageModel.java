package com.example.docsapp.chatbotapp.Data;

public class MessageModel {
    private String message;
    private String flag;

    public MessageModel(String message, String flag){
        this.message = message;
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
    }

    public String getMessage() {
        return message;
    }
}
