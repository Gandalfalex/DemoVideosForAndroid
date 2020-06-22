package com.example.tablet1_video;

import android.content.Context;
import android.graphics.Point;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.util.Pair;
import android.view.Display;
import android.view.WindowManager;

public class FileInformation {


    public static Uri getFileUri(Context context, String fileName){
        String pkgName = context.getPackageName();
        int resID = context.getResources().getIdentifier(fileName, "raw", pkgName);
        return Uri.parse("android.resource://" + pkgName + "/" + resID);
    }

    /**
     * get the original display size
     * @param videoUri the video uri specifies the video file
     * @param context
     * @return video size
     */
    public static Pair<Integer, Integer> getVideoSize(Uri videoUri, Context context){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(context , videoUri);
        int width = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));
        int height = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));
        retriever.release();
        return new Pair<>(width,height);
    }

    /**
     * calculates the screen size
     * @param context
     * @return
     */
    public static Pair<Integer, Integer> getScreenSize(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display screenSize = wm.getDefaultDisplay() ;
        Point display = new Point();
        screenSize.getRealSize(display);
        return new Pair<>(display.x, display.y);
    }
}
