package com.example.docsapp.chatbotapp.Data.Database;

import android.arch.persistence.room.Entity;

@Entity(tableName = "message")
public class MessageEntity {
    public Long timeStamp;
    public String message;
    public String flag;

    public Long getTimeStamp() {
        return timeStamp;
    }
}
