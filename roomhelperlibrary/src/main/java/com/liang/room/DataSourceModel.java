/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.liang.room;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Access point for managing data.
 */
public abstract class DataSourceModel<T, DAO extends BaseDao<T>> extends ViewModel implements Source<T, DAO> {

    private DAO dao;
    private MutableLiveData<List<T>> listLiveData;
    private MutableLiveData<T> liveData;

    public DataSourceModel(DAO dao) {
        this.dao = dao;
        this.listLiveData = new MutableLiveData<>();
        this.liveData = new MutableLiveData<>();
    }

    @Override
    public final DAO getDao() {
        return dao;
    }

    public MutableLiveData<List<T>> getListLiveData() {
        return listLiveData;
    }

    public MutableLiveData<T> getLiveData() {
        return liveData;
    }

    @Override
    public final Disposable bindAllData() {
        return getDataList().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<T>>() {
                    @Override
                    public void accept(List<T> ts) throws Exception {
                        listLiveData.setValue(ts);
                    }
                });
    }

    @NonNull
    protected abstract Flowable<List<T>> getDataList();

    @Override
    public final Disposable insert(@NonNull final T data) {
        return Completable.fromAction(new Action() {
            @Override
            public void run() {
                getDao().insert(data);
            }
        }).subscribeOn(Schedulers.io()).subscribe();
    }

    @Override
    public final Disposable insert(@NonNull final List<T> data) {
        return Completable.fromAction(new Action() {
            @Override
            public void run() {
                getDao().insertAll(data);
            }
        }).subscribeOn(Schedulers.io()).subscribe();
    }

    @Override
    public final Disposable update(@NonNull final T data) {
        return Completable.fromAction(new Action() {
            @Override
            public void run() {
                getDao().update(data);
            }
        }).subscribeOn(Schedulers.io()).subscribe();
    }

    @Override
    public final Disposable update(@NonNull final List<T> data) {
        return Completable.fromAction(new Action() {
            @Override
            public void run() {
                getDao().update(data);
            }
        }).subscribeOn(Schedulers.io()).subscribe();
    }

    @Override
    public final void delete(@NonNull final T data) {
        Completable.fromAction(new Action() {
            @Override
            public void run() {
                getDao().delete(data);
            }
        }).subscribeOn(Schedulers.io()).subscribe();
    }

    public static <T extends DataSourceModel> T getViewModel(@NonNull FragmentActivity activity,
                                                             @Nullable ViewModelFactory<T> factory,
                                                             Class<T> clazz) {
        return ViewModelProviders.of(activity, factory).get(clazz);
    }
}
