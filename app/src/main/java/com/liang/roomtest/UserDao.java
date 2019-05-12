package com.liang.roomtest;

import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.liang.room.BaseDao;

import java.util.List;


import io.reactivex.Flowable;

@Dao
public interface UserDao extends BaseDao<User> {

    @Override
    @Query("SELECT * FROM user")
    DataSource.Factory<Integer, User> queryAll();

    @Query("select * from user where id = :uid")
    DataSource.Factory<Integer, User> query(int uid);

    @Query("select * from user where id = :uid")
    Flowable<User> loadUser(int uid);
}
