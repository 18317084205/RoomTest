package com.liang.roomtest;

import android.app.Application;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.migration.Migration;

import com.liang.room.DatabaseHelper;

public class App extends Application {

    public static AppDatabase appDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        appDatabase = new DatabaseHelper<>(this, AppDatabase.class, "test")
                .updateDatabase(MIGRATION_1_2, MIGRATION_2_3)
                .build();
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE user " + " ADD COLUMN age INTEGER NOT NULL DEFAULT 0");
        }
    };

    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `Dog` (`did` INTEGER NOT NULL, `name` TEXT, `sex` INTEGER NOT NULL, PRIMARY KEY(`did`))");
        }
    };


}
