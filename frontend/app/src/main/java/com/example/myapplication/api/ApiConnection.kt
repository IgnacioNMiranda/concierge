package com.example.myapplication.api


import android.util.Log
import com.example.myapplication.modelResponse.RegistroResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiConnection {

    @Suppress("SpellCheckingInspection")
    companion object {

        private val request = ApiAdapter.buildService(ConciergeApi::class.java)

        fun fetchRegistros(callback: RegistroCallback) {
            var respuestaRegistros: Response<RegistroResponse>? = null
            val call = request.fetchRegistros()

            call.enqueue(object : Callback<RegistroResponse> {
                override fun onResponse(
                    call: Call<RegistroResponse>,
                    response: Response<RegistroResponse>
                ) {
                    // It checks if status ~ 200
                    if (response.isSuccessful) {
                        callback.fetchRegistros(response)
                    }
                }

                override fun onFailure(call: Call<RegistroResponse>, e: Throwable) {
                    callback.fetchRegistros(null)
                }
            })

            Log.e("responseBeforeReturn", respuestaRegistros.toString())

        }
    }
}