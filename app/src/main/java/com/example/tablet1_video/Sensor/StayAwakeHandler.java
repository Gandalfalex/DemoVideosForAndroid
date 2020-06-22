package com.example.tablet1_video.Sensor;


import android.os.Handler;

public class StayAwakeHandler {

    private Handler stayAwakeHandler;
    private int delayForAwake = 500000; // 8.3 min
    private OnTimeToWakeListener callback;


    /**
     * The runnable has a delay specified in the delayForAwake variable,
     * it starts the screen so the cpu wont get a chance to sleep
     */
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            callback.awakeDevice();
            stayAwakeHandler.postDelayed(this, delayForAwake);
        }
    };

    public StayAwakeHandler(Handler handler){
        this.stayAwakeHandler = handler;
    }


    public void startStayAwake(){
        stayAwakeHandler.postDelayed(mRunnable, delayForAwake);
    }

    public void stopHandler(){
        stayAwakeHandler.removeCallbacks(mRunnable);
    }

    public void setStayAwakeHandler(OnTimeToWakeListener onTimeToWakeListener){
        callback = onTimeToWakeListener;
    }

    public interface OnTimeToWakeListener{
        public void awakeDevice();
    }
}
