package com.example.ashleighwilson.booksearch;

import android.app.Application;
import android.content.Context;

import com.example.ashleighwilson.booksearch.dagger.Injector;

public class MyApp extends Application {

    static Context mContext;

    public MyApp() {
        mContext = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Injector.getInstance().init(this);
    }
}
