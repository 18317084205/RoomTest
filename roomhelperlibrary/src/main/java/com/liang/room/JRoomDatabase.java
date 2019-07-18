package com.liang.room;

import android.annotation.SuppressLint;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.RoomDatabase;


public abstract class JRoomDatabase extends RoomDatabase {

    @SuppressLint("RestrictedApi")
    public void clearTable(String table) {
        assertNotMainThread();
        final SupportSQLiteDatabase _db = getOpenHelper().getWritableDatabase();
        try {
            beginTransaction();
            _db.execSQL("DELETE FROM " + table);
            setTransactionSuccessful();
        } finally {
            endTransaction();
            _db.query("PRAGMA wal_checkpoint(FULL)").close();
            if (!_db.inTransaction()) {
                _db.execSQL("VACUUM");
            }
        }
    }
}
