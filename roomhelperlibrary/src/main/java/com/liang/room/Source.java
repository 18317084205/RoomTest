package com.liang.room;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.disposables.Disposable;

public interface Source<T, DAO extends BaseDao<T>> {

    DAO getDao();

    Disposable bindAllData();

    Disposable insert(@NonNull T data);

    Disposable insert(@NonNull List<T> data);

    Disposable update(@NonNull T data);

    Disposable update(@NonNull List<T> data);

    void delete(T data);
}
