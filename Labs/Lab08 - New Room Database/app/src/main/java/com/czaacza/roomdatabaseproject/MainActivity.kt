package com.czaacza.roomdatabaseproject

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.czaacza.roomdatabaseproject.data.Team
import com.czaacza.roomdatabaseproject.data.TeamViewModel

const val TAG = "MainActivityTag"

class MainActivity : ComponentActivity() {

    private lateinit var teamViewModel: TeamViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        teamViewModel = TeamViewModel(application = application)

//        teamViewModel.insert(Team(1, "Arsenal"))
//        teamViewModel.insert(Team(2, "Chelsea"))
//        teamViewModel.insert(Team(3, "Real"))

        setContent {
            Column {
                ShowTopAppBar()
                InsertTeam(teamViewModel = teamViewModel)
                ListUsers(teamViewModel = teamViewModel)
            }
        }
    }
}

@Composable
fun ListUsers(teamViewModel: TeamViewModel) {
    val teamList = teamViewModel.getAllTeams()?.observeAsState()
    if (teamList?.value != null) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(content = {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(stringResource(id = R.string.header_teams), fontSize = 20.sp)
                    }
                }
                items(teamList.value!!) { team ->
                    Text("Team: $team", fontSize = 35.sp)
                }
            })
        }

    }
}

@Composable
fun InsertTeam(teamViewModel: TeamViewModel) {
    var teamName by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        TextField(
            value = teamName,
            placeholder = {
                Text(text = stringResource(id = R.string.text_field_placeholder))
            },
            onValueChange = {
                teamName = it
            })

        Button(onClick = {
            teamViewModel.insert(Team(0, teamName))
        }) {
            Text(text = stringResource(id = R.string.button_text))
        }
    }
}

@Composable
fun ShowTopAppBar() {
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.top_app_bar_title))
        }
    )
}