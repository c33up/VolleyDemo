package com.css.volleytest.volley;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class RequestQueueUtil {

    private static RequestQueue sRequestQueue;

    public static RequestQueue getRequestQueue(Context context) {
        if (sRequestQueue == null) {
            synchronized (RequestQueue.class) {
                if (sRequestQueue == null) {
                    sRequestQueue = Volley.newRequestQueue(context);
                }
            }
        }
        return sRequestQueue;
    }

}
