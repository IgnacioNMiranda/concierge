package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.*
import androidx.ui.core.Alignment
import androidx.ui.core.ContextAmbient
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.Border
import androidx.ui.foundation.Text
import androidx.ui.foundation.TextField
import androidx.ui.graphics.Color
import androidx.ui.input.TextFieldValue
import androidx.ui.layout.*
import androidx.ui.material.Button
import androidx.ui.material.Divider
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Surface
import androidx.ui.text.style.TextOverflow
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.example.myapplication.api.ApiConnection
import com.example.myapplication.ui.MyApplicationTheme

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    LoginPreview()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    MaterialTheme {
        val typography = MaterialTheme.typography
        val context = ContextAmbient.current
        Column(
            modifier = Modifier.padding(16.dp) + Modifier.fillMaxWidth() + Modifier.fillMaxHeight(),
            horizontalGravity = Alignment.CenterHorizontally
        ) {
            Text(
                text = context.resources.getString(R.string.title_activity_login),
                style = typography.h6,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = Color.Black,
                modifier = Modifier.absolutePadding(0.dp, 8.dp, 0.dp, 0.dp)
            )

            Divider(color = Color.Black)

            /* TODO: implementation of selects and datapickers*/
            var textValue by state { TextFieldValue("Enter your text here") }
            TextField(value = textValue,
                modifier = Modifier.padding(16.dp) + Modifier.fillMaxWidth(),
                // Update value of textValue with the latest value of the text field
                onValueChange = {
                    textValue = it
                }
            )

            var showPopUp: MutableState<Boolean> = state { false }
            var registroResponse: MutableState<Boolean> = state { false }
            var sendingData: MutableState<Boolean> = state { false }
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
                border = Border(2.dp, Color.Magenta),
                modifier = Modifier.absolutePadding(0.dp, 0.dp, 0.dp, 10.dp)
            ) {
                Text(
                    text = context.resources.getString(R.string.post_register_btn)
                )
            }

            val onPopupDismissed = { showPopUp.value = false }
            Utility.LoadingComponent(showPopUp, onPopupDismissed, sendingData, registroResponse, context)

        }
    }
}
