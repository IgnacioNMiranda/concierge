package com.example.myapplication.model

import androidx.annotation.Keep
import java.time.LocalDateTime
import java.util.*

/**
 * Registro class that represents the Registro model used in Api rest.
 */
@Keep
class Registro(fecha: Date, parentesco: String, empresaEntrega: Boolean, persona_id: Int, departamento_id: Int) {

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
    var persona_id: Int? = null

    /**
     * Relation with the visited apartment.
     */
    var departamento_id: Int? = null

    init {
        this.fecha = fecha
        this.parentesco = parentesco
        this.empresaEntrega = empresaEntrega
        this.persona_id = persona_id
        this.departamento_id = departamento_id
    }

}