package com.example.myapplication.api


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.MutableState
import androidx.core.text.isDigitsOnly
import com.example.myapplication.Login
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.Utility
import com.example.myapplication.model.Persona
import com.example.myapplication.model.Registro
import com.example.myapplication.model.User
import com.example.myapplication.modelResponse.AuthResponse
import com.example.myapplication.modelResponse.PersonaResponse
import com.example.myapplication.modelResponse.RegistroResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


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
         * Registers a user in the system, generates the auth token and storages it.
         */
        fun register(
            context: Context,
            registerResponse: MutableState<Boolean>,
            sendingData: MutableState<Boolean>,
            name: String,
            email: String,
            password: String,
            password_confirmation: String,
            popUpStringContent: MutableState<String>
        ) {
            val user = User(name, email, password, password_confirmation)
            val call = request.register(user)

            /** Async call */
            call.enqueue(object : Callback<AuthResponse> {
                override fun onResponse(
                    call: Call<AuthResponse>,
                    response: Response<AuthResponse>
                ) {
                    // It checks if status ~ 200
                    if (response.isSuccessful) {
                        sendingData.value = false
                        registerResponse.value = true

                        /* Stores the authToken on SharedPreferences */
                        val prefs: SharedPreferences =
                            context.getSharedPreferences("CONCIERGE_APP", Context.MODE_PRIVATE)
                        prefs.edit().apply {
                            putString("AUTH_TOKEN", response.body()?.token)
                        }.apply()

                        /* Calls the main activity after register. */
                        val intent = Intent(context, MainActivity::class.java)
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        context.startActivity(intent)

                    } else {
                        val json: JSONObject =
                            Utility.ValidationErrorsToJsonObject(response.errorBody()?.string()!!)

                        popUpStringContent.value = Utility.AuthErrors(json)

                        sendingData.value = false
                        registerResponse.value = false
                    }
                }

                override fun onFailure(call: Call<AuthResponse>, e: Throwable) {
                    popUpStringContent.value =
                        context.resources.getString(R.string.server_connection_failed)
                    sendingData.value = false
                    registerResponse.value = false
                }
            })
        }

        /**
         * Makes the login of a user, generates the auth token and storages it.
         */
        fun login(
            context: Context,
            loginResponse: MutableState<Boolean>,
            sendingData: MutableState<Boolean>,
            email: String,
            password: String,
            popUpStringContent: MutableState<String>
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

                        /* Calls the main activity after login. */
                        val intent = Intent(context, MainActivity::class.java)
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        context.startActivity(intent)
                    } else {
                        val json: JSONObject =
                            Utility.ValidationErrorsToJsonObject(response.errorBody()?.string()!!)

                        popUpStringContent.value = Utility.AuthErrors(json)

                        sendingData.value = false
                        loginResponse.value = false
                    }
                }

                override fun onFailure(call: Call<AuthResponse>, e: Throwable) {
                    popUpStringContent.value =
                        context.resources.getString(R.string.server_connection_failed)
                    sendingData.value = false
                    loginResponse.value = false
                }
            })
        }

        fun logout(
            context: Context,
            logoutResponse: MutableState<Boolean>,
            logoutState: MutableState<Boolean>
        ) {
            /* Obtains the authToken. */
            val authToken = context.getSharedPreferences(
                "CONCIERGE_APP",
                Context.MODE_PRIVATE
            ).getString("AUTH_TOKEN", null)!!

            val call = request.logout()

            /** Async call of logout*/
            call.enqueue(object : Callback<AuthResponse> {
                override fun onResponse(
                    call: Call<AuthResponse>,
                    response: Response<AuthResponse>
                ) {
                    // It checks if status ~ 200
                    if (response.isSuccessful) {
                        logoutState.value = false
                        logoutResponse.value = true

                        /* Deletes the authToken from SharedPreferences */
                        val prefs: SharedPreferences =
                            context.getSharedPreferences("CONCIERGE_APP", Context.MODE_PRIVATE)
                        prefs.edit().apply {
                            putString("AUTH_TOKEN", null)
                        }.apply()

                        /* Returns to login activity. */
                        val intent = Intent(context, Login::class.java)
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        context.startActivity(intent)
                    } else {
                        logoutState.value = false
                        logoutResponse.value = true
                    }
                }

                override fun onFailure(call: Call<AuthResponse>, e: Throwable) {
                    logoutState.value = false
                    logoutResponse.value = false
                }
            })
        }

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
            context: Context,
            registroResponse: MutableState<Boolean>,
            obtainingData: MutableState<Boolean>,
            parentesco: String,
            empresaEntrega: Boolean,
            rutPersona: String,
            numDept: String,
            popUpStringContent: MutableState<String>
        ) {

            val reg = Registro(
                null,
                Calendar.getInstance().time,
                parentesco,
                empresaEntrega,
                null,
                null,
                null,
                null
            )
            var numDepartamento: Int = 0
            try {
                numDepartamento = numDept.toInt()
            }  catch (e: NumberFormatException) {
                popUpStringContent.value = context.resources.getString(R.string.invalid_numDept)
                obtainingData.value = false
                registroResponse.value = false
                return
            }
            val call = request.createRegistro(reg, rutPersona, numDepartamento)

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

                        val intent = Intent(context, MainActivity::class.java)
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        context.startActivity(intent)
                    } else {
                        val json: JSONObject =
                            Utility.ValidationErrorsToJsonObject(response.errorBody()?.string()!!)

                        popUpStringContent.value = Utility.RegistroErrors(json)

                        obtainingData.value = false
                        registroResponse.value = false
                    }
                }

                override fun onFailure(call: Call<RegistroResponse>, e: Throwable) {
                    obtainingData.value = false
                    registroResponse.value = false
                }
            })
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

        /**
         * Retrieves all the database 'personas' asynchronously.
         */
        fun fetchPersonas(
            personas: MutableState<List<Persona>>,
            personasResponse: MutableState<Boolean>,
            obtainingData: MutableState<Boolean>
        ) {
            val call = request.fetchPersonas()

            /** Async call */
            call.enqueue(object : Callback<PersonaResponse> {
                override fun onResponse(
                    call: Call<PersonaResponse>,
                    response: Response<PersonaResponse>
                ) {
                    // It checks if status ~ 200
                    if (response.isSuccessful) {
                        obtainingData.value = false
                        personasResponse.value = true
                        personas.value = response.body()?.personas?.toList()!!
                    } else {
                        obtainingData.value = false
                        personasResponse.value = false
                    }
                }

                override fun onFailure(call: Call<PersonaResponse>, e: Throwable) {
                    obtainingData.value = false
                    personasResponse.value = false
                }
            })
        }

        fun findPersonaByRut(rut: String, persona: MutableState<Persona?>) {
            val call = request.findPersonaByRut(rut)

            call.enqueue(object : Callback<PersonaResponse> {
                override fun onResponse(
                    call: Call<PersonaResponse>,
                    response: Response<PersonaResponse>
                ) {
                    if (response.isSuccessful) {
                        persona.value = response.body()?.persona
                    }
                }

                override fun onFailure(call: Call<PersonaResponse>, e: Throwable) {
                    throw Exception(e)
                }
            })
        }

        fun createPersona(
            context: Context,
            personaResponse: MutableState<Boolean>,
            obtainingData: MutableState<Boolean>,
            rut: String,
            nombre: String,
            fono: String,
            email: String,
            numeroDepto: String,
            popUpStringContent: MutableState<String>
        ) {
            var numeroDeptoAux = numeroDepto.toIntOrNull()
            if (numeroDeptoAux == null) {
                numeroDeptoAux = 0
            }
            val persona = Persona(null, rut, nombre, fono, email, null, null, numeroDeptoAux)
            val call = request.createPersona(persona)

            call.enqueue(object : Callback<PersonaResponse> {
                override fun onResponse(
                    call: Call<PersonaResponse>,
                    response: Response<PersonaResponse>
                ) {
                    if (response.isSuccessful) {
                        obtainingData.value = false
                        personaResponse.value = true

                        /* Returns to the same activity (persona). */
                        val intent = Intent(context, MainActivity::class.java)
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        context.startActivity(intent)

                    } else {
                        val json: JSONObject =
                            Utility.ValidationErrorsToJsonObject(response.errorBody()?.string()!!)

                        popUpStringContent.value = Utility.PersonaErrors(json)

                        obtainingData.value = false
                        personaResponse.value = false
                    }
                }

                override fun onFailure(call: Call<PersonaResponse>, e: Throwable) {
                    //popUpStringContent.value =
                        //context.resources.getString(R.string.server_connection_failed)
                    obtainingData.value = false
                    personaResponse.value = false
                }
            })
        }

    }
}
