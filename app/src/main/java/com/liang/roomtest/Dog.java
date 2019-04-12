package com.liang.roomtest;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Dog {
    @PrimaryKey
    @NonNull
    public int did;

    public String name;

    public int sex;
}
