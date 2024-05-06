package com.example.visionaro.Threads;

import android.app.Activity;

import com.example.visionaro.Utility.Voice;

public class LanguageToggle extends Thread {
    Activity activity;

    public LanguageToggle(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void run() {
        Voice.speak(activity, "AppCommands/application language is English.mp3", true);

    }

}
