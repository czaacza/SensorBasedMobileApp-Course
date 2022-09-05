package com.czaacza.roomdatabaseapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.czaacza.roomdatabaseapp.data.TeamDB
import com.czaacza.roomdatabaseapp.data.TeamDao
import com.czaacza.roomdatabaseapp.data.TeamViewModel
import com.czaacza.roomdatabaseapp.ui.theme.RoomDatabaseAppTheme

const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {

    private lateinit var teamViewModel: TeamViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
//        teamViewModel = TeamViewModel(application = application)
//        Log.d(TAG, teamViewModel.toString())
        setContent {
            RoomDatabaseAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RoomDatabaseAppTheme {
        Greeting("Android")
    }
}