package com.example.visionaro.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.visionaro.R;
import com.example.visionaro.Utility.Configurations.BitmapConfiguration;
import com.example.visionaro.Utility.Configurations.CameraConfiguration;
import com.example.visionaro.Utility.Gestures;
import com.example.visionaro.Utility.Helper.IntroductionMessageHelper;
import com.example.visionaro.Utility.Helper.TabsSwipeHelper;
import com.example.visionaro.Utility.Helper.ThreadHelper;
import com.example.visionaro.Utility.UI_Connection;
import com.example.visionaro.Utility.Voice;
import com.github.pwittchen.swipe.library.rx2.Swipe;
import com.pedromassango.doubleclick.DoubleClick;
import com.pedromassango.doubleclick.DoubleClickListener;

import androidx.appcompat.app.AppCompatActivity;
import io.fotoapparat.view.CameraView;
import io.fotoapparat.view.FocusView;

public class MainActivity extends AppCompatActivity {

    private final int CameraCode = 1;
    BitmapConfiguration bitmapConfiguration;

    //layout
    LinearLayout linearLayout;
    //threads
    ThreadHelper threadHelper;
    TabsSwipeHelper tabsSwipeHelper;
    IntroductionMessageHelper introductionMessageHelper;

    private FocusView focusView;
    private boolean hasCameraPermission = false;
    private CameraView cameraView;
    private CameraConfiguration cameraConfigurations;
    //SWIP
    private Swipe swipe;
    private Button schtbn;
    private Gestures gestures;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Voice.init(this);
        Voice.initToggle(this);

        importViews();
        cameraConfigurations = new CameraConfiguration(cameraView, this, focusView);
        if (hasCameraPermission)
            cameraConfigurations.startCamera();
        bitmapConfiguration = new BitmapConfiguration();


        threadHelper = new ThreadHelper(this, bitmapConfiguration, cameraConfigurations, getApplication());
        introductionMessageHelper = new IntroductionMessageHelper(this, this);
        Gestures.swipesNumber = Voice.Language.equals("ar") ? 3 : 5;
        swipeConfiguration();
        tabsSwipeHelper = new TabsSwipeHelper(gestures, threadHelper);


        ActivityHelper.hideNotificationBar(this);

        UI_Connection.fillMap();
        if (hasCameraPermission)
            introductionMessageHelper.introductionMessage(hasCameraPermission);
        mainScreen();


    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        swipe.dispatchTouchEvent(event);
        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (hasCameraPermission) {
            cameraConfigurations.startCamera();

        } else {
            cameraConfigurations.requestCameraPermission(this);
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (hasCameraPermission) {

            cameraConfigurations.KillCamera();
        }
        Voice.release();
        threadHelper.killAllThreadsAndReleaseVoice();


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == CameraCode) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                hasCameraPermission = true;
                introductionMessageHelper.introductionMessage(hasCameraPermission);
                cameraConfigurations.startCamera();


            } else {

                hasCameraPermission = false;


                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void mainScreen() {
        final Activity activity = this;
        linearLayout.setOnClickListener(new DoubleClick(new DoubleClickListener() {

            @Override
            public void onSingleClick(View view) {
                if (hasCameraPermission)
                    tabsSwipeHelper.tabs(activity);
            }

            @Override
            public void onDoubleClick(View view) {
                if (hasCameraPermission) {
                    threadHelper.flashToggleThread();

                }
            }
        }));

        linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (hasCameraPermission) {
                    Log.d("lang1" , Voice.Language);
                    Voice.Language = Voice.Language.equals("ar") ? "en" : "ar";
                    Log.d("lang1" , Voice.Language);
                    Voice.toggleLang(MainActivity.this);


                    boolean t = introductionMessageHelper.introductionMessage(hasCameraPermission);
                    if (t) {
                        threadHelper.languageToggleThread();
                    }

                    Gestures.swipesNumber = Voice.Language.equals("ar") ? 3 : 5;
                }
                return true;
            }
        });
    }

        private void swipeConfiguration() {
        swipe = new Swipe();
        gestures = new Gestures();
        swipe.setListener(gestures);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (hasCameraPermission) {

            cameraConfigurations.KillCamera();
        }
        Voice.release();
        threadHelper.killAllThreadsAndReleaseVoice();

    }

    private void importViews() {
        cameraView = findViewById(R.id.cameraView);
        linearLayout = findViewById(R.id.linearLayout);
        focusView = findViewById(R.id.focusView);
    }


}
