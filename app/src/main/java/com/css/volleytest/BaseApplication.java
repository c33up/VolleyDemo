package com.css.volleytest;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.css.volleytest.volley.RequestQueueUtil;

public class BaseApplication extends Application {
    public static RequestQueue sRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        sRequestQueue = RequestQueueUtil.getRequestQueue(this);
    }
}
