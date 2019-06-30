package com.example.docsapp.chatbotapp.Data.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;

import java.util.ArrayList;
import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface ChatBotDao {

    @Insert(onConflict = REPLACE)
    void save(UserEntity userEntity);

    @Query("SELECT * FROM user WHERE userId = :mUserId")
    UserEntity getUserChatConvo(String mUserId);

    @TypeConverters(DbTypeConverter.class)
    @Query("UPDATE user SET messageList = :mMessageList WHERE userId = :userId")
    void updateUserMessageList(ArrayList<MessageObject> mMessageList, String userId);
}
