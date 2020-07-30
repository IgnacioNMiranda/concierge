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
    var message: String? = null,
    var persona: Persona? = null,
    var personas: Collection<Persona>? = null
)