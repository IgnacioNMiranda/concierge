package com.example.myapplication.api

import android.util.Log
import com.example.myapplication.model.Registro
import com.example.myapplication.modelResponse.RegistroResponse
import retrofit2.Response

class ApiConnection {

    companion object {

        private val request = ApiAdapter.buildService(ConciergeApi::class.java)

        fun fetchRegistros(): List<Registro>? {
            var response: Response<RegistroResponse>? = null
            val call = request.fetchRegistros()

            val thread = Thread(Runnable {
                response = call.execute()
            })

            thread.join()

            Log.d("tag", response.toString())

            return response?.body()?.registros?.toList()
        }
    }

}