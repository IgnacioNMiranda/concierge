package com.example.myapplication.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class ApiConnection {

    companion object {

        private val request = ApiAdapter.buildService(ConciergeApi::class.java)

        fun fetchRegistros() = runBlocking {
            val call = request.fetchRegistros()

            val response = withContext(Dispatchers.Default) {
                call.execute()
            }

            return@runBlocking response.body()?.registros?.toMutableList()
        }

    }

}