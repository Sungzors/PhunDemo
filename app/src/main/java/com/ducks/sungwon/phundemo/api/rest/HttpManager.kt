package com.ducks.sungwon.phundemo.api.rest

import com.ducks.sungwon.phundemo.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HttpManager {
    companion object {
        var instance: HttpManager = HttpManager()
            private set
    }

    /*Properties*/
    var mRetrofit: Retrofit? = null
    var mHttpClient: OkHttpClient? = null

    init {
        /*Http Client*/
        mHttpClient = OkHttpClient
                .Builder()
                .addInterceptor(HeaderInterceptor.instance)
                .addInterceptor(HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()

        val gsonBuilder: GsonBuilder = GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'hh:mm:ssZ")

        val mGson = gsonBuilder.create()

        /*Retrofit Rx*/
        mHttpClient?.let {

            mRetrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(mGson))
                    .baseUrl(BuildConfig.BASE_URL)
                    .client(it)
                    .build()
        }
    }

}