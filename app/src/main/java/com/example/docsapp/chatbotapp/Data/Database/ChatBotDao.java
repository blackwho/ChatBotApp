package com.example.docsapp.chatbotapp.Data.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface ChatBotDao {

    @Insert(onConflict = REPLACE)
    void save(UserEntity userEntity);
}
