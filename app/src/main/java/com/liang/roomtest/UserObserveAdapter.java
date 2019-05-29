/*
 * Copyright 2017, The Android Open Source Project
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

package com.liang.roomtest;

import android.annotation.SuppressLint;
import android.arch.paging.PagedListAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liang.roomtest.databinding.LayoutItemBinding;


public class UserObserveAdapter extends PagedListAdapter<User, UserObserveAdapter.UserViewHolder> {

    protected UserObserveAdapter() {
        super(new DiffUtil.ItemCallback<User>() {
            @Override
            public boolean areItemsTheSame(@NonNull User user, @NonNull User t1) {
                return user.id == t1.id;
            }

            @SuppressLint("DiffUtilEquals")
            @Override
            public boolean areContentsTheSame(@NonNull User user, @NonNull User t1) {
                return user == t1;
            }
        });
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_item, parent, false);
        return new UserObserveAdapter.UserViewHolder(viewDataBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        LayoutItemBinding viewDataBinding = DataBindingUtil.getBinding(holder.itemView);
        viewDataBinding.setUser(getItem(position));
        viewDataBinding.executePendingBindings();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


}
