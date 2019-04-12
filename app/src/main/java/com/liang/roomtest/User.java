package com.liang.roomtest;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "user")
public class User {
    @PrimaryKey
    @NonNull
    public long id;

    public String name;

    public User(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
