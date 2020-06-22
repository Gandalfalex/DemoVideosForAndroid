package com.example.tablet1_video;

import android.view.WindowManager;

public class ScreenSetting {

    public static WindowManager.LayoutParams turnOnDisplayFull(WindowManager.LayoutParams params){
        if (params == null) return null;
        params.screenBrightness = 1;
        params.flags |= WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON;
        return params;
    }

    public static WindowManager.LayoutParams turnOffDisplayFull(WindowManager.LayoutParams params){
        if (params == null) return null;
        params.screenBrightness = 0;
        params.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        return params;
    }
}
