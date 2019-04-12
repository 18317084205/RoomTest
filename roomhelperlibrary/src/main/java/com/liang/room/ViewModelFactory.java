package com.liang.room;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

public abstract class ViewModelFactory<VM extends DataSourceModel> implements ViewModelProvider.Factory {
    @NonNull
    @Override
    public final <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (DataSourceModel.class.isAssignableFrom(modelClass)) {
            return (T) getViewModel();
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

    public abstract VM getViewModel();
}
