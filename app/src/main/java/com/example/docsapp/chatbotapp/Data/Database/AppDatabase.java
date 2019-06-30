package com.example.docsapp.chatbotapp.Data.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;


@Database(entities = {UserEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase mInstance;
    public abstract ChatBotDao chatBotDao();

    public synchronized static AppDatabase getDatabase(final Context context) {
        if (mInstance == null) {
            synchronized (AppDatabase.class) {
                if (mInstance == null) {
                    mInstance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "chat_bot_database")
                            .build();

                }
            }
        }
        return mInstance;
    }
}
