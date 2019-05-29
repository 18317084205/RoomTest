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
import com.liang.roomtest.databinding.ActivityRoomObserveBinding;


public class RoomObserveActivity extends AppCompatActivity {

    UserObserveAdapter adapter;
    ActivityRoomObserveBinding viewDataBinding;
    UserViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_room_observe);
        viewModel = DataSourceModel.getViewModel(this, UserViewModel.class);
        adapter = new UserObserveAdapter();
        viewDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        viewDataBinding.recyclerView.setAdapter(adapter);
        databaseSet();
    }

    int age = 10;

    private void databaseSet() {
        viewDataBinding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String id = viewDataBinding.editText.getText().toString();
                if (id.isEmpty()) {
                    return;
                }
                User user = new User();
                user.id = Integer.parseInt(id);
                user.name = "add/update = " + id;
                user.age = age;
                age++;
                viewModel.insert(user);
            }
        });

        viewDataBinding.button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String id = viewDataBinding.editText1.getText().toString();
                if (id.isEmpty()) {
                    return;
                }

                User user = new User();
                user.id = Integer.parseInt(id);

                viewModel.delete(user);
            }
        });

        viewModel.getDataObserve().observe(this, new Observer<PagedList<User>>() {
            @Override
            public void onChanged(@Nullable PagedList<User> users) {
                adapter.submitList(users);
            }
        });

    }
}
