package com.tt.circualrectview.utils;

import android.graphics.Rect;

/**
 * Created by TuoZhaoBing on 2016/4/14 0014.
 */
public class MeasureUtils {
    public static final String TAG = "MeasureUtils";
    public static Rect getRect(int centerx,int centy,int width,int height){
        return new Rect(centerx-width/2,centy-height/2,centerx+width/2,centy+height/2);
    }
}
