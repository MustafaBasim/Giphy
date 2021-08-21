package com.mustafa.giphy.model.networking

import android.util.Log
import com.mustafa.giphy.utilities.Urls
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Created by: Mustafa Basim
 * E-mail: 96.mustafa.basim@gmail.com
 * Project name: FreshWorks Giphy
 * Package: com.mustafa.giphy.model.networking
 * Date: 8/21/2021
 */

object NetworkClient {

//    @SuppressLint("ConstantLocale")
//    private val language = Hawk.get(Prefs.LANGUAGE, Locale.getDefault().language)

    private val okHttpClientBuilder = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request()
            val newRequest =
                request.newBuilder()
//                    .header("Accept-Language", language)
//                    .header("Accept", "application/json")
//                    .header("Content-Type", "application/json")

//            if (UserUtil.isLoggedIn()) {
////                Log.d(TAG, " token = ${Hawk.get(Prefs.USER_AUTH_TOKEN, "")} ")
//                newRequest.header("Authorization", "Bearer ${Hawk.get(Prefs.USER_AUTH_TOKEN, "")}")
//            }
            Log.d("ERROR", " URL = ${request.url()} ")
            chain.proceed(newRequest.build())
        }

    fun initializeAPI(): APIInterface = Retrofit.Builder()
        .baseUrl(Urls.BASE_URL)
        .client(okHttpClientBuilder.build())
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(APIInterface::class.java)


}