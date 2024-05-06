package com.example.visionaro.Threads;

import android.app.Activity;
import android.graphics.Bitmap;

import com.example.visionaro.Utility.UI_Connection;
import com.example.visionaro.Utility.Voice;


public class CurrencyDetection extends Thread {
    Activity activity;
    Bitmap bitmap;
    String s;

    public CurrencyDetection(Activity activity, Bitmap bitmap) {
        this.activity = activity;
        this.bitmap = bitmap;
    }
    public void reSetData(Activity activity, Bitmap bitmap){
        this.activity = activity;
        this.bitmap = bitmap;
    }
    @Override
    public void run() {
        s = UI_Connection.currency(bitmap, activity);
        Voice.speak(activity, s, true);
    }
}
