package com.example.myapplication.api

import android.util.Log
import com.example.myapplication.model.Registro
import com.example.myapplication.modelResponse.RegistroResponse
import org.junit.Assert
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class ConciergeApiTest {

    /**
     * Singleton that allows the connection with restful api
     */
    private val request = ApiAdapter.buildService(ConciergeApi::class.java)

    /**
     * Registro created to use in the test of store, update and delete
     */
    private val registro = Registro(1, Calendar.getInstance().time, "Familiar", false, 1, 1, null, null)
    private var call = request.createRegistro(registro)

    /**
     * Test of List of all [Registro]s from backend
     */
    @Test
    fun fetchRegistrosTest() {
        Log.i("The testing of fetchRegistrosTest ...", "The testing of fetchRegistrosTest ...")
        call = request.fetchRegistros()

        call.enqueue(object : Callback<RegistroResponse>{
            override fun onResponse(call: Call<RegistroResponse>, response: Response<RegistroResponse>) {
                if (response.isSuccessful) {

                    Assert.assertNotNull(response.body()?.registros)

                    Log.e("tag", response.body()?.registros?.toString()!!)

                    Assert.assertEquals("OK", response.message())
                }
            }

            override fun onFailure(call: Call<RegistroResponse>, t: Throwable) {
                Assert.fail("... fetchRegistrosTest failed!")
            }
        })

        Log.i("The testing of fetchRegistrosTest ...", "The testing of fetchRegistrosTest ...")

    }

    

}