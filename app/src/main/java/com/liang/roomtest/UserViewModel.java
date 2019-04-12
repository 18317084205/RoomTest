package com.liang.roomtest;

import android.support.annotation.NonNull;

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

    @NonNull
    @Override
    protected Flowable<List<User>> getDataList() {
        return getDao().loadAllUsers();
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

}
