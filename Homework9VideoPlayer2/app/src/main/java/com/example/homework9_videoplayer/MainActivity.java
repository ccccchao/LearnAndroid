package com.example.homework9_videoplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    VideoView videoView;
    Button startButton,pauseButton,replayButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoView=(VideoView) findViewById(R.id.video_view);
        startButton=(Button) findViewById(R.id.start_button);
        pauseButton=(Button) findViewById(R.id.pause_button);
        replayButton=(Button) findViewById(R.id.replay_button);

        startButton.setOnClickListener(this);
        pauseButton.setOnClickListener(this);
        replayButton.setOnClickListener(this);

        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }else{
            initVideoPath();
        }
    }

    private void initVideoPath() {
        File file=new File(getExternalFilesDir("").getAbsolutePath(),"cat.mp4");
//        File file=new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"cat.mp4");
        Log.d("lyc","File Address is "+file.getPath());
        Log.d("lyc","\"video.setVideoPath(file.getPath)\" function is called.");
        if(!file.exists()){
            Toast.makeText(this,"文件未打开！",Toast.LENGTH_SHORT).show();
            Log.d("lyc","File doesn't exist!");
        }else{
            videoView.setVideoPath(file.getPath());
            Log.d("lyc","\"video.setVideoPath(file.getPath)\" function finished.");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length>0&&grantResults[0]== PERMISSION_GRANTED){
                    initVideoPath();
                }else{
                    Toast.makeText(this,"拒绝权限将无法使用程序！",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start_button:
                Log.d("lyc","Start Button Is Pressed.");
                if(!videoView.isPlaying()){
                    videoView.start();
                    Log.d("lyc","\"videoView.start()\" function finished.");
                }
                break;
            case R.id.pause_button:
                Log.d("lyc","Pause Button Is Pressed.");
                if(videoView.isPlaying()){
                    videoView.pause();
                    Log.d("lyc","\"videoView.pause()\" function finished.");
                }
                break;
            case R.id.replay_button:
                Log.d("lyc","Replay Button Is Pressed.");
                if(videoView.isPlaying()){
                    videoView.resume();
                }else{
                    videoView.resume();
                    videoView.start();
                }

                Log.d("lyc","\"videoView.resume()\" function finished.");
                break;
            default:
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(videoView!=null){
            videoView.suspend();
        }
    }
}
