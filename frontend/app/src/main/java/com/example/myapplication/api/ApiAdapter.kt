package com.example.myapplication.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Retrofit singleton instance that allows the connection with the api rest.
 */
object ApiAdapter {

    /**
     * OkHttpClient that allows the connection with api.
     */
    private val client = OkHttpClient.Builder().build()

    /**
     * Retrofit instance that connects with the api.
     */
    private val retrofit = Retrofit.Builder()
        //.baseUrl("http://10.0.2.2:8000/api/")
        .baseUrl("http://192.168.0.113:8000/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    /**
     * Creates the retrofit instance using a [service] interface with its api connection methods.
     */
    fun <T> buildService(service: Class<T>): T {
        return retrofit.create(service)
    }
}