package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.core.clip
import androidx.ui.core.setContent
import androidx.ui.foundation.AdapterList
import androidx.ui.foundation.Border
import androidx.ui.foundation.Image
import androidx.ui.foundation.Text
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
import com.example.myapplication.model.Registro
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

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
                "System of Concierge ",
                style = typography.h6,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = Color(255, 255, 255)
            )
            Text(
                "Semestre 1 - 2020",
                style = typography.body2,
                color = Color(255, 255, 255),
                modifier = Modifier.absolutePadding(0.dp, 8.dp, 0.dp, 8.dp)
            )

            var registros: List<Registro>? = emptyList()
            Button(onClick = {
                registros = ApiConnection.fetchRegistros()!!
            }, border = Border(2.dp, Color.White)) {
                Text(
                    text = "Obtener registros"
                )
            }

            registros?.let {
                AdapterList(data = it) { registro ->
                    ListItem(
                        text = registro.fecha.toString(),
                        secondaryText = registro.parentesco
                    )
                }
            }

        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    NewsStory()
}
