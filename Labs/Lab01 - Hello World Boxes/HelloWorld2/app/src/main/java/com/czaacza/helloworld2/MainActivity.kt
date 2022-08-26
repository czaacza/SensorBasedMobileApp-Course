package com.czaacza.helloworld2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.czaacza.helloworld2.ui.theme.Blue200
import com.czaacza.helloworld2.ui.theme.Blue700
import com.czaacza.helloworld2.ui.theme.HelloWorld2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HelloWorld2Theme {

                var helloWorldString by
                remember {
                    mutableStateOf(
                        ""
                    )
                }
                helloWorldString = stringResource(id = R.string.hello_world)

                var text by remember { mutableStateOf("") }

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("W1_D1 Lab 1") },
                        )
                    },
                    content = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Box(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .fillMaxWidth()
                                    .fillMaxHeight(0.5f)
                                    .background(Blue200),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxHeight(),
                                    verticalArrangement = Arrangement.SpaceBetween,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(text = helloWorldString)
                                    Box(
                                        modifier = Modifier
                                            .padding(bottom = 10.dp)
                                            .fillMaxSize(0.5f)
                                            .background(Blue700),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text("2", color = Color.White)
                                    }

                                }
                            }
                            Spacer(modifier = Modifier.height(100.dp))
                            TextField(
                                value = text,
                                onValueChange = { text = it },
                                placeholder = {
                                    Text(text = "Put your name here")
                                }
                            )
                        }
                    },
                    bottomBar = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(onClick = {
                                if (text.length > 0){
                                    helloWorldString = "Hello $text!"
                                } else {
                                    if(helloWorldString == "Hello World!"){
                                        helloWorldString = "Goodbye Darkness"
                                    } else {
                                        helloWorldString = "Hello World!"
                                    }
                                }
                            }) {
                                Text(text = stringResource(id = R.string.button))
                            }
                        }

                    }
                )
            }
        }
    }
}