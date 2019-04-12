package com.liang.roomtest;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {User.class, Dog.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();

    public abstract DogDao dogDao();
}
