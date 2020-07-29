package com.example.myapplication.api

import androidx.compose.Composable
import androidx.ui.foundation.AdapterList
import androidx.ui.material.ListItem
import com.example.myapplication.modelResponse.RegistroResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiConnection {

    companion object {

        private val request = ApiAdapter.buildService(ConciergeApi::class.java)

        fun fetchRegistros() {
            /*
            val call = request.fetchRegistros()

            call.enqueue(object : Callback<RegistroResponse> {
                @Composable
                override fun onResponse(call: Call<RegistroResponse>, response: Response<RegistroResponse>) {
                    if (response.isSuccessful) {
                        val registros = response.body()?.registros?.toList()

                        if (registros != null) {
                            AdapterList(data = registros) { registro ->
                                ListItem(
                                    text = registro.fecha.toString(),
                                    secondaryText = registro.parentesco
                                )
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<RegistroResponse>, e: Throwable) {

                }
            })*/
        }
    }

}