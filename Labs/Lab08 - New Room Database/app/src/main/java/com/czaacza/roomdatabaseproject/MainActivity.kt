package com.czaacza.roomdatabaseproject

import android.content.res.Resources
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.czaacza.roomdatabaseproject.data.Player
import com.czaacza.roomdatabaseproject.data.Team
import com.czaacza.roomdatabaseproject.model.TeamViewModel
import com.czaacza.roomdatabaseproject.ui.theme.Blue500

class MainActivity : ComponentActivity() {

    private lateinit var teamViewModel: TeamViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        teamViewModel = TeamViewModel(application = application)

        setContent {
            AppNavigation(teamViewModel = teamViewModel)
        }
    }
}

@Composable
fun AppNavigation(teamViewModel: TeamViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            ShowMain(teamViewModel, navController)
        }
        composable("players/{teamId}") {
            val id = it.arguments?.getString("teamId")?.toLong() ?: 0
            ShowPlayers(teamViewModel = teamViewModel, id, navController)
        }
    }

}

@Composable
fun ShowMain(teamViewModel: TeamViewModel, navController: NavController) {
    Column {
        ShowTopAppBar()
        InsertTeam(teamViewModel = teamViewModel)
        ListUsers(teamViewModel = teamViewModel, navController = navController)
    }
}

@Composable
fun ShowPlayers(teamViewModel: TeamViewModel, id: Long, navController: NavController) {
    val chosenTeam = teamViewModel.getTeamById(id)?.observeAsState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = chosenTeam?.value?.name.toString(),
            fontSize = 35.sp,
            fontWeight = FontWeight(700)
        )
        ListPlayers(teamViewModel = teamViewModel, id = id)
    }
}

@Composable
fun ListPlayers(teamViewModel: TeamViewModel, id: Long) {
    val playersList = teamViewModel.getPlayersByTeamId(id).observeAsState()
    if (playersList.value != null) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            InsertPlayer(teamViewModel = teamViewModel, id = id)
            LazyColumn(content = {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 20.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(stringResource(id = R.string.header_players), fontSize = 20.sp)
                    }
                }
                items(playersList.value!!) { player ->
                    Text(
                        "Player:   $player", fontSize = 27.sp,
                    )
                }
            })
        }

    }
}

@Composable
fun ListUsers(teamViewModel: TeamViewModel, navController: NavController) {
    val teamList = teamViewModel.getAllTeams()?.observeAsState()
    if (teamList?.value != null) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(content = {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 20.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(stringResource(id = R.string.header_players), fontSize = 20.sp)
                    }
                }
                items(teamList.value!!) { team ->
                    Text("Team: $team", fontSize = 35.sp,
                        modifier = Modifier.clickable() {
                            navController.navigate("players/${team.tid}")
                        })
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
            .padding(top = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        TextField(
            value = teamName,
            placeholder = {
                Text(text = stringResource(id = R.string.text_field_placeholder_teamname))
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
fun InsertPlayer(teamViewModel: TeamViewModel, id: Long) {
    var playerFirstName by remember { mutableStateOf("") }
    var playerLastName by remember { mutableStateOf("") }
    var playerNumber by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        TextField(
            value = playerFirstName,
            placeholder = {
                Text(text = stringResource(id = R.string.text_field_placeholder_firstname))
            },
            onValueChange = {
                playerFirstName = it
            })

        TextField(
            value = playerLastName,
            placeholder = {
                Text(text = stringResource(id = R.string.text_field_placeholder_lastname))
            },
            onValueChange = {
                playerLastName = it
            })

        TextField(
            value = playerNumber,
            placeholder = {
                Text(text = stringResource(id = R.string.text_field_placeholder_number))
            },
            onValueChange = {
                playerNumber = it
            })

        Button(onClick = {
            try {
                teamViewModel.insertPlayer(
                    Player(
                        id,
                        0,
                        playerFirstName,
                        playerLastName,
                        playerNumber.toInt()
                    )
                )
            } catch (e: NumberFormatException) {
                teamViewModel.insertPlayer(
                    Player(
                        id,
                        0,
                        playerFirstName,
                        playerLastName,
                        0
                    )
                )
            }
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
        },
    )
}