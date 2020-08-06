package com.example.myapplication.api


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.compose.MutableState
import com.example.myapplication.MainActivity
import com.example.myapplication.model.Registro
import com.example.myapplication.model.User
import com.example.myapplication.modelResponse.AuthResponse
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
                    } else {
                        obtainingData.value = false
                        registrosResponse.value = false
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
            /*
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
            */
        }

        fun deleteRegistro(id: Long?) {
            val call = request.deleteRegistro(id)

            call.enqueue(object : Callback<RegistroResponse> {
                override fun onResponse(
                    call: Call<RegistroResponse>,
                    response: Response<RegistroResponse>
                ) {
                    // It checks if status ~ 200
                    if (response.isSuccessful) {
                        //TODO: set bool
                    }
                }

                override fun onFailure(call: Call<RegistroResponse>, e: Throwable) {
                    throw Exception(e)
                }
            })
        }

        fun login(
            context: Context,
            loginResponse: MutableState<Boolean>,
            sendingData: MutableState<Boolean>,
            email: String,
            password: String
        ) {
            val user = User(null, email, password)
            val call = request.login(user)

            /** Async call */
            call.enqueue(object : Callback<AuthResponse> {
                override fun onResponse(
                    call: Call<AuthResponse>,
                    response: Response<AuthResponse>
                ) {
                    // It checks if status ~ 200
                    if (response.isSuccessful) {
                        sendingData.value = false
                        loginResponse.value = true

                        /* Stores the authToken on SharedPreferences */
                        val prefs: SharedPreferences =
                            context.getSharedPreferences("CONCIERGE_APP", Context.MODE_PRIVATE)
                        prefs.edit().apply {
                            putString("AUTH_TOKEN", response.body()?.token)
                        }.apply()

                        val intent = Intent(context, MainActivity::class.java)
                        context.startActivity(intent)
                    }
                }

                override fun onFailure(call: Call<AuthResponse>, e: Throwable) {
                    sendingData.value = false
                    loginResponse.value = false
                }
            })
        }

        fun updateRegistro(registro: Registro) {
            val call = request.updateRegistro(registro.id, registro)

            call.enqueue(object : Callback<RegistroResponse> {
                override fun onResponse(
                    call: Call<RegistroResponse>,
                    response: Response<RegistroResponse>
                ) {
                    // It checks if status ~ 200
                    if (response.isSuccessful) {
                        // TODO: set bool
                    } else if (response.code() == 404) {
                        throw Exception("404 Not found")
                    }
                }

                override fun onFailure(call: Call<RegistroResponse>, e: Throwable) {
                    throw Exception(e)
                }
            })
        }

    }
}
