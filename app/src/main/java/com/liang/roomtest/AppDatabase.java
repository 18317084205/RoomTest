package com.liang.roomtest;

import android.arch.persistence.room.Database;

import com.liang.room.JRoomDatabase;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends JRoomDatabase {
    public abstract UserDao userDao();
}
