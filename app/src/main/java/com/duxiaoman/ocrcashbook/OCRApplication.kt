package com.duxiaoman.ocrcashbook

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class OCRApplication:Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        var TOKEN = ""
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}