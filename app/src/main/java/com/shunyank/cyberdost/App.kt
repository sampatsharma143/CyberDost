package com.shunyank.cyberdost

import android.app.Application
import android.content.Context
import java.util.Objects

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        context = this
    }

    companion object{
         lateinit var context:Context
    }

}