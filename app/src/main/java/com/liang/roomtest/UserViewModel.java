package com.liang.roomtest;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.support.annotation.NonNull;
import android.util.FloatProperty;
import android.util.Log;

import com.liang.room.DataSourceModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class UserViewModel extends DataSourceModel<User, UserDao> {

    private MutableLiveData<User> liveData = new MutableLiveData<>();

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
                        liveData.setValue(ts);
                    }
                });
    }

    public MutableLiveData<User> getLiveData() {
        return liveData;
    }

    @Override
    public DataSource.Factory<Integer, User> bindAllData() {
        return getDao().queryAll();
    }
}
