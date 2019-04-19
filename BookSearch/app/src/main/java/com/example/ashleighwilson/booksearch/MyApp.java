package com.example.ashleighwilson.booksearch;

import android.app.Application;
import android.content.Context;

import com.example.ashleighwilson.booksearch.dagger.Injector;

public class MyApp extends Application {

    static Context mContext;
    private static MyApp myApp;

    public MyApp() {
        mContext = this;
    }

    public static MyApp getInstance() {
        return myApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApp = this;
        Injector.getInstance().init(this);
    }
}
