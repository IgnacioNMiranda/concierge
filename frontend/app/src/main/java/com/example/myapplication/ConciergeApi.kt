package com.example.myapplication

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Call
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
     * @return a [Call] that can return a [Response] with a Collection of [Registro].
     */
    @GET("/api/registro")
    suspend fun registros(): Call<Collection<Registro>>

    /**
     * Create a [Registro].
     *
     * @param id The ID of the [Registro]
     * @return a [Call] that can return a [Response] with a [Registro].
     */
    @POST("/api/registro")
    suspend fun createRegistro(@Body registro: Registro): Call<Registro>

    /**
     * Get a [Registro] with its associated ID of [Departamento].
     *
     * @param id The ID of the [Departamento].
     * @return a [Call] that can return a [Response] with a [Registro].
     */
    @GET("/api/registro/{departamento_id}")
    suspend fun readRegistro(@Path("departamento_id") id: Long): Call<Registro>

    /**
     * Update a [Registro].
     *
     * @param id The ID of the [Registro].
     * @param registro The [Registro] to update.
     * @return a [Call] that can return a [Response] with a [Registro].
     */
    @PUT("/api/registro/{registro}")
    suspend fun updateRegistro(
        @Path("registro") id: Long,
        @Body registro: Registro
    ): Call<Registro>

    /**
     * Delete a [Registro].
     *
     * @param id The ID of the [Registro].
     * @return a [Call] that can return a [Response] with a message.
     */
    @DELETE("/api/registro/{registro}")
    suspend fun deleteRegistro(@Path("registro") id: Long): Call<ResponseBody>

}