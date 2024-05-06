package com.example.visionaro.Utility;

import android.app.Activity;
import android.app.Application;
import android.graphics.Bitmap;
import android.util.Log;

import com.example.visionaro.Models.Helper.CurrencyModelClassifier;
import com.example.visionaro.Models.Helper.DetectionModelClassifier;
import com.example.visionaro.Models.Models;
import com.example.visionaro.Models.PreTrainedModel.BarcodeRecognizer;
import com.example.visionaro.Models.PreTrainedModel.OCRRecognizer;
import com.example.visionaro.Models.Recognition;

import java.util.HashMap;
import java.util.List;

public class UI_Connection {
    private static DetectionModelClassifier detectionClassifier;
    private static List<Recognition> list;
    private static BarcodeRecognizer barcodeRecognizer;
    private static OCRRecognizer ocrRecognizer;
    private static ColorHelper color;
    private static HashMap<Integer, String[]> map;
    private static String[] Label;
    private static String[] conf;
    private static HashMap<String, String> hashMap = new HashMap<String, String>();
    private static CurrencyModelClassifier currencyClassifier;


    public static List<Recognition> detection(Bitmap bitmap, Activity activity) {
        detectionClassifier = new DetectionModelClassifier(activity,
                bitmap, Models.DETECTIONMODEL, Models.DETECTIONLABEL, true);
        detectionClassifier.classifer();
        list = detectionClassifier.getAllOutPut();

        return list;
    }

    public static String currency(Bitmap bitmap, Activity activity) {
        String s;
        currencyClassifier = new CurrencyModelClassifier(activity,
                bitmap, Models.CURRENCYMODEL, Models.CURRENCYLABEL, false);

        map = currencyClassifier.classifer();
        Label = map.get(0);
        conf = map.get(1);
        for (int i = 0; i < conf.length; i++) {
            Log.i("labels", Label[i]);

            Log.i("Conf", conf[i]);
        }

        if (Float.parseFloat(conf[conf.length - 1]) > 0.4) {
            s = hashMap.get(Label[conf.length - 1]);
        } else {
            s = Voice.getCanNot();
        }
        return s;
    }

    public static void get_Barcode(Bitmap bitmap, final Activity activity, Application application) {
        if (barcodeRecognizer == null)
            barcodeRecognizer = new BarcodeRecognizer();
        barcodeRecognizer.getBarcode(bitmap, activity, application);
    }

    public static void OCR(Bitmap bitmap, final Activity activity) {
        if (ocrRecognizer == null)
            ocrRecognizer = new OCRRecognizer();
        ocrRecognizer.OCR(bitmap, activity);
    }

    public static void colorDetection(Bitmap bitmap, final Activity activity) {
        if (color == null)
            color = new ColorHelper();
        color.getColor(activity, bitmap);
    }

    //static voice
    public static void fillMap() {


    }

}
