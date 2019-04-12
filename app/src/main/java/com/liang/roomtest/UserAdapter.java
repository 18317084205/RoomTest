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

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    List<User> mUsers;

    public void setUsers(List<User> users) {
        if (users != null) {
            mUsers = users;
            notifyDataSetChanged();
        }
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UserViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, parent, false));
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        holder.uid.setText("id: " + mUsers.get(position).id);
        holder.name.setText("name: " + mUsers.get(position).name);
        holder.age.setText("age: " + mUsers.get(position).age);
    }

    @Override
    public int getItemCount() {
        return mUsers == null ? 0 : mUsers.size();
    }

    @Override
    public long getItemId(int position) {
        return mUsers.get(position).id;
    }

    class UserViewHolder extends RecyclerView.ViewHolder {

        TextView uid;
        TextView name;
        TextView age;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            uid = itemView.findViewById(R.id.textView);
            name = itemView.findViewById(R.id.textView1);
            age = itemView.findViewById(R.id.textView2);
        }
    }
}
