# room-helper

#### Android日常开发中都会用到数据库，有些时候可能需要实时观察数据状态对列表进行刷新或是节省资源进行分页查询展示，Google在[Jetpack](https://developer.android.google.cn/jetpack)组件中提供了数据库（[Room](https://developer.android.google.cn/topic/libraries/architecture/room)）和分页（[Paging](https://developer.android.google.cn/topic/libraries/architecture/paging/)）来帮助我们处理以上问题，而room-helper是对Room和Paging的二次封装，让我们在开发中更简单的去处理数据库的操作。

# 使用room-helper
#### 1. 在module的Gradle中加入
```
implementation 'com.liang:room-helper:1.0.7'

//官方库，具体版本见官方
implementation "android.arch.persistence.room:runtime:1.1.1"
implementation "android.arch.persistence.room:rxjava2:1.1.1"
implementation 'android.arch.paging:runtime:1.0.1'
implementation "android.arch.paging:common:1.0.1"
implementation "android.arch.paging:rxjava2:1.0.0-rc1"
implementation "io.reactivex.rxjava2:rxandroid:2.0.1"

annotationProcessor 'android.arch.persistence.room:compiler:1.1.1'
```

#### 2. 主要说明如下，其余见demo源码
```
public class UserViewModel extends DataSourceModel<User, UserDao> {

    ...

    @Override
    protected UserDao setDao() {
        App app = getApplication();
        return app.appDatabase.userDao();
    }

    @Override
    protected void onZeroItemsLoaded() {
        Log.e("UserViewModel", "onZeroItemsLoaded: ...");
        //数据库没查询到0条数据,加载网络数据
        getDataWithNetwork();
    }

    @Override
    protected void onItemAtEndLoaded(User itemAtEnd) {
        Log.e("UserViewModel", "onItemAtEndLoaded: " + itemAtEnd.id);
        //用户已到达列表末尾,加载网络数据
        getDataWithNetwork();
    }

    private void getDataWithNetwork() {
        Toast.makeText(getApplication(), "去网络拉取数据。。。", Toast.LENGTH_SHORT).show();
        //模拟网络加载
        int index = 0;
        List<User> users = new ArrayList<>();
        while (index < 50) {
            index++;
            User user = new User();
            user.name = "网络数据: " + index;
            user.age = index;
            users.add(user);
        }
        insert(users);
    }
    ...
}
```

```
public class RoomObserveActivity extends AppCompatActivity {

    ...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_room_observe);
        viewModel = DataSourceModel.getViewModel(this, UserViewModel.class);
        adapter = new UserObserveAdapter();
        viewDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        viewDataBinding.recyclerView.setAdapter(adapter);
        viewModel.getDataObserve().observe(this, new Observer<PagedList<User>>() {
            @Override
            public void onChanged(@Nullable PagedList<User> users) {
                adapter.submitList(users);
            }
        });
    }

    ...
}
```
