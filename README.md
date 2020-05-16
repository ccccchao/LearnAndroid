# Learn Android
记录学习移动应用开发的点滴
## 2020/03/21
上传了Homework2（看似简单，其实%……*）

* 设置了全局变量，然后用 TextView 的 setText(String string) 更新界面内容。
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
## 2020/03/30
上传了Homework3

主要用到了：
* EditText（&密码框）、Spinner、RadioGroup、CheckBox、ImageView
* 布局嵌套
* 在activity间传递信息

注：
* Spinner要设置ArrayListAdapter，选择逻辑的处理是重写OnItemSelectedListener函数。
* RadioGroup是编写setOnCheckedChangeListener函数。清除选择项的话是调用clearCheck()。
* CheckBox是让每一个选项实例化，然后利用is_checked编写处理逻辑。清除选择项是分别调用setChecked(false)。
* ImageView有个setImageResource函数挺好玩的。
* 用Log来debug好像有点意思。

## 2020/05/16
上传了Homework9-VideoPlaye
* 实现了视频播放、暂停、重新播放
* 比较简单，主要是VideoView的使用+申请权限
* 卡了较长时间的是文件路径方面，无论是路径函数的返回值、还是手机实际的存储路径名，都是坑：
* * Environment.getExternalStorageDirectory() 和 Environment.getExternalStorageDirectory().getAbsolutePath()——返回值都不是根目录的路径，而是/storage/emulated/0
* * getExternalFileDir("").getAbsolutePath() 的返回值是/storage/emulated/0/Android/data/（包名）/files
* * 而在手机实际存储中，“/storage/emulated/0/Android”就是根目录下的“Android”文件夹，但是为什么根目录不等于“/storage/emulated/0”呢？？？疑惑了。。。