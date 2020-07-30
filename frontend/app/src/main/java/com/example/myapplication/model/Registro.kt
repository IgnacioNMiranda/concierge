package com.example.myapplication.model

import androidx.annotation.Keep
import java.util.*

/**
 * Registro class that represents the Registro model used in the RESTful API.
 */
@Keep
class Registro(
    fecha: Date,
    parentesco: String,
    empresaEntrega: Boolean,
    persona: Persona? = null,
    departamento: Departamento? = null,
    persona_id: Long,
    departamento_id: Long
) {

    /**
     * The date.
     */
    var fecha: Date? = null

    /**
     * The relationship between the visit and the Apartment owner.
     */
    var parentesco: String? = null

    /**
     * Indicates if it is a delivery business.
     */
    var empresaEntrega: Boolean? = null

    /**
     * Relation with the visitor that does the visit.
     */
    var persona: Persona? = null

    /**
     * Relation with the visitor ID that does the visit.
     */
    var persona_id: Long? = null

    /**
     * Relation with the visited apartment.
     */
    var departamento: Departamento? = null

    /**
     * Relation with the visited apartment ID.
     */
    var departamento_id: Long? = null

    init {
        this.fecha = fecha
        this.parentesco = parentesco
        this.empresaEntrega = empresaEntrega
        this.persona = persona
        this.persona_id = persona_id
        this.departamento = departamento
        this.departamento_id = departamento_id
    }
}