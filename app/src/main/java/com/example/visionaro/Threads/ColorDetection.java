package com.example.visionaro.Threads;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.example.visionaro.Utility.UI_Connection;

public class ColorDetection extends Thread {
    Activity activity;
    Bitmap bitmap;

    public ColorDetection(Activity activity, Bitmap bitmap) {
        this.activity = activity;
        this.bitmap = bitmap;
    }
    public void reSetData(Activity activity, Bitmap bitmap){
        this.activity = activity;
        this.bitmap = bitmap;
    }
    @Override
    public void run() {
        //TODO ColorDetection
        UI_Connection.colorDetection(bitmap, activity);
    }

}
