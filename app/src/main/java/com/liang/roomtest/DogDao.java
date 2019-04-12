package com.liang.roomtest;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.liang.room.BaseDao;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface DogDao extends BaseDao<Dog> {
    @Query("SELECT * FROM dog")
    Flowable<List<Dog>> loadAllDogs();

    @Query("select * from dog where did = :did")
    Flowable<Dog> loadDog(int did);
}
