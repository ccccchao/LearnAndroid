package com.example.homework3;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editText,editText_password;
    private Button button_hand_in;
    private RadioGroup radioGroup;
    private CheckBox cute,cool,beautiful,funny;
    private String string,message,password,sex,radio;
    private boolean is_radio_checked, is_box_checked;
    private ProgressBar progressBar;
    private CountDownTimer countDownTimer;
    private ImageView imageView;
    private int times;
    private List<String> list;
    private ArrayAdapter<String> arrayAdapter;
    private Spinner spinner;
    private ArrayList<String> box;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ActionBar actionBar= getSupportActionBar();
//        if(actionBar!=null) actionBar.hide();
        Log.d("lyc_flag","flag1");
        editText=(EditText) findViewById(R.id.edit_text_1);
        editText_password=(EditText)findViewById(R.id.edit_text_password);
        button_hand_in=(Button) findViewById(R.id.button_hand_in);
        radioGroup=(RadioGroup)findViewById(R.id.radio_group_1);
        cute=(CheckBox)findViewById(R.id.cute);
        cool=(CheckBox)findViewById(R.id.cool);
        beautiful=(CheckBox)findViewById(R.id.beautiful);
        funny=(CheckBox)findViewById(R.id.funny);
        string=""; message=""; password=""; sex=""; radio="";
        is_box_checked=false;
        is_radio_checked=false;
        progressBar=(ProgressBar)findViewById(R.id.progress_bar_1);
        imageView=(ImageView)findViewById(R.id.image_view_1);
        times=0;
        spinner=(Spinner)findViewById(R.id.spinner_1);
        list=new ArrayList<String>();
        list.add("");
        list.add("男");
        list.add("女");
        arrayAdapter=new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, list);
        /* arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item); */
        spinner.setAdapter(arrayAdapter);
        box=new ArrayList<String>();
        intent=new Intent(MainActivity.this,Second.class);

        button_hand_in.setOnClickListener(this);
        imageView.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.good_look:
                        Log.d("lyc_flag","checked good_look");
                        message="谢谢您的支持~";
                        radio="好看";
                        is_radio_checked=true;
                        break;
                    case R.id.just_so_so:
                        Log.d("lyc_flag","checked just_so_so");
                        message="希望下次的图片您会喜欢~";
                        radio="海星";
                        is_radio_checked=true;
                        break;
                    case R.id.bad_look:
                        Log.d("lyc_flag","checked bad_look");
                        message="审美鸿沟啊审美鸿沟";
                        radio="阿格雷";
                        is_radio_checked=true;
                        break;
                    default:
                        Log.d("lyc_flag","checked default");
                        break;
                }
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected=parent.getItemAtPosition(position).toString();
                if(selected.equals("男")) sex="男";
                else if(selected.equals("女")) sex="女";
                else sex="未知";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sex="未知";
            }
        });

        countDownTimer=new CountDownTimer(1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this,string,Toast.LENGTH_SHORT).show();
            }
        };
}

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button_hand_in:
                progressBar.setVisibility(View.VISIBLE);
                is_box_checked=(cute.isChecked()||cool.isChecked()||beautiful.isChecked()||funny.isChecked());
                if(is_box_checked){
                    for(int i=0;i<box.size();i++) box.remove(i);
                    if(cute.isChecked()) box.add("可爱");
                    if(cool.isChecked()) box.add("酷炫");
                    if(beautiful.isChecked()) box.add("唯美");
                    if(funny.isChecked()) box.add("搞笑");
                }
                password = editText_password.getText().toString();
                if(is_radio_checked&&is_box_checked&&(!password.equals(""))){
                    radioGroup.clearCheck(); is_radio_checked=false;
                    cute.setChecked(false); cool.setChecked(false); beautiful.setChecked(false); funny.setChecked(false); is_box_checked=false;
                    editText_password.setText("");
                    intent.putExtra("sex",sex);
                    intent.putExtra("radio",radio);
                    intent.putExtra("box", box);
                    this.intent.putExtra("password",password);
                    string=editText.getText().toString();
                    if(!string.equals("")){
                        editText.setText("");
                        intent.putExtra("editText",string);
                        string="";
                    }
                    startActivity(this.intent);
                }
                else if((!is_radio_checked)&&(!is_box_checked)){
                    string="没有可提交的内容";
                }
                else if(!is_box_checked){
                    string="请填写您的喜好";
                }
                else if(!is_radio_checked){
                    string="请选择您对图片的看法";
                }
                else if(password.equals("")){
                    string="请输入密码";
                }
                string = editText.getText().toString();
                countDownTimer.start();
                break;
            case R.id.image_view_1:
                if(times%2==0){
                    imageView.setImageResource(R.drawable.watch_tv);
                    times++;
                }
                else{
                    imageView.setImageResource(R.drawable.hanta_image);
                    times++;
                }
                break;
            default:
                break;
        }
    }
}
