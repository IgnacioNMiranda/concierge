package com.example.myapplication

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
import com.example.myapplication.api.RegistroCallback
import com.example.myapplication.model.Registro
import com.example.myapplication.modelResponse.RegistroResponse
import kotlinx.coroutines.*
import retrofit2.Response

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
                "Conserjería",
                style = typography.h6,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = Color.Black,
                modifier = Modifier.absolutePadding(0.dp, 4.dp, 0.dp, 4.dp)
            )

            var registros: MutableState<List<Registro>> = state { emptyList<Registro>() }
            var showPopup by state { false }
            var registrosResponse by state { false }
            var obtainingData by state { false }
            Button(
                onClick = {
                    showPopup = true
                    registrosResponse = false
                    obtainingData = true
                    runBlocking {
                        withContext(Dispatchers.Default) {
                            ApiConnection.fetchRegistros(object : RegistroCallback {
                                override fun fetchRegistros(response: Response<RegistroResponse>?) {
                                    if (response != null && response.isSuccessful) {
                                        registrosResponse = true
                                        obtainingData = false
                                        registros.value = response.body()?.registros?.toList()!!
                                    } else {
                                        obtainingData = false
                                    }
                                }
                            })
                        }
                    }
                },
                border = Border(2.dp, Color.Magenta),
                modifier = Modifier.absolutePadding(0.dp, 0.dp, 0.dp, 10.dp)
            ) {
                Text(
                    text = "Obtener registros"
                )
            }

            val onPopupDismissed = { showPopup = false }
            LoadingComponent(showPopup, onPopupDismissed, obtainingData, registrosResponse)

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
    showPopup: Boolean,
    onPopupDismissed: () -> Unit,
    obtainingData: Boolean,
    registrosResponse: Boolean
) {
    if (showPopup) {
        var text: String
        if (obtainingData) {
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
                        Text("Obteniendo datos...")
                    }
                },
                confirmButton = { }
            )
            return
        } else if (!registrosResponse) {
            text = "Fallo del servidor. No se pudo recuperar los registros ):"
        } else {
            text = "Datos recuperados :D"
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
