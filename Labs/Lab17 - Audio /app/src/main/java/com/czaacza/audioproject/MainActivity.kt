package com.czaacza.audioproject

import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.czaacza.audioproject.ui.theme.AudioProjectTheme
import java.io.File
import java.io.IOException

class MainActivity : ComponentActivity() {
    lateinit var audioViewModel: AudioViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val storageDir = getExternalFilesDir(Environment.DIRECTORY_MUSIC)

        audioViewModel = AudioViewModel(application, this, storageDir)



        setContent {
            AudioProjectTheme {
                ShowContent(audioViewModel)
            }
        }
    }
}

@Composable
fun ShowContent(audioViewModel: AudioViewModel) {
    var recRunning by remember { mutableStateOf(false) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        var prefix by remember { mutableStateOf("Start") }
        Button(
            onClick = {
                if (!recRunning) {
                    audioViewModel.recRunning = true
                    recRunning = true
                    prefix = "Stop"
                } else {
                    audioViewModel.recRunning = false
                    recRunning = false
                    prefix = "Start"
                }
                audioViewModel.startRecording()
            },
            shape = MaterialTheme.shapes.large,
            modifier = Modifier
                .padding(20.dp)
                .width(300.dp)
                .height(60.dp)
        ) {
            Text(text = " $prefix record", fontSize = 32.sp)
        }
        Button(
            onClick = { /*TODO*/ },
            shape = MaterialTheme.shapes.large,
            modifier = Modifier
                .padding(20.dp)
                .width(300.dp)
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colors.primaryVariant)
        ) {
            Text(text = "Play", fontSize = 32.sp)
        }
    }
}
