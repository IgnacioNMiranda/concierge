package com.example.myapplication.model

/**
 * Departamento that has an [id] and a [numero] associated.
 */
data class Departamento(

    /**
     * Backend's id.
     */
    val id: Long? = null,

    /**
     * Apartment's number.
     */
    var numero: Int? = null
)
