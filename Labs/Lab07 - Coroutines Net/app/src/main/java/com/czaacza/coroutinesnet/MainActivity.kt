package com.czaacza.coroutinesnet

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.czaacza.coroutinesnet.ui.theme.CoroutinesNetTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.lang.Exception
import java.net.URL

const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {

    val imageUrl = URL("https://images.pexels.com/photos/3361739/pexels-photo-3361739.jpeg")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val imageLiveData = MutableLiveData<Bitmap>()

        lifecycleScope.launch {
            val imageBitmap: Bitmap? = getImage(imageUrl)
            imageLiveData.value = imageBitmap
        }

        setContent {
            CoroutinesNetTheme {
                showLayout(imageLiveData)
            }
        }
    }

    suspend fun getImage(url: URL): Bitmap? = withContext(Dispatchers.IO) {
        try {
            val connection = url.openConnection()
            val iStream = connection.getInputStream()
            val bufferedInputStream = BufferedInputStream(iStream)

            return@withContext BitmapFactory.decodeStream(bufferedInputStream)

        } catch (e: Exception) {
            Log.d(TAG, "I could not download the image from the internet.")
        }

        return@withContext null
    }

}

@Composable
fun showLayout(imageLiveData: LiveData<Bitmap>) {
    var imageState = imageLiveData.observeAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Download an image")
                },
            )
        },
        content = {
            if (imageState.value != null) {
                Image(
                    bitmap = imageState.value!!.asImageBitmap(),
                    contentDescription = "Cute doggy",
                    modifier = Modifier.padding(20.dp),
                    alignment = Alignment.Center
                )
            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Image loading",
                        modifier = Modifier.padding(20.dp),
                        fontSize = 32.sp
                    )
                    CircularProgressIndicator()
                }
            }
        }
    )
}