package com.example.visionaro.Models.PreTrainedModel;

import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions;

public class TextTranslation {

     FirebaseModelDownloadConditions conditions;
     FirebaseTranslatorOptions options;
     FirebaseTranslator  englishGermanTranslator;
     public TextTranslation(){
         options =
                 new FirebaseTranslatorOptions.Builder()
                         .setSourceLanguage(FirebaseTranslateLanguage.EN)
                         .setTargetLanguage(FirebaseTranslateLanguage.UR)
                         .build();
         englishGermanTranslator =
                 FirebaseNaturalLanguage.getInstance().getTranslator(options);

         conditions = new FirebaseModelDownloadConditions.Builder()
                 .requireWifi()
                 .build();
     }

    public  void translation(final String text) {
        
        englishGermanTranslator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void v) {
                                englishGermanTranslator.translate(text)
                                        .addOnSuccessListener(
                                                new OnSuccessListener<String>() {
                                                    @Override
                                                    public void onSuccess(String translatedText) {
                                                        Log.d("translation", translatedText);
                                                    }
                                                })
                                        .addOnFailureListener(
                                                new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(Exception e) {
                                                        // Error.
                                                        Log.d("translation", "no text translated");
                                                        // ...
                                                    }
                                                });
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(Exception e) {
                                // Model couldn’t be downloaded or other internal error.
                                Log.d("translation", "model not downloaded");
                                // ...
                            }
                        });

    }
}
