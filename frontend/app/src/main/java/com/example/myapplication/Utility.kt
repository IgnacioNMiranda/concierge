@file:Suppress("FunctionName")

package com.example.myapplication

import android.content.Context
import androidx.compose.*
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.layout.Row
import androidx.ui.layout.absolutePadding
import androidx.ui.layout.fillMaxWidth
import androidx.ui.material.*
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.Home
import androidx.ui.material.icons.filled.Person
import androidx.ui.unit.dp
import org.json.JSONObject

class Utility {

    companion object {

        /**
         * Parse to JSONObject a json object formatted in string.
         */
        fun ValidationErrorsToJsonObject(errorBody: String): JSONObject {
            val jsonValue = errorBody.replace("\\", "").substring(1, errorBody.length - 1)
            return JSONObject(jsonValue)
        }

        /**
         * Obtains all the validation error messages of register operation.
         */
        fun RegisterErrors(json: JSONObject): String {
            val nameMessages = json.getJSONArray("name")
            val emailMessages = json.getJSONArray("email")
            val passwordMessages = json.getJSONArray("password")

            var errors = ""
            for (i in 0 until nameMessages.length()) {
                errors += nameMessages[i]
                errors += "\n"
            }
            for (i in 0 until emailMessages.length()) {
                errors += emailMessages[i]
                errors += "\n"
            }
            for (i in 0 until passwordMessages.length()) {
                errors += passwordMessages[i]
                errors += "\n"
            }
            return errors
        }

        /**
         * Obtains all the validation error messages of register operation.
         */
        fun LoginErrors(json: JSONObject): String {
            val emailMessages = json.getJSONArray("email")
            val passwordMessages = json.getJSONArray("password")

            var errors = ""
            for (i in 0 until emailMessages.length()) {
                errors += emailMessages[i]
                errors += "\n"
            }
            for (i in 0 until passwordMessages.length()) {
                errors += passwordMessages[i]
                errors += "\n"
            }
            return errors
        }

        /**
         * Obtains all the validation error messages of operations related to Persona model.
         */
        fun PersonaErrors(json: JSONObject): String {
            val rutMessages = json.getJSONArray("rut")
            val nameMessages = json.getJSONArray("nombre")
            val phoneMessages = json.getJSONArray("telefono")
            val emailMessages = json.getJSONArray("email")
            val apartmentIdMessages = json.getJSONArray("departamento_id")

            var errors = ""
            for (i in 0 until rutMessages.length()) {
                errors += rutMessages[i]
                errors += "\n"
            }
            for (i in 0 until nameMessages.length()) {
                errors += nameMessages[i]
                errors += "\n"
            }
            for (i in 0 until phoneMessages.length()) {
                errors += phoneMessages[i]
                errors += "\n"
            }
            for (i in 0 until emailMessages.length()) {
                errors += emailMessages[i]
                errors += "\n"
            }
            for (i in 0 until apartmentIdMessages.length()) {
                errors += apartmentIdMessages[i]
                errors += "\n"
            }
            return errors
        }

        fun RegistroErrors(json: JSONObject): String {
            val dateMessages = json.getJSONArray("fecha")
            val relationshipMessages = json.getJSONArray("parentesco")
            val personIdMessages = json.getJSONArray("persona_id")
            val apartmentIdMessages = json.getJSONArray("departamento_id")

            var errors = ""
            for (i in 0 until dateMessages.length()) {
                errors += dateMessages[i]
                errors += "\n"
            }
            for (i in 0 until relationshipMessages.length()) {
                errors += relationshipMessages[i]
                errors += "\n"
            }
            for (i in 0 until personIdMessages.length()) {
                errors += personIdMessages[i]
                errors += "\n"
            }
            for (i in 0 until apartmentIdMessages.length()) {
                errors += apartmentIdMessages[i]
                errors += "\n"
            }
            return errors
        }

        fun DepartamentoErrors(json: JSONObject): String {
            val numberMessages = json.getJSONArray("numero")

            var errors = ""
            for (i in 0 until numberMessages.length()) {
                errors += numberMessages[i]
                errors += "\n"
            }
            return errors
        }

        /**
         * Shows a popup with a CircularProgressIndicator during a server request.
         */
        @Suppress("CascadeIf")
        @Composable
        fun LoadingComponent(
            popUpText: String,
            showPopup: MutableState<Boolean>,
            onPopupDismissed: () -> Unit,
            obtainingData: MutableState<Boolean>,
            receivedResponse: MutableState<Boolean>,
            context: Context
        ) {
            var text: String
            if (showPopup.value) {
                if (obtainingData.value) {
                    AlertDialog(
                        onCloseRequest = onPopupDismissed,
                        text = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalGravity = Alignment.CenterVertically
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.absolutePadding(
                                        0.dp,
                                        0.dp,
                                        10.dp,
                                        0.dp
                                    )
                                )
                                Text(text = popUpText)
                            }
                        },
                        confirmButton = { }
                    )
                    return
                } else if (!receivedResponse.value) {
                    //text = context.resources.getString(R.string.server_connection_failed)
                    text = popUpText
                } else {
                    text = context.resources.getString(R.string.server_connection_success)
                }

                AlertDialog(
                    onCloseRequest = onPopupDismissed,
                    text = {
                        Text(text)
                    },
                    confirmButton = {
                        Button(
                            onClick = onPopupDismissed,
                            text = {
                                Text("OK")
                            }
                        )
                    }
                )
            }
        }


        @Composable
        fun BottomNavigationBar(context: Context, bottomBarState: MutableState<Int>) {
            val listItems = listOf(
                context.resources.getString(R.string.visits_label),
                context.resources.getString(R.string.people_label)
            )
            val listIcons = listOf(Icons.Filled.Home, Icons.Filled.Person)

            // BottomNavigation is a component placed at the bottom of the screen that represents primary
            // destinations in your application.
            BottomNavigation(modifier = Modifier.absolutePadding(0.dp, 16.dp, 0.dp, 0.dp)) {
                listItems.forEachIndexed { index, label ->

                    // BottomNavigationItem represents a singular primary destination in the application.
                    BottomNavigationItem(
                        icon = {
                            Icon(asset = listIcons[index])
                        },
                        text = {
                            Text(text = label)
                        },
                        // Update the selected index when the BottomNavigationItem is clicked
                        selected = bottomBarState.value == index,
                        onSelected = {
                            if (bottomBarState.value != index) {
                                bottomBarState.value = index
                            }
                        }
                    )
                }
            }
        }
    }

}