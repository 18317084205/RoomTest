package com.liang.room;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;

public abstract class BasePageAdapter<T, VH extends RecyclerView.ViewHolder> extends PagedListAdapter<T, VH> {


   protected static class DEF_DIFF_CALLBACK<T> extends DiffUtil.ItemCallback<T> {

       @Override
       public boolean areItemsTheSame(T oldItem, T newItem) {
           return oldItem == newItem;
       }

       @Override
       public boolean areContentsTheSame(T oldItem, T newItem) {
           return oldItem == newItem;
       }
   };

    public BasePageAdapter() {
        super(new DEF_DIFF_CALLBACK<T>());
    }

    @NonNull
    protected DiffUtil.ItemCallback<T> getDiffCallback() {
        return new DEF_DIFF_CALLBACK<T>();
    }





}
