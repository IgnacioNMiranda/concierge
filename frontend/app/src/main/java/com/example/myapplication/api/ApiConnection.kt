package com.example.myapplication.api


import android.util.Log
import com.example.myapplication.modelResponse.RegistroResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Makes async connection with restful api.
 */
class ApiConnection {

    /**
     * Singleton instances to avoid multiple api connection paths.
     */
    @Suppress("SpellCheckingInspection")
    companion object {

        /**
         * Singleton that allows the connection with restful api.
         */
        private val request = ApiAdapter.buildService(ConciergeApi::class.java)

        /**
         * Retrieves all the database 'registros' asynchronously.
         */
        fun fetchRegistros(callback: RegistroCallback) {
            val call = request.fetchRegistros()

            /** Async call */
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
        }
    }
}
