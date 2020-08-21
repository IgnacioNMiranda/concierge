package com.example.myapplication.api

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.ui.core.ContextAmbient
import com.example.myapplication.MainActivity
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Retrofit singleton instance that allows the connection with the api rest.
 */
object ApiAdapter : Application() {

    /**
     * Context of the application.
     */
    private lateinit var context: Context

    /**
     * OkHttpClient that allows the connection with api.
     */
    private val client = OkHttpClient.Builder().addInterceptor { chain ->
        val request = chain.request()
        /* It does not require the auth token in register and login.*/
        if (request.url().encodedPath().equals("/api/register", true) || request.url().encodedPath()
                .equals("/api/login", true)
        ) {
            return@addInterceptor chain.proceed(request)
        }

        val authToken = this.context.getSharedPreferences(
            "CONCIERGE_APP",
            Context.MODE_PRIVATE
        ).getString("AUTH_TOKEN", null)!!

        val newRequest = request.newBuilder()
            .addHeader("Authorization", "Bearer $authToken")
            .build()
        return@addInterceptor chain.proceed(newRequest)
    }.build()

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
     * Obtains the application context for auth_token validation.
     */
    fun setContext(context: Context) {
        this.context = context
    }

    /**
     * Creates the retrofit instance using a [service] interface with its api connection methods.
     */
    fun <T> buildService(service: Class<T>): T {
        return retrofit.create(service)
    }
}