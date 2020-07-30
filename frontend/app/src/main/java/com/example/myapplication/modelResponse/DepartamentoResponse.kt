package com.example.myapplication.modelResponse

import com.example.myapplication.model.Departamento

/**
 * Response handler for the Departamento model.
 *
 * Each response contains a server [message], a [departamento]
 * which may contain a single object, or a [departamentos]
 * which may hold a Collection when a fetch all elements operation
 * is called.
 */
data class DepartamentoResponse(
    var message: String? = null,
    var departamento: Departamento? = null,
    var departamentos: Collection<Departamento>? = null
)