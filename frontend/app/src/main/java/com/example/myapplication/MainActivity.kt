package com.example.myapplication

import android.graphics.Paint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.layout.*
import androidx.ui.material.Button
import androidx.ui.material.MaterialTheme
import androidx.ui.res.imageResource
import androidx.ui.text.style.TextOverflow
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import androidx.ui.core.*
import androidx.ui.foundation.*
import androidx.ui.graphics.Brush
import androidx.ui.graphics.Color
import androidx.ui.material.ListItem
import androidx.ui.text.font.FontStyle
import androidx.ui.unit.Dp
import com.example.myapplication.api.ApiAdapter
import com.example.myapplication.api.ApiConnection
import com.example.myapplication.api.ConciergeApi
import com.example.myapplication.model.Registro
import com.example.myapplication.modelResponse.RegistroResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

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
                color = Color(255,255,255)
            )
            Text(
                "Semestre 1 - 2020",
                style = typography.body2,
                color = Color(255,255,255)
            )

            Button(onClick = {
                ApiConnection.fetchRegistros()
            }, border = Border(2.dp, Color.White)) {
                Text(
                    text = "Obtener registros"
                )
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    NewsStory()
}
