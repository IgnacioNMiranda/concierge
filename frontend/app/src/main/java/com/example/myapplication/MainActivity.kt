@file:Suppress("CanBeVal")

package com.example.myapplication

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.*
import androidx.ui.core.*
import androidx.ui.foundation.*
import androidx.ui.foundation.lazy.LazyColumnItems
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.res.imageResource
import androidx.ui.text.style.TextOverflow
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.example.myapplication.api.ApiConnection
import com.example.myapplication.model.Registro
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DefaultPreview()
        }
    }
}

@Composable
fun NewsStory() {

    val context = ContextAmbient.current

    val image = imageResource(R.drawable.concierge2)
    MaterialTheme {
        val typography = MaterialTheme.typography
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            val imageModifier = Modifier
                .preferredHeightIn(maxHeight = 180.dp)
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(4.dp))

            Image(image, modifier = imageModifier)
            Spacer(Modifier.preferredHeight(16.dp))

            Text(
                text = context.resources.getString(R.string.app_title),
                style = typography.h6,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = Color.Black,
                modifier = Modifier.absolutePadding(0.dp, 4.dp, 0.dp, 4.dp)
            )

            var registros: MutableState<List<Registro>> = state { emptyList<Registro>() }
            var baseState by state { emptyList<Registro>() }
            var showPopUp: MutableState<Boolean> = state { false }
            var registrosResponse: MutableState<Boolean> = state { false }
            var obtainingData: MutableState<Boolean> = state { false }
            Button(
                onClick = {
                    registros.value = baseState
                    showPopUp.value = true
                    registrosResponse.value = false
                    obtainingData.value = true

                    ApiConnection.fetchRegistros(
                        registros,
                        registrosResponse,
                        obtainingData
                    )
                },
                border = Border(2.dp, Color.Magenta),
                modifier = Modifier.absolutePadding(0.dp, 0.dp, 0.dp, 10.dp)
            ) {
                Text(
                    text = context.resources.getString(R.string.fetch_registers_btn)
                )
            }

            val onPopupDismissed = { showPopUp.value = false }
            LoadingComponent(showPopUp, onPopupDismissed, obtainingData, registrosResponse, context)

            LazyColumnItems(items = registros.value,
                modifier = Modifier.padding(0.dp),
                itemContent = { registro ->
                    ListItem(
                        text = registro.fecha.toString(),
                        secondaryText = registro.parentesco + " - rut: " + registro.persona?.rut + " - departamento: " + registro.departamento?.numero
                    )
                }
            )

        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    NewsStory()
}

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
