package com.example.myapplication.model

/**
 * Persona that visits or lives in a Departamento.
 *
 * It's named [nombre] with a non-modifiable [rut], a [telefono] phone number,
 * an [email] and a [departamento] if it lives in the building.
 */
data class Persona(
    val rut: String? = null,
    var nombre: String? = null,
    var telefono: String? = null,
    var email: String? = null,
    var departamento: Departamento? = null
)