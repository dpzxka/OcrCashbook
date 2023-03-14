package com.duxiaoman.ocrcashbook.util

import android.util.Log

object LogUtil {

    private const val TAG = "zhangtao"
    private const val VERBOSE = 1

    private const val DEBUG = 2

    private const val INFO = 3

    private const val WARN = 4

    private const val ERROR = 5

    //通过level的值，来控制日志的打印。
    private var level = VERBOSE

    fun v(tag: String=TAG, msg: String) {
        if (level <= VERBOSE) {
            Log.v(tag, msg)
        }
    }

    fun d(tag: String=TAG, msg: String) {
        if (level <= DEBUG) {
            Log.i(tag, msg)
        }
    }

    fun i(tag: String=TAG, msg: String) {
        if (level <= INFO) {
            Log.i(tag, msg)
        }
    }

    fun w(tag: String=TAG, msg: String) {
        if (level <= WARN) {
            Log.w(tag, msg)
        }
    }

    fun e(tag: String=TAG, msg: String) {
        if (level <= ERROR) {
            Log.e(tag, msg)
        }
    }
}