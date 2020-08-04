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

/**
 * RESTful API for Concierge backend.
 *
 * This class implement the methods of the interface
 * the exposed API in the Concierge system
 * and is used by the Retrofit client.
 */
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

    /**
     * Test creating a [Registro] in the backend
     */
    @Test
    fun storeGetRegistroTest() {
        Log.i("The testing of store RegistroTest ...", "The testing of store RegistroTest ...")

        call.enqueue(object : Callback<RegistroResponse> {
            override fun onResponse(call: Call<RegistroResponse>, response: Response<RegistroResponse>) {
                if (response.isSuccessful) {

                    Log.e("Comparation", response.body()?.registro?.toString())
                    Assert.assertEquals("Created", response.message())

                    Assert.assertEquals(registro.fecha, response.body()?.registro?.fecha)
                    Assert.assertEquals(registro.parentesco, response.body()?.registro?.parentesco)
                    Assert.assertEquals(registro.empresaEntrega, response.body()?.registro?.empresaEntrega)
                    Assert.assertEquals(registro.persona_id, response.body()?.registro?.persona_id)
                    Assert.assertEquals(registro.departamento_id, response.body()?.registro?.departamento_id)
                }
            }

            override fun onFailure(call: Call<RegistroResponse>, t: Throwable) {
                Assert.fail("... store RegistroTest failed!")
            }
        })

        Log.i("The testing of store RegistroTest ...", "The testing of store RegistroTest ...")

        Log.i("The testing of get RegistroTest ...", "The testing of get RegistroTest ...")

        call = request.readRegistro(registro.departamento_id)

        call.enqueue(object : Callback<RegistroResponse> {
            override fun onResponse(call: Call<RegistroResponse>, response: Response<RegistroResponse>) {
                if(response.isSuccessful) {

                    Assert.assertEquals("Getted", response.message())

                    Assert.assertEquals(registro.id, response.body()?.registro?.id)
                }
            }

            override fun onFailure(call: Call<RegistroResponse>, t: Throwable) {
                Assert.fail("... get RegistroTest failed!")
            }
        })
        Log.i("The testing of get RegistroTest ...", "The testing of get RegistroTest ...")
    }



}