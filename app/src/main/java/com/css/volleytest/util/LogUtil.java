package com.css.volleytest.util;

import android.util.Log;

import com.css.volleytest.BaseApplication;
import com.css.volleytest.BuildConfig;
import com.css.volleytest.R;

public class LogUtil {
    public static final boolean DEBUG = BuildConfig.DEBUG;

    public static final String TAG = BaseApplication.getInstance().getString(R.string.config_logcat_tag);

    public static void d(String msg) {
        d(TAG, msg);
    }

    public static void d(String tag, String msg) {
        if (DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void e(String msg) {
        e(TAG, msg);
    }

    public static void e(String tag, String msg) {
        if (DEBUG) {
            Log.e(tag, msg);
        }
    }

    public static void v(String msg) {
        v(TAG, msg);
    }

    public static void v(String tag, String msg) {
        if (DEBUG) {
            Log.v(tag, msg);
        }
    }

    public static void exception(String msg) {
        e(msg);
    }
}
