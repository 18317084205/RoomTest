package com.liang.roomtest;

import android.arch.lifecycle.Observer;
import android.arch.paging.PagedList;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.liang.room.DataSourceModel;
import com.liang.roomtest.databinding.ActivityRoomBinding;

import java.util.List;


public class RoomActivity extends AppCompatActivity {

    UserAdapter adapter;
    ActivityRoomBinding viewDataBinding;
    UserViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_room);
        viewModel = DataSourceModel.getViewModel(this, UserViewModel.class);
        adapter = new UserAdapter();
        viewDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        viewDataBinding.recyclerView.setAdapter(adapter);
        databaseSet();
    }

    int age = 10;

    private void databaseSet() {
        viewDataBinding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                user.name = "test";
                user.age = age;
                age++;
                viewModel.insert(user);
            }
        });

        viewDataBinding.button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.getUsers();
            }
        });

        viewModel.getUserDate().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {
                adapter.setUsers(users);
            }
        });

    }
}
