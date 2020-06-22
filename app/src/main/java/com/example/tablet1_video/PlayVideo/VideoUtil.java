package com.example.tablet1_video.PlayVideo;
import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.tablet1_video.FileInformation;


public class VideoUtil {

    public static final String LOG_TAG = "AndroidVideoView";
    public FinishedVideoListener callback;


    public void playRawVideo(Context context, final VideoView videoView, String resName, boolean loopVideo)  {
        try {
            if (loopVideo) enableLooping(videoView);
            else finishedVideo(videoView);
            videoView.setVideoURI(FileInformation.getFileUri(context, resName));
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    videoView.start();
                }
            });
           // videoView.start();
        }
        catch (Exception e) {
            Log.e(LOG_TAG, "Error Play Raw Video: " + e.getMessage());
            Toast.makeText(context,"Error Play Raw Video: "+ e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }



    /**
     * on completion listener
     * if the video is over, it gets restarted
     * @param videoView
     */
    private void enableLooping(final VideoView videoView){
       videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
           @Override
           public void onCompletion(MediaPlayer mp) {
               mp.seekTo(1);
               mp.start();
           }
       });
    }


    /**
     * listener gets implemented
     * @param videoView
     */
    private void finishedVideo(VideoView videoView){
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                callback.finishedVideo();
            }
        });
    }

    public void setFinishedVideoListener(FinishedVideoListener videoListener){
        callback = videoListener;
    }


    /**
     * this interface has to be implemented by the calling activity
     * Listener, fires if video is finished
     */
    public interface FinishedVideoListener{
        public void finishedVideo();
    }
}