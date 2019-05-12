package com.liang.room;

import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface BaseDao<T> {

    DataSource.Factory<Integer, T> queryAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(T data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insert(List<T> data);

    @Delete
    void delete(T data);

    @Update
    void update(T data);

    @Update
    void update(List<T> data);
}
