package com.zrq.sese.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @Description:
 * @author zhangruiqian
 * @date 2023/8/4 18:13
 */
object RetrofitProvider {

    private const val BASE_URL = "https://www.xvideos.com"

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttp)
            .build()
    }

    private val okHttp: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addNetworkInterceptor { chain ->
                val url = chain.request().url()
                val headers = chain.request().headers()
                Log.d(TAG, "url: $url")
                Log.d(TAG, "headers: $headers")
                chain.proceed(chain.request())
            }
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .build()
    }

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    const val TAG  = "RetrofitProvider"
}