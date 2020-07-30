package com.example.myapplication.api

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myapplication.model.Registro
import com.example.myapplication.modelResponse.RegistroResponse
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

@RunWith(AndroidJUnit4::class)
class RestfulApiConsumingTest {

    /**
     * Test the restful api consuming.
     */
    @Test
    fun registroApiConnection() {
        Log.i("... restfulApi consuming testing ...", "... restfulApi consuming testing ...")
        val request = ApiAdapter.buildService(ConciergeApi::class.java)

        val registro = Registro(Calendar.getInstance().time, "Empresa", true, null, null, 1, 1)
        var call = request.createRegistro(registro)

        call.enqueue(object : Callback<RegistroResponse> {
            override fun onResponse(call: Call<RegistroResponse>, response: Response<RegistroResponse>) {

                // It checks if status ~ 200
                if (response.isSuccessful) {

                    // Registro response must to be not null
                    Assert.assertNotNull(response.body()?.registro)

                    // Response message has to be Created
                    Assert.assertEquals("Created", response.message())

                    // Attributes from the response's registro has to be equal to the created registro's ones.
                    Assert.assertEquals(registro.fecha, response.body()?.registro?.fecha)
                    Assert.assertEquals(registro.parentesco, response.body()?.registro?.parentesco)
                    Assert.assertEquals(registro.empresaEntrega, response.body()?.registro?.empresaEntrega)
                    Assert.assertEquals(registro.persona_id, response.body()?.registro?.persona_id)
                    Assert.assertEquals(registro.departamento_id, response.body()?.registro?.departamento_id)
                }
            }

            override fun onFailure(call: Call<RegistroResponse>, e: Throwable) {
                //Assert.assertFalse(call.isCanceled)
                // no supe qué hacer aqui, me tiraba test pass de cualquier forma
            }
        })

        call = request.fetchRegistros()
        call.enqueue(object : Callback<RegistroResponse> {
            override fun onResponse(call: Call<RegistroResponse>, response: Response<RegistroResponse>) {

                // It checks if status ~ 200
                if (response.isSuccessful) {

                    // Registro response must to be not null
                    Assert.assertNotNull(response.body()?.registros)

                    Log.e("tag", response.body()?.registros?.toString()!!)

                    // Response message has to be OK
                    Assert.assertEquals("OK", response.message())
                }
            }

            override fun onFailure(call: Call<RegistroResponse>, e: Throwable) {
                //Assert.assertFalse(call.isCanceled)
                // no supe qué hacer aqui, me tiraba test pass de cualquier forma
            }
        })


        Log.i("restfulApi consuming testing", "... restfulApi consuming testing finishing...")
    }
}