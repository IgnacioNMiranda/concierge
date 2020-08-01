package com.example.myapplication.api

import com.example.myapplication.modelResponse.RegistroResponse
import retrofit2.Response

interface RegistroCallback {

    fun fetchRegistros(response: Response<RegistroResponse>?)
}