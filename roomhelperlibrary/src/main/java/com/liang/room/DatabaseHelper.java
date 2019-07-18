package com.liang.room;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.support.annotation.NonNull;

public class DatabaseHelper<T extends JRoomDatabase> {

    private RoomDatabase.Builder<T> mBuilder;

    public DatabaseHelper(@NonNull Application application,
                          @NonNull Class<T> clazz,
                          @NonNull String dbName) {
        mBuilder = Room.databaseBuilder(application, clazz, dbName);
    }

    public DatabaseHelper<T> updateDatabase(@NonNull Migration... migration) {
        mBuilder.addMigrations(migration);
        return this;
    }

    public T build() {
        return mBuilder.build();
    }

}
