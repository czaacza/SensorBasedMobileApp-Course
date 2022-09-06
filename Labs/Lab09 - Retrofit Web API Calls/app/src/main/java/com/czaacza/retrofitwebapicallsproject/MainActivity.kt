package com.czaacza.retrofitwebapicallsproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.czaacza.retrofitwebapicallsproject.model.MyViewModel
import com.czaacza.retrofitwebapicallsproject.data.PresidentsDataProvider

class MainActivity : ComponentActivity() {

    private var myViewModel: MyViewModel = MyViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShowList(myViewModel)
        }
    }
}

@Composable
fun ShowList(myViewModel: MyViewModel) {
    val totalHitsState = myViewModel.changeNotifier.observeAsState()
    var clickedPresidentName by remember { mutableStateOf("") }
    val list = PresidentsDataProvider.presidents
    var insideText = ""

    Column() {
        if (clickedPresidentName != "") {
            insideText = "${clickedPresidentName}, hits: ${totalHitsState.value.toString()}"
        }
        Text(
            text = insideText,
            fontSize = 25.sp,
            fontWeight = FontWeight(700),
            modifier = Modifier.padding(20.dp)
        )
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(20.dp)
        ) {
            for (president in list) {
                Text(
                    text = president.name, modifier = Modifier
                        .selectable(selected = true, onClick = {
                            clickedPresidentName = president.name
                            myViewModel.getHits(president.name)
                        }),
                    fontSize = 30.sp
                )

            }
        }
    }
}
