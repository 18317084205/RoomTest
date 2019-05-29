package com.liang.roomtest;

import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.liang.room.BaseDao;


import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface UserDao extends BaseDao<User> {

    //必须重写此方法
    @Override
    @Query("SELECT * FROM user")
    DataSource.Factory<Integer, User> queryAll();

    //自定义查询
    @Query("SELECT * FROM user")
    Flowable<List<User>> loadUsers();
    //自定义查询
    @Query("select * from user where id = :uid")
    Flowable<User> loadUser(int uid);
}
