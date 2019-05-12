package com.liang.roomtest;

import android.arch.lifecycle.Observer;
import android.arch.paging.PagedList;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.liang.room.ViewModelFactory;
import com.liang.roomtest.databinding.ActivityMainBinding;

import java.util.List;

import static com.liang.room.DataSourceModel.*;


public class MainActivity extends AppCompatActivity {

    UserAdapter adapter;
    ActivityMainBinding viewDataBinding;
    UserViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
//        viewModel = ViewModelProviders.of(this, new ViewModelFactory<UserViewModel>() {
//            @Override
//            public UserViewModel getViewModel() {
//                return new UserViewModel(App.appDatabase.userDao());
//            }
//        }).bindAllData(UserViewModel.class);
        viewModel = getViewModel(this, new ViewModelFactory<UserViewModel>() {
            @Override
            public UserViewModel getViewModel() {
                return new UserViewModel(App.appDatabase.userDao());
            }
        }, UserViewModel.class);
        adapter = new UserAdapter();
        viewDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        viewDataBinding.recyclerView.setAdapter(adapter);
        databaseSet();
        viewModel.bindAllData();
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
                user.name = "这个Id = " + id;
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
                user.name = "这个Id = " + id;

                viewModel.delete(user);
            }
        });

        viewModel.getAllData().observe(this, new Observer<PagedList<User>>() {
            @Override
            public void onChanged(@Nullable PagedList<User> users) {
                adapter.submitList(users);
            }
        });

    }
}
