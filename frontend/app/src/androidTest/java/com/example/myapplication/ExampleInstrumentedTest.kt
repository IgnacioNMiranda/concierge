package com.example.myapplication

import android.util.Log
import android.widget.Toast
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myapplication.api.ApiAdapter
import com.example.myapplication.api.ConciergeApi
import com.example.myapplication.modelResponse.RegistroResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.myapplication", appContext.packageName)
    }

    @Test
    fun registroApiConnection() {

        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        val request = ApiAdapter.buildService(ConciergeApi::class.java)
        val call = request.fetchRegistros()

        Log.e("aaaaaaaaaa", "inicio de llamado")
        call.enqueue(object : Callback<RegistroResponse> {
            override fun onResponse(call: Call<RegistroResponse>, response: Response<RegistroResponse>) {

                var registros = response.body()?.registros

                var registro1 = registros?.elementAt(0)

                Log.d("response", "onResponse")
                Log.d("registros", registros.toString())
                Log.d("registro1", registro1.toString())
                Log.d("parentesco", registro1?.parentesco)
                if (response.isSuccessful) {
                    Log.d("yei", response.message())
                }
            }

            override fun onFailure(call: Call<RegistroResponse>, e: Throwable) {
                Log.e("nanai", e.message)
                Toast.makeText(appContext, "${e.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}