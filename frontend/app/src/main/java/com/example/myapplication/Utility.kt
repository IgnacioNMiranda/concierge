package com.example.myapplication

import android.content.Context
import android.content.Intent
import androidx.compose.*
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.layout.Row
import androidx.ui.layout.absolutePadding
import androidx.ui.layout.fillMaxWidth
import androidx.ui.layout.padding
import androidx.ui.material.*
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.Build
import androidx.ui.material.icons.filled.Home
import androidx.ui.material.icons.filled.List
import androidx.ui.material.icons.filled.Person
import androidx.ui.unit.dp

class Utility {

    companion object {

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