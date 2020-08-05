package com.example.myapplication.modelResponse

import com.example.myapplication.model.Persona

/**
 * Response handler for the Persona model.
 *
 * Each response contains a server [message], a [persona]
 * which may contain a single object, or a [personas]
 * which may hold a Collection when a fetch all elements operation
 * is called.
 */
data class PersonaResponse(

    /**
     * message of the http response.
     */
    var message: String? = null,

    /**
     * Persona object received from the http response.
     */
    var persona: Persona? = null,

    /**
     * Persona collection received from the http response.
     */
    var personas: Collection<Persona>? = null
)