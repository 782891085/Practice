package com.ekemp.practice;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Administrator on 15-11-24.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}
