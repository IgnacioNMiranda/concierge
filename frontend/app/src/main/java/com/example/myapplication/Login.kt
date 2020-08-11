package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.*
import androidx.core.graphics.toColor
import androidx.ui.core.Alignment
import androidx.ui.core.ContextAmbient
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.Border
import androidx.ui.foundation.Text
import androidx.ui.foundation.TextField
import androidx.ui.graphics.Color
import androidx.ui.input.ImeAction
import androidx.ui.input.KeyboardType
import androidx.ui.input.PasswordVisualTransformation
import androidx.ui.input.TextFieldValue
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.text.style.TextOverflow
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.example.myapplication.api.ApiAdapter
import com.example.myapplication.api.ApiConnection
import com.example.myapplication.model.User
import com.example.myapplication.modelResponse.AuthResponse
import com.example.myapplication.ui.MyApplicationTheme

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface {
                    LoginPreview()
                }
            }
        }

        ApiAdapter.setContext(applicationContext)

        /*applicationContext.getSharedPreferences(
            "CONCIERGE_APP",
            Context.MODE_PRIVATE
        ).edit().clear().apply()*/

        val authToken = applicationContext.getSharedPreferences(
            "CONCIERGE_APP",
            Context.MODE_PRIVATE
        ).getString("AUTH_TOKEN", null)

        if (authToken != null) {
            val intent = Intent(applicationContext, MainActivity::class.java)
            applicationContext.startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
            finish()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {

    val context = ContextAmbient.current

    Column(
        modifier = Modifier.padding(16.dp) + Modifier.fillMaxWidth() + Modifier.fillMaxHeight(),
        horizontalGravity = Alignment.CenterHorizontally
    ) {

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

        val showPopUp: MutableState<Boolean> = state { false }
        val loginResponse: MutableState<Boolean> = state { false }
        val sendingData: MutableState<Boolean> = state { false }
        Button(
            onClick = {
                showPopUp.value = true
                loginResponse.value = false
                sendingData.value = true

                ApiConnection.login(
                    context,
                    loginResponse,
                    sendingData,
                    emailTextValue.text,
                    pwTextValue.text
                )
            },
            modifier = Modifier.absolutePadding(0.dp, 0.dp, 0.dp, 10.dp)
        ) {
            Text(
                text = context.resources.getString(R.string.login_button)
            )
        }

        val onPopupDismissed = { showPopUp.value = false }
        Utility.LoadingComponent(
            context.resources.getString(R.string.login_placeholder),
            showPopUp,
            onPopupDismissed,
            sendingData,
            loginResponse,
            context
        )

    }
}
