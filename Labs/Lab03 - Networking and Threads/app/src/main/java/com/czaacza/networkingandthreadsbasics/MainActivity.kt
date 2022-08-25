package com.czaacza.networkingandthreadsbasics

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.czaacza.networkingandthreadsbasics.ui.theme.NetworkingAndThreadsBasicsTheme
import java.net.URL
import kotlin.concurrent.thread

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NetworkingAndThreadsBasicsTheme {
                var str = "not found"
                val thread1 = thread {
                    str = getData(
                        "https://users.metropolia.fi/~jarkkov/koe.txt"
                    )
                }
                thread1.join()
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text("DOWNLOADED TEXT: ", fontSize = 30.sp)
                    Spacer(modifier = Modifier.height(100.dp))
                    Text(str, fontSize = 30.sp)
                }

            }
        }
    }
}

fun getData(urlString: String): String {
    Log.i("DBG", "getData called")
    val myURL = URL(urlString)
    val myConn = myURL.openConnection()
    val istream = myConn.getInputStream()
    val allText = istream.bufferedReader().use { it.readText() }
    val result = StringBuilder()
    result.append(allText)
    val str = result.toString()
    return str;
}
