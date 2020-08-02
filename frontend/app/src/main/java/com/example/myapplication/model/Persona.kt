package com.example.myapplication.model

/**
 * Persona that visits or lives in a Departamento.
 *
 * It's named [nombre] with a non-modifiable [rut], a [telefono] phone number,
 * an [email] and a [departamento] if it lives in the building, the [id] refers
 * to the backend ID.
 */
data class Persona(
    val id: Long? = null,
    val rut: String? = null,
    var nombre: String? = null,
    var telefono: String? = null,
    var email: String? = null,
    var departamento_id: Long? = null,
    var departamento: Departamento? = null
)