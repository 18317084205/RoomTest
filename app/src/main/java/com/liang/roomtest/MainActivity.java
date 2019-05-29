package com.liang.roomtest;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.liang.room.DataSourceModel;
import com.liang.roomtest.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding viewDataBinding;
    UserViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewDataBinding.setRoomObserve(this);
        viewModel = DataSourceModel.getViewModel(this, UserViewModel.class);
//        databaseInit();
    }

    private void databaseInit() {
        for (int i = 0; i < 100; i++) {
            User user = new User();
            user.id = i + 1;
            user.name = "test" + user.id;
            user.age = 10 + i;
            viewModel.insert(user);
        }
    }

    public void goRoomTest() {
        startActivity(new Intent(this, RoomActivity.class));
    }

    public void goRoomObserve() {
        startActivity(new Intent(this, RoomObserveActivity.class));
    }
}
