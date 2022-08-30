package com.czaacza.networkingandthreadsbasics

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.czaacza.networkingandthreadsbasics.ui.theme.NetworkingAndThreadsBasicsTheme
import java.net.URL


class MainActivity : ComponentActivity() {
    val TAG = "MainActivityTAG"

    var textMessage = MutableLiveData<String>()

    private val myHandler = object : Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            if(msg.what == 0){
                textMessage.value = msg.obj.toString()
                Log.d(TAG, textMessage.value.toString())
            }
        }
    }
    private val url : URL = URL("https://users.metropolia.fi/~jarkkov/koe.txt")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val myRunnable = Conn(myHandler, url)
            val myThread = Thread(myRunnable)
            myThread.start()

            NetworkingAndThreadsBasicsTheme {
                ShowContent(textMessage)
            }
        }
    }
    @Composable
    fun ShowContent(liveData: LiveData<String>) {
        val textState = liveData.observeAsState()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text("DOWNLOADED TEXT: ", fontSize = 30.sp)
            Spacer(modifier = Modifier.height(100.dp))
            Text(text = textState.value.toString(), fontSize = 30.sp)
        }
    }
}


