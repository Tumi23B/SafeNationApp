// This file creates a reusable Retrofit client for network calls.
package com.example.agricultureagritech.core.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*
* This object provides a single, configured instance of the ApiService.
* It uses a logging interceptor to help you debug network calls by showing them in the Logcat.
*/
object RetrofitClient {
    private const val BASE_URL = "https://agri-safety.onrender.com" // my REST API URL deployed with RENDER.

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}