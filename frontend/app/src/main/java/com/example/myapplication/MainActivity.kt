package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.ui.foundation.Image
import androidx.ui.foundation.Text
import androidx.ui.foundation.contentColor
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.layout.*
import androidx.ui.material.Button
import androidx.ui.material.MaterialTheme
import androidx.ui.res.imageResource
import androidx.ui.text.style.TextOverflow
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import androidx.ui.core.*
import com.example.myapplication.api.ApiAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
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
                overflow = TextOverflow.Ellipsis
            )
            Text(
                "Semestre 1 - 2020",
                style = typography.body2
            )

            /*
            Button(onClick = {
                MainScope().launch(Dispatchers.Main) {

                    val context = ContextAmbient.current

                    // Try catch block to handle exceptions when calling the API.
                    try {
                        val response = ApiAdapter.apiClient.fetchRegistros()
                        // Check if response was successful.
                        if (response.isSuccessful && response.body() != null) {
                            val data = response.body()

                            // Check for null safety.
                            /*response.message().let { imageUrl ->
                                // Load URL into the ImageView using Coil.
                                iv_dog_image.load(imageUrl)
                            }*/
                        } else {

                            // Show API error.
                            Toast.makeText(
                                context,
                                "Error Occurred: ${response.message()}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } catch (e: Exception) {
                        // Show API error. This is the error raised by the client.
                        Toast.makeText(
                            context,
                            "Error Occurred: ${e.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }) {
                Text(
                    text = "Button"
                )
            }*/
        }

    }
}

@Preview
@Composable
fun DefaultPreview() {
    NewsStory()
}
