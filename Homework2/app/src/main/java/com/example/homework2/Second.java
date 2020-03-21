package com.example.homework2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AccelerateInterpolator;
import android.widget.TextView;
import android.widget.Toast;

public class Second extends AppCompatActivity {

    public void update_times(){
        TextView textView=(TextView)findViewById(R.id.times);
        String tmp="Click First "+First.first_time+" times\n"
                +"Click Second "+First.second_time+" times\n"
                +"Click Third "+First.third_time+" times";
        textView.setText(tmp);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        update_times();
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        ActivityCollector.removeActivity(this);
        super.onDestroy();
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
                First.first_time++;
                update_times();
                Intent intent1=new Intent(Second.this,First.class);
                startActivity(intent1);
                break;
            case R.id.second_item:
                First.second_time++;
                update_times();
                Toast.makeText(Second.this,"The current activity is Second",Toast.LENGTH_SHORT).show();
                break;
            case R.id.third_item:
                First.third_time++;
                update_times();
                Intent intent3=new Intent(Second.this,Third.class);
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
}
