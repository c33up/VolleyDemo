package com.css.volleytest;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.css.volleytest.volley.RequestQueueUtil;

public class BaseApplication extends Application {
    public static RequestQueue sRequestQueue;
    private static BaseApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
        sRequestQueue = RequestQueueUtil.getRequestQueue(this);

    }

    public static BaseApplication getInstance(){
        return mInstance;
    }
}
