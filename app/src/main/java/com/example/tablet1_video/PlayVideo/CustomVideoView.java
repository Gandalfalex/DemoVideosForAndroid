package com.example.tablet1_video.PlayVideo;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Pair;
import android.widget.VideoView;

import com.example.tablet1_video.FileInformation;

public class CustomVideoView extends VideoView {

    private Pair<Integer,Integer> adaptedVideoSize = new Pair<Integer, Integer>(0,0);

    public CustomVideoView(Context context) {
        super(context);
    }

    public CustomVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomVideoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    //translate video size to display size
    public void setAdaptedVideoSize(String videoName, Context context){
        Uri videoUri = FileInformation.getFileUri(context, videoName);
        Pair<Integer,Integer> originalVideoSize = FileInformation.getVideoSize(videoUri, context);
        Pair<Integer,Integer> display = FileInformation.getScreenSize(context);

        float scale_x =  ((float)originalVideoSize.first / (float) display.first);
        float scale_y =  ((float)originalVideoSize.second / (float) display.second);
        float new_scale = Math.max(scale_x, scale_y);
        this.adaptedVideoSize = new Pair<>( ((int) Math.floor(originalVideoSize.first / new_scale)), ((int) Math.floor(originalVideoSize.second / new_scale)));
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(adaptedVideoSize.first, adaptedVideoSize.second);
        setMeasuredDimension(adaptedVideoSize.first, adaptedVideoSize.second);
    }



}
