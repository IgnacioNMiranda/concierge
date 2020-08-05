package com.example.myapplication

import android.content.Context
import androidx.compose.Composable
import androidx.compose.MutableState
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Text
import androidx.ui.layout.Row
import androidx.ui.layout.absolutePadding
import androidx.ui.layout.fillMaxWidth
import androidx.ui.material.AlertDialog
import androidx.ui.material.Button
import androidx.ui.material.CircularProgressIndicator
import androidx.ui.unit.dp

class Utility {

    companion object {

        /**
         * Shows a popup with a CircularProgressIndicator during a server request.
         */
        @Suppress("CascadeIf")
        @Composable
        fun LoadingComponent(
            showPopup: MutableState<Boolean>,
            onPopupDismissed: () -> Unit,
            obtainingData: MutableState<Boolean>,
            registrosResponse: MutableState<Boolean>,
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
                                Text(text = context.resources.getString(R.string.obtaining_data_ph))
                            }
                        },
                        confirmButton = { }
                    )
                    return
                } else if (!registrosResponse.value) {
                    text = context.resources.getString(R.string.server_connection_failed)
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
    }

}