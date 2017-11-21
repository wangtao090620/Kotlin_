package com.xm.kotlin.gank.api

import com.xm.kotlin.gank.data.GoodsData
import com.xm.kotlin.gank.data.GoodsResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by wangtao on 2017/11/15.
 */
interface ContentApi {

    @GET("福利/10/{page}")
    fun getGirls(@Path("page") page: Int): Observable<GoodsResult<List<GoodsData>>>


    @GET("Android/10/{page}")
    fun getAndroids(@Path("page") page: Int): Observable<GoodsResult<List<GoodsData>>>


    @GET("iOS/10/{page}")
    fun getIOS(@Path("page") page: Int): Observable<GoodsResult<List<GoodsData>>>
}