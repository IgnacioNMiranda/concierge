package com.example.myapplication.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Retrofit instance that allows the connection with the api rest.
 */
object ApiAdapter {
    val apiClient: ConciergeApi = Retrofit.Builder()
        .baseUrl("https://localhost:7000")
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ConciergeApi::class.java)
}