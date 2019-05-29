package com.liang.roomtest;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.liang.room.DataSourceModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class UserViewModel extends DataSourceModel<User, UserDao> {

    private MutableLiveData<User> liveData = new MutableLiveData<>();
    private MutableLiveData<List<User>> liveDatas = new MutableLiveData<>();

    public UserViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected UserDao setDao() {
        App app = getApplication();
        return app.appDatabase.userDao();
    }

    @Override
    protected void onZeroItemsLoaded() {
        Log.e("UserViewModel", "onZeroItemsLoaded: ...");
        //数据库没有查询到数据
        getDataWithNetwork();
    }

    @Override
    protected void onItemAtEndLoaded(User itemAtEnd) {
        Log.e("UserViewModel", "onItemAtEndLoaded: " + itemAtEnd.id);
        //用户已到达列表末尾,加载网络数据
        getDataWithNetwork();
    }

    private void getDataWithNetwork() {
        Toast.makeText(getApplication(), "去网络拉取数据。。。", Toast.LENGTH_SHORT).show();
        //模拟网络加载
        int index = 0;
        List<User> users = new ArrayList<>();
        while (index < 50) {
            index++;
            User user = new User();
            user.name = "网络数据: " + index;
            user.age = index;
            users.add(user);
        }
        insert(users);
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


    public Disposable getUsers() {
        return getDao().loadUsers().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<User>>() {
                    @Override
                    public void accept(List<User> users) throws Exception {
                        liveDatas.setValue(users);
                    }
                });
    }

    public MutableLiveData<User> getLiveData() {
        return liveData;
    }

    public MutableLiveData<List<User>> getUserDate() {
        return liveDatas;
    }
}
