package com.xm.kotlin.gank

import android.app.Application
import butterknife.ButterKnife

/**
 * Created by wangtao on 2017/11/15.
 */
class MyApp : Application() {

    companion object {
        lateinit var shared: MyApp
    }

    init {
        shared = this
    }

    override fun onCreate() {
        super.onCreate()
        ButterKnife.setDebug(true)
    }
}