package com.example.tablet1_video.PlayVideo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.tablet1_video.R;
import com.example.tablet1_video.ScreenSetting;

public class PlayVideoActivity extends AppCompatActivity implements VideoUtil.FinishedVideoListener {

    private CustomVideoView videoView;
    private boolean hideActionBar = true;
    private String videoName = "leben_vid";
    private boolean repeatVideo = true;
    private VideoUtil videoUtil;
    private static PlayVideoActivity playVideoActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_video);
        videoView = findViewById(R.id.videoView);

        playVideoActivity = this;
        videoUtil = new VideoUtil();

        Bundle parameters = getIntent().getExtras();
        if (parameters != null) setMemberVariables(parameters);

        //hide navigation and status bar
        if (hideActionBar) getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        videoView.setAdaptedVideoSize(videoName, PlayVideoActivity.this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        getWindow().setAttributes(ScreenSetting.turnOnDisplayFull(getWindow().getAttributes()));
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        videoUtil.playRawVideo(PlayVideoActivity.this, videoView, videoName, repeatVideo);
    }


    /**
     * sets the member variables if set
     * @param bundle
     */
    private void setMemberVariables(Bundle bundle){
        if(bundle.containsKey("video_name"))    this.videoName = bundle.getString("video_name");
        if(bundle.containsKey("repeat_video"))  this.repeatVideo = bundle.getBoolean("repeat_video");


        if(!repeatVideo){
            videoUtil.setFinishedVideoListener(this);
        }
    }

    /**
     * if the video is not on repeat, the activity closes after the video finished
     */
    @Override
    public void finishedVideo() {
        finish();
    }

    /**
     * destroys itself when called by parent activity
     */
    public static void finishVideoThroughCall(){
        if(playVideoActivity != null) playVideoActivity.finishedVideo();
    }
}
