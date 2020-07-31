package com.example.myapplication.api


import android.app.Activity
import android.app.Application
import android.content.Context
import android.widget.Toast
import com.example.myapplication.MainActivity
import com.example.myapplication.model.Registro
import com.example.myapplication.modelResponse.RegistroResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.lang.Exception

class ApiConnection {

    companion object {

        private val request = ApiAdapter.buildService(ConciergeApi::class.java)

        fun fetchRegistros(): Response<RegistroResponse>? = runBlocking {
            var response: Response<RegistroResponse>?
            val call = request.fetchRegistros()

            try {
                response = withContext(Dispatchers.Default) {
                    call.execute()
                }
                return@runBlocking response
            } catch (e: Exception) {
                return@runBlocking null
            }
        }
    }
}