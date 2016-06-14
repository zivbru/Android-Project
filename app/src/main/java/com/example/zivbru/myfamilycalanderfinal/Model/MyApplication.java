package com.example.zivbru.myfamilycalanderfinal.Model;

import android.app.Application;
import android.content.Context;

/**
 * Created by zivbru on 4/17/2016.
 */
public class MyApplication extends Application {

    private static Context context;

    public MyApplication(Context context) {
        this.onCreate(context);
    }
    public MyApplication( ) {

    }

    public void onCreate(Context context) {
        super.onCreate();
        MyApplication.context =context;
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }
}

