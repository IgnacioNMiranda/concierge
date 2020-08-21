package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.*
import androidx.ui.core.*
import androidx.ui.foundation.Text
import androidx.ui.input.TextFieldValue
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.example.myapplication.api.ApiConnection
import com.example.myapplication.ui.MyApplicationTheme
import kotlinx.coroutines.*


class PostPersona : AppCompatActivity(), CoroutineScope by MainScope() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                PostPersonas()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PostPersonas() {
    val context = ContextAmbient.current

    Column(
        modifier = Modifier.padding(16.dp) + Modifier.fillMaxWidth() + Modifier.fillMaxHeight(),
        horizontalGravity = Alignment.CenterHorizontally
    ) {
        var rutTextValue by state { TextFieldValue("") }
        OutlinedTextField(value = rutTextValue,
            modifier = Modifier.padding(16.dp) + Modifier.fillMaxWidth(),
            onValueChange = {
                rutTextValue = it
            },
            label = { Text(context.resources.getString(R.string.rut_input_label)) }
        )

        var nameTextValue by state { TextFieldValue("") }
        OutlinedTextField(value = nameTextValue,
            modifier = Modifier.padding(16.dp) + Modifier.fillMaxWidth(),
            onValueChange = {
                nameTextValue = it
            },
            label = { Text(context.resources.getString(R.string.name_input_label)) }
        )

        var phoneTextValue by state { TextFieldValue("") }
        OutlinedTextField(value = phoneTextValue,
            modifier = Modifier.padding(16.dp) + Modifier.fillMaxWidth(),
            onValueChange = {
                phoneTextValue = it
            },
            label = { Text(context.resources.getString(R.string.phone_input_label)) }
        )

        var emailTextValue by state { TextFieldValue("") }
        OutlinedTextField(value = emailTextValue,
            modifier = Modifier.padding(16.dp) + Modifier.fillMaxWidth(),
            onValueChange = {
                emailTextValue = it
            },
            label = { Text(context.resources.getString(R.string.email_input_label)) }
        )

        var deptTextValue by state { TextFieldValue("") }
        OutlinedTextField(value = deptTextValue,
            modifier = Modifier.padding(16.dp) + Modifier.fillMaxWidth(),
            onValueChange = {
                deptTextValue = it
            },
            label = { Text(context.resources.getString(R.string.dept_input_label)) }
        )

        val showPopUp: MutableState<Boolean> = state { false }
        val personaResponse: MutableState<Boolean> = state { false }
        val sendingData: MutableState<Boolean> = state { false }
        val invalidFieldsResponse: MutableState<Boolean> = state { false }
        val popUpStringContent: MutableState<String> = state { "" }
        Button(
            onClick = {
                showPopUp.value = true
                personaResponse.value = false
                sendingData.value = true
                invalidFieldsResponse.value = false
                popUpStringContent.value =
                    context.resources.getString(R.string.post_placeholder_person)

                ApiConnection.createPersona(
                    context,
                    personaResponse,
                    sendingData,
                    invalidFieldsResponse,
                    rutTextValue.text,
                    nameTextValue.text,
                    phoneTextValue.text,
                    emailTextValue.text,
                    deptTextValue.text,
                    popUpStringContent
                )
            },
            modifier = Modifier.absolutePadding(0.dp, 0.dp, 0.dp, 10.dp)
        ) {
            Text(
                text = context.resources.getString(R.string.post_person_btn)
            )
        }

        val onPopupDismissed = { showPopUp.value = false }
        Utility.LoadingComponent(
            popUpStringContent.value,
            showPopUp,
            onPopupDismissed,
            sendingData,
            personaResponse,
            invalidFieldsResponse,
            context
        )

    }
}
