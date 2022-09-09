package com.czaacza.internalexternalstorageproject

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.io.FileNotFoundException

internal const val FILENAME = "data.txt"

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShowContent(app = application)
        }
    }

    fun writeToFile(app: Application, fileName: String, outputValue: String) {
        app.openFileOutput(fileName, Context.MODE_APPEND).use {
            it.write("$outputValue\n".toByteArray())
        }
    }

    fun readFile(app: Application, fileName: String): String {
        return try {
            app.openFileInput(fileName).bufferedReader().use {
                it.readText()
            }
        } catch (e: FileNotFoundException) {
            getString(R.string.read_failed)
        }
    }

    @Composable
    fun ShowContent(app: Application) {
        var readText = readFile(app, FILENAME)
        var inputValue by remember { mutableStateOf("") }
        var textArray by remember {
            mutableStateOf(listOf<String>())
        }
        textArray = readText.split("\n")

        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(value = inputValue, onValueChange = {
                    inputValue = it
                })
                Button(onClick = {
                    writeToFile(app, FILENAME, inputValue)
                    readText = readFile(app, FILENAME)
                    textArray = readText.split("\n")
                }) {
                    Text(text = stringResource(id = R.string.button_text))
                }
            }
            LazyColumn(
                content = {
                    items(textArray) { word ->
                        Text(
                            text = word, fontSize = 18.sp,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                })
        }
    }
}




