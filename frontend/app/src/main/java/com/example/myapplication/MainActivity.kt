package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.MutableState
import androidx.compose.state
import androidx.ui.core.ContextAmbient
import androidx.ui.core.Modifier
import androidx.ui.core.clip
import androidx.ui.core.setContent
import androidx.ui.foundation.*
import androidx.ui.foundation.lazy.LazyColumnItems
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.material.Button
import androidx.ui.material.ListItem
import androidx.ui.material.MaterialTheme
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
    val context = ContextAmbient.current
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
                "Concerjería",
                style = typography.h6,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = Color.Black,
                modifier = Modifier.absolutePadding(0.dp, 4.dp, 0.dp, 4.dp)
            )

            var registros: MutableState<List<Registro>> = state { emptyList<Registro>() }
            Button(
                onClick = {
                    runBlocking {
                        withContext(Dispatchers.Default) {
                            ApiConnection.fetchRegistros(object : RegistroCallback {
                                override fun fetchRegistros(response: Response<RegistroResponse>?) {
                                    if (response != null && response.isSuccessful) {
                                        registros.value = response.body()?.registros?.toList()!!

                                        Toast.makeText(
                                            context,
                                            "Registros recuperados!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "No se pudo recuperar los registros :(",
                                            Toast.LENGTH_SHORT
                                        ).show()
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
