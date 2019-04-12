package com.liang.roomtest;

import android.app.Application;

import com.liang.room.DatabaseHelper;

public class App extends Application {

    public static AppDatabase appDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        appDatabase = new DatabaseHelper<>(this, AppDatabase.class, "test")
                .build();
    }

}
