package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.*
import androidx.ui.core.Alignment
import androidx.ui.core.ContextAmbient
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.Text
import androidx.ui.input.ImeAction
import androidx.ui.input.KeyboardType
import androidx.ui.input.PasswordVisualTransformation
import androidx.ui.input.TextFieldValue
import androidx.ui.layout.*
import androidx.ui.material.Button
import androidx.ui.material.OutlinedTextField
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.example.myapplication.api.ApiConnection
import com.example.myapplication.ui.MyApplicationTheme

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                SignUpPreview()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpPreview() {
    val context = ContextAmbient.current

    Column(
        modifier = Modifier.padding(16.dp) + Modifier.fillMaxWidth() + Modifier.fillMaxHeight(),
        horizontalGravity = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        var nameTextValue by state { TextFieldValue("") }
        OutlinedTextField(value = nameTextValue,
            modifier = Modifier.padding(16.dp) + Modifier.fillMaxWidth(),
            onValueChange = {
                nameTextValue = it
            },
            label = { Text(context.resources.getString(R.string.name_input_label)) }
        )

        var emailTextValue by state { TextFieldValue("") }
        OutlinedTextField(value = emailTextValue,
            modifier = Modifier.padding(16.dp) + Modifier.fillMaxWidth(),
            onValueChange = {
                emailTextValue = it
            },
            label = { Text(context.resources.getString(R.string.email_input_label)) }
        )

        var pwTextValue by state { TextFieldValue("") }
        OutlinedTextField(value = pwTextValue,
            modifier = Modifier.padding(16.dp) + Modifier.fillMaxWidth(),
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done,
            // Visual transformation is used to modify the visual output of the input field. In
            // this example, I'm using an existing visual transformation - the
            // PasswordVisualTransformation. All it does is that it transforms any input character
            // into "•". The text itself isn't altered, just its visual appearance is. You can
            // easily created you own visual transformations by implementing the
            // VisualTransformation interface.
            visualTransformation = PasswordVisualTransformation(),
            // Update value of pwTextValue with the latest value of the text field
            onValueChange = {
                pwTextValue = it
            },
            label = { Text(context.resources.getString(R.string.password_input_label)) }
        )

        var pwConfirmationTextValue by state { TextFieldValue("") }
        OutlinedTextField(value = pwConfirmationTextValue,
            modifier = Modifier.padding(16.dp) + Modifier.fillMaxWidth(),
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done,
            // Visual transformation is used to modify the visual output of the input field. In
            // this example, I'm using an existing visual transformation - the
            // PasswordVisualTransformation. All it does is that it transforms any input character
            // into "•". The text itself isn't altered, just its visual appearance is. You can
            // easily created you own visual transformations by implementing the
            // VisualTransformation interface.
            visualTransformation = PasswordVisualTransformation(),
            // Update value of pwTextValue with the latest value of the text field
            onValueChange = {
                pwConfirmationTextValue = it
            },
            label = { Text(context.resources.getString(R.string.password_confirmation_input_label)) }
        )

        val showPopUp: MutableState<Boolean> = state { false }
        val registerResponse: MutableState<Boolean> = state { false }
        val sendingData: MutableState<Boolean> = state { false }
        val popUpStringContent: MutableState<String> = state { "" }
        Button(
            onClick = {
                showPopUp.value = true
                registerResponse.value = false
                sendingData.value = true
                popUpStringContent.value = context.resources.getString(R.string.sign_up_placeholder)

                ApiConnection.register(
                    context,
                    registerResponse,
                    sendingData,
                    nameTextValue.text,
                    emailTextValue.text,
                    pwTextValue.text,
                    pwConfirmationTextValue.text,
                    popUpStringContent
                )
            },
            modifier = Modifier.absolutePadding(0.dp, 0.dp, 0.dp, 10.dp)
        ) {
            Text(
                text = context.resources.getString(R.string.sign_up_button)
            )
        }

        val onPopupDismissed = { showPopUp.value = false }
        Utility.LoadingComponent(
            popUpStringContent.value,
            showPopUp,
            onPopupDismissed,
            sendingData,
            registerResponse,
            context
        )

    }
}
