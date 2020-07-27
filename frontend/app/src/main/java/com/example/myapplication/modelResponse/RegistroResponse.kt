package com.example.myapplication.modelResponse

import com.example.myapplication.model.Registro

class RegistroResponse {

    /**
     * message of the http response.
     */
    var message: String? = null

    /**
     * Registro object received from the http response.
     */
    var registro: Registro? = null

    /**
     * Registro collection received from the http response.
     */
    var registros: Collection<Registro>? = null
}