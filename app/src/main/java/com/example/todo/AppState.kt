package com.example.todo

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log

@SuppressLint("StaticFieldLeak")
object AppState {
    private var _activityContext: Context? = null
    // private val leakedActivities = mutableListOf<Context>()

    fun initialize(context: Context) {
        _activityContext = context.applicationContext
        // leakedActivities.add(context) // accumulates every leaked activity
        Log.w("AppState", "Safe context stored-application context never leaks")
    }
}

