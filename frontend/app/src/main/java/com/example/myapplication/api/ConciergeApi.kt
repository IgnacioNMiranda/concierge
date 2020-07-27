package com.example.myapplication.api

import com.example.myapplication.model.Registro
import com.example.myapplication.modelResponse.RegistroResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

/**
 * RESTful API for Concierge backend.
 *
 * This interface contains the exposed API in the Concierge system
 * and is used by the Retrofit client.
 */
interface ConciergeApi {

    /**
     * Get a list of all [Registro]s.
     *
     * @return a [Response] with a Collection of [Registro].
     */
    @GET("registro")
    fun fetchRegistros(): Call<RegistroResponse>

    /**
     * Create a [Registro].
     *
     * @return a [Response] with a [Registro].
     */
    @POST("registro")
    fun createRegistro(@Body registro: Registro): Call<RegistroResponse>

    /**
     * Get a [Registro] with its associated ID of [Departamento].
     *
     * @param id The ID of the [Departamento].
     * @return a [Response] with a [Registro].
     */
    @GET("registro/{departamento_id}")
    fun readRegistro(@Path("departamento_id") id: Long): Call<RegistroResponse>

    /**
     * Update a [Registro].
     *
     * @param id The ID of the [Registro].
     * @param registro The [Registro] to update.
     * @return a [Response] with a [Registro].
     */
    @PUT("registro/{registro}")
    fun updateRegistro(
        @Path("registro") id: Long,
        @Body registro: Registro
    ): Call<RegistroResponse>

    /**
     * Delete a [Registro].
     *
     * @param id The ID of the [Registro].
     * @return a [Response] with a message.
     */
    @DELETE("registro/{registro}")
    fun deleteRegistro(@Path("registro") id: Long): Call<RegistroResponse>

}