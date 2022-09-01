package com.czaacza.coroutinesnet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.czaacza.coroutinesnet.ui.theme.CoroutinesNetTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoroutinesNetTheme {
                showLayout()
            }
        }
    }

    suspend fun getImage(urlString : String) = withContext(Dispatchers.IO){
        val url = URL(urlString)
        
    }

}

@Composable
fun showLayout() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Download an image")
                },
            )
        },
        content = {

        }
    )
}