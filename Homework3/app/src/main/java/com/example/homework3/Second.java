package com.example.homework3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class Second extends AppCompatActivity {
    String sex,radio,password,add,boxString;
    ArrayList<String> box;
    int boxNum;
    TextView textView_add,textView_sex,textView_radio,textView_box,textView_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent intent=getIntent();
        sex="性别："+intent.getStringExtra("sex");
        radio="观点："+intent.getStringExtra("radio");
        password="密码："+intent.getStringExtra("password");
        add="用户名："+intent.getStringExtra("editText");
        box=intent.getStringArrayListExtra("box");
        boxString="喜好："; for(int i = 0; i<box.size(); i++) boxString=boxString+box.get(i)+" ";

        textView_add=(TextView)findViewById(R.id.text_view_add);
        textView_add.setText(add);
        textView_sex=(TextView)findViewById(R.id.text_view_sex);
        textView_sex.setText(sex);
        textView_radio=(TextView)findViewById(R.id.text_view_radio);
        textView_radio.setText(radio);
        textView_box=(TextView)findViewById(R.id.text_view_box);
        textView_box.setText(boxString);
        textView_password=(TextView)findViewById(R.id.text_view_password);
        textView_password.setText(password);
    }
}
