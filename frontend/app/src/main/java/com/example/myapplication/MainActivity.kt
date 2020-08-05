@file:Suppress("CanBeVal")

package com.example.myapplication

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.*
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.toColor
import androidx.ui.core.*
import androidx.ui.foundation.*
import androidx.ui.foundation.lazy.LazyColumnItems
import androidx.ui.foundation.selection.selectable
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.graphics.ImageAsset
import androidx.ui.graphics.SolidColor
import androidx.ui.graphics.imageFromResource
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.Edit
import androidx.ui.material.icons.filled.Favorite
import androidx.ui.res.imageResource
import androidx.ui.res.loadImageResource
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

@Preview
@Composable
fun DefaultPreview() {
    indexRegisters()
}

@Composable
fun indexRegisters() {

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

            val registros: MutableState<List<Registro>> = state { emptyList<Registro>() }
            val baseState by state { emptyList<Registro>() }
            val showPopUp: MutableState<Boolean> = state { false }
            val registrosResponse: MutableState<Boolean> = state { false }
            val obtainingData: MutableState<Boolean> = state { false }
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

            Divider(color = Color.Black)

            val onPopupDismissed = { showPopUp.value = false }
            Utility.LoadingComponent(
                showPopUp,
                onPopupDismissed,
                obtainingData,
                registrosResponse,
                context
            )

            LazyColumnItems(items = registros.value,
                modifier = Modifier.padding(0.dp),
                itemContent = { registro ->
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        border = Border(
                            1.dp,
                            Color.Magenta
                        ),
                        modifier = Modifier.absolutePadding(0.dp, 4.dp, 0.dp, 4.dp)
                    ) {
                        ListItem(
                            text = registro.fecha.toString(),
                            secondaryText = "${registro.parentesco} - rut: ${registro.persona?.rut} - numero: ${registro.departamento?.numero}"
                        )
                    }
                }
            )
        }
    }
}
