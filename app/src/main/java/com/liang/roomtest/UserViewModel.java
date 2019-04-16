package com.liang.roomtest;

import android.arch.paging.DataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import com.liang.room.DataSourceModel;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class UserViewModel extends DataSourceModel<User, UserDao> {

    public UserViewModel(UserDao dao) {
        super(dao);
    }

    @Override
    protected int getPageSize() {
        return 20;
    }

    @Override
    protected void onItemAtFrontLoaded(User itemAtFront) {
        Log.e("UserViewModel", "onItemAtFrontLoaded: "+ itemAtFront.id);
    }

    @Override
    protected void onZeroItemsLoaded() {
        Log.e("UserViewModel", "onZeroItemsLoaded: ...");
    }

    @Override
    protected void onItemAtEndLoaded(User itemAtEnd) {
        Log.e("UserViewModel", "onItemAtEndLoaded: "+ itemAtEnd.id);
    }

    public Disposable getUser(int id) {
        return getDao().loadUser(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<User>() {
                    @Override
                    public void accept(User ts) throws Exception {
                        getLiveData().setValue(ts);
                    }
                });
    }

    @Override
    public DataSource.Factory<Integer, User> bindAllData() {
        return getDao().loadAllUsers();
    }
}
