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

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

/**
 * Access point for managing data.
 */
public abstract class DataSourceModel<T, DAO extends BaseDao<T>> extends ViewModel implements Source<T, DAO> {

    private DAO dao;
    private LiveData<PagedList<T>> listLiveData;
    private MutableLiveData<T> liveData;

    public DataSourceModel(DAO dao) {
        this.dao = dao;
        this.liveData = new MutableLiveData<>();
        this.listLiveData = new LivePagedListBuilder<>(
                bindAllData(), getPageSize())
                .setBoundaryCallback(dataBoundaryCallback)
                .build();
    }

    protected abstract int getPageSize();

    protected void onZeroItemsLoaded() {
    }

    protected void onItemAtFrontLoaded(T itemAtFront) {
    }

    protected void onItemAtEndLoaded(T itemAtEnd) {
    }

    @Override
    public final DAO getDao() {
        return dao;
    }

    public LiveData<PagedList<T>> getListLiveData() {
        return listLiveData;
    }

    public MutableLiveData<T> getLiveData() {
        return liveData;
    }

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

    public static <T extends DataSourceModel> T getViewModel(@NonNull Fragment fragment,
                                                             @Nullable ViewModelFactory<T> factory,
                                                             Class<T> clazz) {
        return ViewModelProviders.of(fragment, factory).get(clazz);
    }

    final PagedList.BoundaryCallback<T> dataBoundaryCallback = new PagedList.BoundaryCallback<T>() {

        @Override
        @MainThread
        public void onZeroItemsLoaded() {
            DataSourceModel.this.onZeroItemsLoaded();
        }

        @Override
        @MainThread
        public void onItemAtFrontLoaded(@NonNull T itemAtFront) {
            DataSourceModel.this.onItemAtFrontLoaded(itemAtFront);
        }

        @Override
        @MainThread
        public void onItemAtEndLoaded(@NonNull T itemAtEnd) {
            DataSourceModel.this.onItemAtEndLoaded(itemAtEnd);
        }
    };
}
