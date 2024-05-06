package com.example.visionaro.Threads;

import android.app.Activity;

import com.example.visionaro.Utility.Configurations.CameraConfiguration;

public class FlashToggle extends Thread {
    CameraConfiguration cameraConfigurations;
    Activity activity;
    public FlashToggle(Activity activity ,CameraConfiguration cameraConfigurations){
        this.activity = activity;
        this.cameraConfigurations = cameraConfigurations;
    }

    @Override
    public void run() {

        cameraConfigurations.toggleFlash(activity);
    }
}
