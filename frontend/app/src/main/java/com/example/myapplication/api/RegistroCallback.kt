package com.example.myapplication.api

import com.example.myapplication.modelResponse.RegistroResponse
import retrofit2.Response

/**
 * Interface that allows callbacks returns when the server's response arrives.
 */
interface RegistroCallback {

    /**
     *  Based on the [response], obtains the data or not.
     */
    fun fetchRegistros(response: Response<RegistroResponse>?)
}