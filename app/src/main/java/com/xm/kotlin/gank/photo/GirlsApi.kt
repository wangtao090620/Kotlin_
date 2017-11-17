package com.xm.kotlin.gank.photo

import com.xm.kotlin.gank.data.android.AndroidData
import com.xm.kotlin.gank.data.android.AndroidResult
import com.xm.kotlin.gank.data.girl.GirlData
import com.xm.kotlin.gank.data.girl.GirlResults
import com.xm.kotlin.gank.data.ios.IOSData
import com.xm.kotlin.gank.data.ios.IOSResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by wangtao on 2017/11/15.
 */
interface GirlsApi {

    @GET("福利/10/{page}")
    fun getGirls(@Path("page") page: Int): Observable<GirlResults<List<GirlData>>>


    @GET("Android/10/{page}")
    fun getAndroids(@Path("page") page: Int): Observable<AndroidResult<List<AndroidData>>>


    @GET("iOS/10/{page}")
    fun getIOS(@Path("page") page: Int): Observable<IOSResult<List<IOSData>>>
}