package com.xm.kotlin.gank.api

import com.xm.kotlin.gank.MyApp
import com.xm.kotlin.gank.common.Constants
import com.xm.kotlin.gank.utils.NetworkUtil
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit


/**
 * Created by wangtao on 2017/11/15.
 */
object GankApi {

    private var girlsApi: ContentApi? = null

    private var retrofit: Retrofit? = null

    private var netInterceptor: Interceptor = Interceptor { chain ->

        var request = chain.request()

        if (!NetworkUtil.isNetworkUseful(MyApp.shared)) {

            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build()
        }

        val originalResponse = chain.proceed(request)

        if (NetworkUtil.isNetworkUseful(MyApp.shared)) {

            return@Interceptor originalResponse.newBuilder()
                    .header("Cache-Control", "public, max-age=" + 0)
                    .removeHeader("Pragma")
                    .build()
        } else {

            val maxTime = 4 * 24 * 60 * 60

            return@Interceptor originalResponse.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxTime)
                    .removeHeader("Pragma")
                    .build()
        }
    }

    private val file: File = File(MyApp.shared.cacheDir, "cacheData")

    private val cache: Cache = Cache(file, 10 * 1024 * 1024)


    private val okhttpClient: OkHttpClient = OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .connectTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(netInterceptor)
            .cache(cache)
            .build()


    private val converterFactory: GsonConverterFactory = GsonConverterFactory.create()

    private val rxJava2CallAdapterFactory: RxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create()

    fun createContentApi(): ContentApi? {


        if (girlsApi == null) {

            retrofit = Retrofit.Builder()
                    .client(okhttpClient)
                    .baseUrl(Constants.baseUrl)
                    .addConverterFactory(converterFactory)
                    .addCallAdapterFactory(rxJava2CallAdapterFactory)
                    .build()
            girlsApi = retrofit?.create(ContentApi::class.java)
        }

        return girlsApi

    }


}