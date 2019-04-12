package com.liang.roomtest;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.liang.room.BaseDao;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface UserDao extends BaseDao<User> {
    @Query("SELECT * FROM user")
    Flowable<List<User>> loadAllUsers();

    @Query("select * from user where id = :uid")
    Flowable<User> loadUser(int uid);
}
