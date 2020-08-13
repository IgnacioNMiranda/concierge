package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.*
import androidx.ui.core.*
import androidx.ui.foundation.Border
import androidx.ui.foundation.Text
import androidx.ui.foundation.TextField
import androidx.ui.graphics.Color
import androidx.ui.input.TextFieldValue
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.text.style.TextOverflow
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.example.myapplication.api.ApiConnection
import com.example.myapplication.ui.MyApplicationTheme
import kotlinx.coroutines.*

class PostRegistro : AppCompatActivity(), CoroutineScope by MainScope() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                DefaultPreview2()
            }

        }
    }
}

@Composable
fun PostRegistros() {
    val context = ContextAmbient.current

    Column(
        modifier = Modifier.padding(16.dp) + Modifier.fillMaxWidth() + Modifier.fillMaxHeight(),
        horizontalGravity = Alignment.CenterHorizontally
    ) {

        /* TODO: implementation of radioGroup for 'parentesco', input rut, apartment number and radioGroup for 'empresaEntrega'*/
        var textValue by state { TextFieldValue("Enter your text here") }
        TextField(value = textValue,
            modifier = Modifier.padding(16.dp) + Modifier.fillMaxWidth(),
            // Update value of textValue with the latest value of the text field
            onValueChange = {
                textValue = it
            }
        )

        val showPopUp: MutableState<Boolean> = state { false }
        val registroResponse: MutableState<Boolean> = state { false }
        val sendingData: MutableState<Boolean> = state { false }
        Button(
            onClick = {
                showPopUp.value = true
                registroResponse.value = false
                sendingData.value = true

                ApiConnection.createRegistro(
                    registroResponse,
                    sendingData
                )
            },
            modifier = Modifier.absolutePadding(0.dp, 0.dp, 0.dp, 10.dp)
        ) {
            Text(
                text = context.resources.getString(R.string.post_register_btn)
            )
        }

        val onPopupDismissed = { showPopUp.value = false }
        Utility.LoadingComponent(
            context.resources.getString(R.string.post_placeholder),
            showPopUp,
            onPopupDismissed,
            sendingData,
            registroResponse,
            context
        )

    }
}

@Preview
@Composable
fun DefaultPreview2() {
    PostRegistros()
}
