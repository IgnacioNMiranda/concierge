package com.example.myapplication.model

import androidx.annotation.Keep

/**
 * Registro class that represents the Registro model used in Api rest.
 */
@Keep
class Registro {

    /**
     * message of the http response.
     */
    var message: String? = null

    /**
     * Registro object send from the http response.
     */
    var registro: String? = null

    /**
     * Registro collection send from the htpp response.
     */
    var registros: String? = null


}