package com.liang.roomtest;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "user")
public class User {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public long id;

    public String name;

    public int age;

}
