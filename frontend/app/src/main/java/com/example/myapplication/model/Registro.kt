package com.example.myapplication.model

import androidx.annotation.Keep
import java.util.*

/**
 * Registro class that represents the Registro model used in Api rest.
 */
@Keep
class Registro {

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


}