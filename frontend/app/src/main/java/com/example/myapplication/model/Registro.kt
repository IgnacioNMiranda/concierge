package com.example.myapplication.model

import androidx.annotation.Keep
import java.util.*

/**
 * Registro that saves information about a visit to the building.
 */
@Keep
data class Registro(
    val id: Long? = null,
    var fecha: Date? = null,
    var parentesco: String? = null,
    var empresaEntrega: Boolean? = null,
    var persona_id: Long? = null,
    var departamento_id: Long? = null,
    var persona: Persona? = null,
    var departamento: Departamento? = null
)