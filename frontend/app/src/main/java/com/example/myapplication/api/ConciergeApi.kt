package com.example.myapplication.api

import com.example.myapplication.model.Departamento
import com.example.myapplication.model.Persona
import com.example.myapplication.model.Registro
import com.example.myapplication.modelResponse.DepartamentoResponse
import com.example.myapplication.modelResponse.PersonaResponse
import com.example.myapplication.modelResponse.RegistroResponse
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
     * @return a [Call] that can be used to retrieve a [RegistroResponse].
     */
    @GET("registro")
    fun fetchRegistros(): Call<RegistroResponse>

    /**
     * Create a [Registro].
     *
     * @param registro The [Registro] to insert.
     * @return a [Call] that can be used to retrieve a [RegistroResponse].
     */
    @POST("registro")
    fun createRegistro(@Body registro: Registro): Call<RegistroResponse>

    /**
     * Get a [Registro] with its associated ID of [Departamento].
     *
     * @param id The ID of the [Departamento].
     * @return a [Call] that can be used to retrieve a [RegistroResponse].
     */
    @GET("registro/{departamento_id}")
    fun readRegistro(@Path("departamento_id") id: Long? = null): Call<RegistroResponse>

    /**
     * Update a [Registro].
     *
     * @param id The ID of the [Registro].
     * @param registro The [Registro] to update.
     * @return a [Call] that can be used to retrieve a [RegistroResponse].
     */
    @PUT("registro/{registro}")
    fun updateRegistro(
        @Path("registro") id: Long? = null,
        @Body registro: Registro
    ): Call<RegistroResponse>

    /**
     * Delete a [Registro].
     *
     * @param id The ID of the [Registro].
     * @return a [Call] that can be used to retrieve a [RegistroResponse].
     */
    @DELETE("registro/{registro}")
    fun deleteRegistro(@Path("registro") id: Long? = null): Call<RegistroResponse>

    /**
     * Get a list of all [Departamento]s.
     *
     * @return a [Call] that can be used to retrieve a [DepartamentoResponse].
     */
    @GET("departamento")
    fun fetchDepartamentos(): Call<DepartamentoResponse>

    /**
     * Create a [Departamento].
     *
     * @param departamento The [Departamento] to insert.
     * @return a [Call] that can be used to retrieve a [DepartamentoResponse].
     */
    @POST("departamento")
    fun createDepartamento(@Body departamento: Departamento): Call<DepartamentoResponse>

    /**
     * Retrieve a [Departamento] from the backend.
     *
     * @param id The ID of the [Departamento] to get.
     * @return a [Call] that can be used to retrieve a [DepartamentoResponse].
     */
    @GET("departamento/{departamento}")
    fun readDepartamento(@Path("departamento") id: Long? = null): Call<DepartamentoResponse>

    /**
     * Update a [Departamento] in the backend.
     *
     * @param id The [Departamento]'s ID.
     * @param departamento The data to update.
     * @return a [Call] that can be used to retrieve a [DepartamentoResponse].
     */
    @PUT("departamento/{departamento}")
    fun updateDepartamento(
        @Path("departamento") id: Long? = null,
        @Body departamento: Departamento
    ): Call<DepartamentoResponse>

    /**
     * Remove a [Departamento] from the backend.
     *
     * @param id The ID of the [Departamento] to remove.
     * @return a [Call] that can be used to retrieve a [DepartamentoResponse].
     */
    @DELETE("departamento/{departamento}")
    fun deleteDepartamento(@Path("departamento") id: Long? = null): Call<DepartamentoResponse>

    /**
     * Get a list of all [Persona]s.
     *
     * @return a [Call] that can be used to retrieve a [PersonaResponse].
     */
    @GET("persona")
    fun fetchPersonas(): Call<PersonaResponse>

    /**
     * Create a [Persona].
     *
     * @param persona The [Persona] to add.
     * @return a [Call] that can be used to retrieve a [PersonaResponse].
     */
    @POST("persona")
    fun createPersona(@Body persona: Persona): Call<PersonaResponse>

    /**
     * Get a [Persona] from the backend.
     *
     * @param id The ID of the [Persona] to retrieve.
     * @return a [Call] that can be used to retrieve a [PersonaResponse].
     */
    @GET("persona/{persona}")
    fun readPersona(@Path("persona") id: Long? = null): Call<PersonaResponse>

    /**
     * Update a [Persona] in the backend.
     *
     * @param id The ID of the [Persona] to update.
     * @param persona The data to update.
     * @return a [Call] that can be used to retrieve a [PersonaResponse].
     */
    @PUT("persona/{persona}")
    fun updatePersona(
        @Path("persona") id: Long? = null,
        @Body persona: Persona
    ): Call<PersonaResponse>

    /**
     * Delete a [Persona] from the backend.
     *
     * @param id The ID of the [Persona] to remove.
     * @return a [Call] that can be used to retrieve a [PersonaResponse].
     */
    @DELETE("persona/{persona}")
    fun deletePersona(@Path("persona") id: Long? = null): Call<PersonaResponse>
}