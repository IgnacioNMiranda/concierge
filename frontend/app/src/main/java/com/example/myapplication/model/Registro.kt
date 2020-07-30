package com.example.myapplication.model

import androidx.annotation.Keep
import java.time.LocalDateTime
import java.util.*

/**
 * Registro class that represents the Registro model used in the RESTful API.
 */
@Keep
class Registro(
    fecha: Date,
    parentesco: String,
    empresaEntrega: Boolean,
    persona: Persona,
    departamento: Departamento) {

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
     * Relation with the visited apartment.
     */
    var departamento: Departamento? = null

    init {
        this.fecha = fecha
        this.parentesco = parentesco
        this.empresaEntrega = empresaEntrega
        this.persona = persona
        this.departamento = departamento
    }

}