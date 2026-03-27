package com.btl_ptit.kiemtra2_android_ptit;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
    private static String TAG = "MyApplicationTAG";
    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
    }
    public static Context getAppContext() {
        return appContext;
    }
}
