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

        var rutPersona by state { TextFieldValue("") }
        var numeroDept by state { TextFieldValue("") }

        val relOptions = listOf(
            context.resources.getString(R.string.relation_family),
            context.resources.getString(R.string.relation_ext),
            context.resources.getString(R.string.relation_company)
        )
        var relSelection by state { context.resources.getString(R.string.relation_ext) }
        var isShippingCompany by state { false }

        /* TODO: Automatic formatting of RUT */
        OutlinedTextField(value = rutPersona,
            modifier = Modifier.padding(16.dp) + Modifier.fillMaxWidth(),
            // Update value of textValue with the latest value of the text field
            onValueChange = {
                rutPersona = it
            },
            label = { Text(context.resources.getString(R.string.insert_rut)) }
        )

        OutlinedTextField(value = numeroDept,
            modifier = Modifier.padding(16.dp) + Modifier.fillMaxWidth(),
            // Update value of textValue with the latest value of the text field
            onValueChange = {
                numeroDept = it
            },
            label = { Text(context.resources.getString(R.string.insert_dept)) }
        )

        RadioGroup(
            options = relOptions,
            selectedOption = relSelection,
            onSelectedChange = { str -> relSelection = str }
        )

        Row {
            Checkbox(
                checked = isShippingCompany,
                onCheckedChange = { isShippingCompany = !isShippingCompany },
                enabled = relSelection == relOptions[2]
            )
            Text(text = context.resources.getString(R.string.is_shipping_company))
        }

        // Keep checking if both are true for potential send
        isShippingCompany = relSelection.equals(relOptions[2]) && isShippingCompany

        val showPopUp: MutableState<Boolean> = state { false }
        val registroResponse: MutableState<Boolean> = state { false }
        val sendingData: MutableState<Boolean> = state { false }
        val invalidFieldsResponse: MutableState<Boolean> = state { false }
        val popUpStringContent: MutableState<String> = state { "" }
        Button(
            onClick = {
                showPopUp.value = true
                registroResponse.value = false
                sendingData.value = true
                invalidFieldsResponse.value = false
                popUpStringContent.value = context.resources.getString(R.string.post_placeholder)

                // Use backend values for parentesco
                when (relSelection) {
                    context.resources.getString(R.string.relation_family) -> relSelection =
                        "Familiar"
                    context.resources.getString(R.string.relation_ext) -> relSelection = "Externo"
                    context.resources.getString(R.string.relation_company) -> relSelection =
                        "Empresa"
                }

                ApiConnection.createRegistro(
                    context,
                    registroResponse,
                    sendingData,
                    invalidFieldsResponse,
                    relSelection,
                    isShippingCompany,
                    rutPersona.text,
                    numeroDept.text,
                    popUpStringContent
                )
            },
            modifier = Modifier.absolutePadding(0.dp, 10.dp, 0.dp, 0.dp)
        ) {
            Text(
                text = context.resources.getString(R.string.post_register_btn)
            )
        }

        val onPopupDismissed = { showPopUp.value = false }
        Utility.LoadingComponent(
            popUpStringContent.value,
            showPopUp,
            onPopupDismissed,
            sendingData,
            registroResponse,
            invalidFieldsResponse,
            context
        )

    }
}

@Preview
@Composable
fun DefaultPreview2() {
    PostRegistros()
}
