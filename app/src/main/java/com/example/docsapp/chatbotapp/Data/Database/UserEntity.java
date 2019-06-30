package com.example.docsapp.chatbotapp.Data.Database;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.util.ArrayList;



@Entity(tableName = "user")
public class UserEntity {

    @NonNull
    @PrimaryKey
    public String userId;
    public String externalId;
    @TypeConverters(DbTypeConverter.class)
    public ArrayList<MessageObject> messageList;

    @NonNull
    public String getUserId() {
        return userId;
    }

    public void setUserId(@NonNull String userId) {
        this.userId = userId;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public ArrayList<MessageObject> getMessageList() {
        return messageList;
    }

    public void setMessageList(ArrayList<MessageObject> messageList) {
        this.messageList = messageList;
    }
}
