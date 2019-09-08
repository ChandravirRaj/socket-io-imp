package com.androboy.chaten;

import android.app.Application;
import android.content.Context;

public class ChatenApp extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getAppContext() {
        return context;
    }
}
