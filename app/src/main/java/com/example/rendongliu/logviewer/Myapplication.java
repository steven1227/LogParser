package com.example.rendongliu.logviewer;

import android.app.Application;
import android.util.Log;

/**
 * Created by rendong.liu on 07/07/15.
 */
public class Myapplication extends Application {
    @Override
    public void onCreate() {
        Log.e(getClass().getName(),"App Start");
        super.onCreate();
    }
}
