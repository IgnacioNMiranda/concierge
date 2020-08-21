package com.example.myapplication.model

/**
 * Persona that visits or lives in a Departamento.
 *
 * It's named [nombre] with a non-modifiable [rut], a [telefono] phone number,
 * an [email] and a [departamento] if it lives in the building, the [id] refers
 * to the backend ID.
 */
data class Persona(

    /**
     * Backend's id.
     */
    val id: Long? = null,

    /**
     * The rut of the [Persona].
     */
    val rut: String? = null,

    /**
     * Name of the [Persona].
     */
    var nombre: String? = null,

    /**
     * Phone of the [Persona].
     */
    var telefono: String? = null,

    /**
     * Email belonged to the [Persona].
     */
    var email: String? = null,

    /**
     * Backend's own apartment id.
     */
    var departamento_id: Long? = null,

    /**
     * Backend's own apartment reference.
     */
    var departamento: Departamento? = null,

    /**
     * Obtained in PostPersona activity and used to obtain the [departamento_id].
     */
    var numeroDepartamento: Int? = null
)
