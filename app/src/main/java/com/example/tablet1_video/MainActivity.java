package com.example.tablet1_video;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.tablet1_video.PlayVideo.PlayVideoActivity;
import com.example.tablet1_video.Sensor.SensorPlayerActivity;

public class MainActivity extends AppCompatActivity  {

    public Button videoLoopBtn;
    public Button sensorVideoBtn_truck;
    public Button sensorVideoBtn_industry;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        videoLoopBtn = findViewById(R.id.startVideoLoopBtn);
        sensorVideoBtn_truck = findViewById(R.id.startSensorVideoPlayBtn_truck);
        sensorVideoBtn_industry = findViewById(R.id.startSensorVideoPlayBtn_industry);

         videoLoopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                Intent intent = new Intent(MainActivity.this, PlayVideoActivity.class);
                startActivity(intent);
            }
        });
        sensorVideoBtn_truck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SensorPlayerActivity.class);
                Bundle para = new Bundle();
                para.putString("videoName", "truck");
                intent.putExtras(para);
                startActivity(intent);
            }
        });


        sensorVideoBtn_industry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle para = new Bundle();
                para.putString("videoName", "industry");
                Intent intent = new Intent(MainActivity.this, SensorPlayerActivity.class);
                intent.putExtras(para);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed(){
        finish();
    }


}
