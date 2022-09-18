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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    lateinit var recordViewModel: RecordViewModel
    lateinit var playViewModel: PlayViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val storageDir = getExternalFilesDir(Environment.DIRECTORY_MUSIC)

        recordViewModel = RecordViewModel(application, this, storageDir)
        playViewModel = PlayViewModel(application, storageDir)

        setContent {
            AudioProjectTheme {
                ShowContent(recordViewModel, playViewModel)
            }
        }
    }
}

@Composable
fun ShowContent(recordViewModel: RecordViewModel, playViewModel: PlayViewModel) {
    var recRunning by remember { mutableStateOf(false) }
    var playRunning by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        var recordPrefix by remember { mutableStateOf("Start") }
        var playPrefix by remember { mutableStateOf("Start") }

        Button(
            onClick = {
                if (!recRunning) {
                    recordViewModel.recRunning = true
                    recRunning = true
                    recordPrefix = "Stop"
                } else {
                    recordViewModel.recRunning = false
                    recRunning = false
                    recordPrefix = "Start"
                }
                recordViewModel.record()
            },
            shape = MaterialTheme.shapes.large,
            modifier = Modifier
                .padding(20.dp)
                .width(300.dp)
                .height(60.dp)
        ) {
            Text(text = " $recordPrefix record", fontSize = 32.sp)
        }
        Button(
            onClick = {
                if (!playRunning) {
                    playViewModel.playRunning = true
                    playRunning = true
                    playPrefix = "Stop"
                } else {
                    playViewModel.playRunning = false
                    playRunning = false
                    playPrefix = "Start"
                }

                GlobalScope.launch(Dispatchers.IO) {
                    val play = async {
                        playViewModel.playAudio()
                    }
                    play.await()
                }
            },
            shape = MaterialTheme.shapes.large,
            modifier = Modifier
                .padding(20.dp)
                .width(300.dp)
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colors.primaryVariant)
        ) {
            Text(text = "$playPrefix playing", fontSize = 32.sp)
        }
    }
}
