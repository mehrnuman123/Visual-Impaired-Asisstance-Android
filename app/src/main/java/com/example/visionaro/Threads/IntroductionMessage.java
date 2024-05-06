package com.example.visionaro.Threads;

import android.app.Activity;

import com.example.visionaro.Utility.Voice;

public class IntroductionMessage extends Thread {
    Activity activity;
    String S ;

    public IntroductionMessage(Activity activity , String s) {
        this.activity = activity;
        this.S = s;
    }

    @Override
    public void run() {
        Voice.speak(activity, S, true);
    }
}
