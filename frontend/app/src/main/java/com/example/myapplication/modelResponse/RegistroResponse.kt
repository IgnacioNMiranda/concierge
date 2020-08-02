package com.example.myapplication.modelResponse

import com.example.myapplication.model.Registro

/**
 * Response handler for the Persona model.
 *
 * Each response contains a server [message], a [registro]
 * which may contain a single object, or a [registros]
 * which may hold a Collection when a fetch all elements operation
 * is called.
 */
data class RegistroResponse(

    /**
     * message of the http response.
     */
    var message: String? = null,

    /**
     * Registro object received from the http response.
     */
    var registro: Registro? = null,

    /**
     * Registro collection received from the http response.
     */
    var registros: Collection<Registro>? = null
)