# Learn Android
记录学习移动应用开发的点滴
## 2020/03/21
上传了Homework2（看似简单，其实%……*）

* 设置了全局变量，然后用 TextView 的 setText() 更新界面内容。
```
public static int first_times=0,second_times=0,third_times=0;
```
```
public void update_times(){
    TextView textView=(TextView)findViewById(R.id.times);
    String tmp="Click First "+First.first_time+" times\n"
        +"Click Second "+First.second_time+" times\n"
        +"Click Third "+First.third_time+" times";
    textView.setText(tmp);
}
```
* 为了可以点击“Exit”就退出程序，编写了public 类 ActivityCollector，其中包含public static成员变量List\<Acitivity\> activities，和三个成员方法addActivity(Activity activity)、removeActivity(Activity activity)、finishAll()。
然后重写每个Activity的onCreate、onDestroy方法。
```
@Override
protected void onCreate(Bundle savedInstanceState) {
    ······
    ActivityCollector.addActivity(this);
    ······
}
```
```
@Override
protected void onDestroy() {
    ActivityCollector.removeActivity(this);
    super.onDestroy();
}
```

