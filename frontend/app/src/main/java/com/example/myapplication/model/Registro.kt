package com.example.myapplication.model

import androidx.annotation.Keep
import java.util.*

/**
 * Registro represents a visit to an apartment.
 *
 * The visit is made on a [fecha], visitor [persona] has a [parentesco] with the apartment's owner
 * and it can be a delivery business ([empresaEntrega]). The registro's [id], [persona_id] and
 * [departamento_id] refers to the backend ID's.
 */
@Keep
data class Registro(

    /**
     * Backend's id.
     */
    val id: Long? = null,

    /**
     * Date of the [Registro].
     */
    var fecha: Date? = null,

    /**
     * Relationship between the visitor and the apartment's owner.
     */
    var parentesco: String? = null,

    /**
     * Represents if the visitor is a delivery business.
     */
    var empresaEntrega: Boolean? = null,

    /**
     * References the backend's visitor id.
     */
    var persona_id: Long? = null,

    /**
     * References the backend's apartment id.
     */
    var departamento_id: Long? = null,

    /**
     * Backend's visitor reference.
     */
    var persona: Persona? = null,

    /**
     * Backend's apartment reference.
     */
    var departamento: Departamento? = null
)
