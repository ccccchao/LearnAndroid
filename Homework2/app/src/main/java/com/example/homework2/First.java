package com.example.homework2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class First extends AppCompatActivity {

    public static int first_time=0, second_time=0, third_time=0;

    public void update_times(){
        TextView textView=(TextView)findViewById(R.id.times);
        String tmp="Click First "+first_time+" times\n"
                +"Click Second "+second_time+" times\n"
                +"Click Third "+third_time+" times";
        textView.setText(tmp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.first_item:
                first_time++;
                update_times();
                Toast.makeText(First.this,"The current activity is First",Toast.LENGTH_SHORT).show();
                break;
            case R.id.second_item:
                second_time++;
                update_times();
                Intent intent2=new Intent(First.this,Second.class);
                startActivity(intent2);
                break;
            case R.id.third_item:
                third_time++;
                update_times();
                Intent intent3=new Intent(First.this,Third.class);
                startActivity(intent3);
                break;
            case R.id.exit_item:
                ActivityCollector.finishAll();
                break;
            default:
                break;
        }
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        update_times();
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        ActivityCollector.removeActivity(this);
        super.onDestroy();
    }
}
