package com.wiggins.service.utils;

import android.util.Log;

/**
 * @Description Log工具类
 * @Author 一花一世界
 */
public class LogUtil {

    private final static boolean DEBUG = true;

    public static void v(String msg) {
        if (DEBUG) {
            Log.v(Constant.LOG_TAG, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (DEBUG) {
            Log.v(tag, msg);
        }
    }

    public static void d(String msg) {
        if (DEBUG) {
            Log.d(Constant.LOG_TAG, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void i(String msg) {
        if (DEBUG) {
            Log.i(Constant.LOG_TAG, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (DEBUG) {
            Log.i(tag, msg);
        }
    }

    public static void w(String msg) {
        if (DEBUG) {
            Log.w(Constant.LOG_TAG, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (DEBUG) {
            Log.w(tag, msg);
        }
    }

    public static void e(String msg) {
        if (DEBUG) {
            Log.e(Constant.LOG_TAG, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (DEBUG) {
            Log.e(tag, msg);
        }
    }
}
