package com.example.tablet1_video.Sensor;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tablet1_video.PlayVideo.PlayVideoActivity;
import com.example.tablet1_video.R;
import com.example.tablet1_video.ScreenSetting;


public class SensorPlayerActivity extends AppCompatActivity implements SensorEventListener,
        StayAwakeHandler.OnTimeToWakeListener {

    private android.hardware.SensorManager sensorManager;
    private Sensor lightSensor;
    private PowerManager powerManager;
    private PowerManager.WakeLock wakeLock;
    private StayAwakeHandler stayAwake;
    private boolean itWasDark = true;
    private int threshold = 5;
    private String videoName = "";



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null && bundle.containsKey("videoName")) this.videoName = bundle.getString("videoName");
        prepareScreen();
        stayAwake = new StayAwakeHandler(new Handler());
        stayAwake.setStayAwakeHandler(this);
        setContentView(R.layout.sensor_manager);
    }

    /**
     * this method acquires a wakelock, so that the CPU can run while the Display is turned off
     * it sets the View in Fullscreen mode, hides the navigation bar and activates the immersive sticky mode to hide the nave bar
     * after some time
     * Screen should not be touchable after that
     */
    private void prepareScreen(){
        powerManager = (PowerManager) getSystemService(this.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, String.format("My Lock"));
        wakeLock.acquire();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        sensorManager = (android.hardware.SensorManager) getSystemService(this.SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
    }

    public Intent setParameters(Intent intent, String videoName, boolean repeat){
        Bundle parameters = new Bundle();
        parameters.putString("video_name", videoName);
        parameters.putBoolean("repeat_video", repeat);

        intent.putExtras(parameters);
        return intent;
    }


    /**
     * This method turn the screen off and clears old flags
     * sets brightness to min, screen while turn off after certain time
     * background to black
     */
    public void turnOffScreen(){
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        getWindow().setAttributes(ScreenSetting.turnOffDisplayFull(getWindow().getAttributes()));
    }


    /**
     * turn screen on or of, requires light sensor
     * Todo: add Setting file and auto detect routine
     * @param event
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.values[0] > threshold){
            if(itWasDark) {
                stayAwake.stopHandler();
                itWasDark = false;
                Intent intent = new Intent(SensorPlayerActivity.this, PlayVideoActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(setParameters(intent, videoName, false));
            }
        }
        else {
            if(!itWasDark) {
                PlayVideoActivity.finishVideoThroughCall();
                stayAwake.startStayAwake();
                turnOffScreen();
                itWasDark = true;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { }



    @Override
    protected void onResume(){
        super.onResume();
        sensorManager.registerListener(this, lightSensor, android.hardware.SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onStart() {
        super.onStart();
        sensorManager.registerListener(this, lightSensor, android.hardware.SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onDestroy() {
        // wakeLock.release();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onDestroy();
    }

    @Override
    public void awakeDevice() {
        stayAwake.stopHandler();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON, WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        stayAwake.startStayAwake();
    }


    @Override
    public void onBackPressed(){
        wakeLock.release();
        sensorManager.unregisterListener(this);
        finish();
    }

    


}
