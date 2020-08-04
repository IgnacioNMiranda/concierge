package com.example.myapplication.api


import android.util.Log
import androidx.compose.MutableState
import androidx.compose.State
import com.example.myapplication.model.Registro
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
        fun fetchRegistros(
            registros: MutableState<List<Registro>>,
            registrosResponse: MutableState<Boolean>,
            obtainingData: MutableState<Boolean>
        ) {
            val call = request.fetchRegistros()

            /** Async call */
            call.enqueue(object : Callback<RegistroResponse> {
                override fun onResponse(
                    call: Call<RegistroResponse>,
                    response: Response<RegistroResponse>
                ) {
                    // It checks if status ~ 200
                    if (response.isSuccessful) {
                        obtainingData.value = false
                        registrosResponse.value = true
                        registros.value = response.body()?.registros?.toList()!!
                    }
                }

                override fun onFailure(call: Call<RegistroResponse>, e: Throwable) {
                    obtainingData.value = false
                    registrosResponse.value = false
                }
            })
        }


        fun createRegistro(
            registroResponse: MutableState<Boolean>,
            obtainingData: MutableState<Boolean>
        ) {
            val call = request.createRegistro()

            /** Async call */
            call.enqueue(object : Callback<RegistroResponse> {
                override fun onResponse(
                    call: Call<RegistroResponse>,
                    response: Response<RegistroResponse>
                ) {
                    // It checks if status ~ 200
                    if (response.isSuccessful) {
                        obtainingData.value = false
                        registroResponse.value = true
                    }
                }

                override fun onFailure(call: Call<RegistroResponse>, e: Throwable) {
                    obtainingData.value = false
                    registroResponse.value = false
                }
            })
        }

    }
}
