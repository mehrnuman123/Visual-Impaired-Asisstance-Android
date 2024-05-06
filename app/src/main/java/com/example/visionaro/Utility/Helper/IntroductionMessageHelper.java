package com.example.visionaro.Utility.Helper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.visionaro.Threads.IntroductionMessage;
import com.example.visionaro.Utility.Voice;

import static android.content.Context.MODE_PRIVATE;

public class IntroductionMessageHelper {
    Activity activity;

    Context context;

    boolean firstStartArabic, firstStartEnglish;
    SharedPreferences prefArabic, prefEnglish;
    SharedPreferences.Editor editorA, editorE;
    IntroductionMessage arIntroductionMessage, enIntroductionMessage;

    public IntroductionMessageHelper(Activity activity, Context context) {
        this.activity = activity;

        this.context = context;


    }

    public boolean introductionMessage(boolean hasCameraPermission) {
        prefArabic = activity.getSharedPreferences("arabicIntro", MODE_PRIVATE);
        prefEnglish = activity.getSharedPreferences("EnglishIntro", MODE_PRIVATE);
        firstStartArabic = prefArabic.getBoolean("arabicIntro", true);
        firstStartEnglish = prefEnglish.getBoolean("EnglishIntro", true);

        if (Voice.Language.equals("en")) {
            Log.d("lang", Voice.Language);
            if (firstStartEnglish) {
                return removeIntroductionMessageEnglish(hasCameraPermission);
            }
        } else {
            if (firstStartArabic) {
                return removeIntroductionMessageArabic(hasCameraPermission);
            }
        }
        return true;
    }


    public boolean removeIntroductionMessageArabic(boolean hasCameraPermission) {
        if (hasCameraPermission) {
            arIntroductionMessage = new IntroductionMessage(activity, "AppCommands/welcomeMessage.mp3");
            arIntroductionMessage.start();
            prefArabic = activity.getSharedPreferences("arabicIntro", MODE_PRIVATE);
            editorA = prefArabic.edit();
            editorA.putBoolean("arabicIntro", false);
            editorA.apply();

        }
        return false;
    }

    public boolean removeIntroductionMessageEnglish(boolean hasCameraPermission) {
        if (hasCameraPermission) {
            //todo : put the english message

            enIntroductionMessage = new IntroductionMessage(activity, "AppCommands/englishWelcomeMessage.mp3");
            enIntroductionMessage.start();
            prefEnglish = activity.getSharedPreferences("EnglishIntro", MODE_PRIVATE);
            editorE = prefEnglish.edit();
            editorE.putBoolean("EnglishIntro", false);
            editorE.apply();

        }
        return false;
    }
}
