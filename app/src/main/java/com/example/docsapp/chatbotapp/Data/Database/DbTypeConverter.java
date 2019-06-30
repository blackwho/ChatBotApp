package com.example.docsapp.chatbotapp.Data.Database;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DbTypeConverter {

    @TypeConverter
    public String fromMessageObjectList(ArrayList<MessageObject> message) {
        if (message == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<MessageObject>>() {}.getType();
        return gson.toJson(message, type);
    }

    @TypeConverter
    public ArrayList<MessageObject> toMessageObjectList(String messageString) {
        if (messageString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<MessageObject>>() {}.getType();
        return gson.fromJson(messageString, type);
    }
}
