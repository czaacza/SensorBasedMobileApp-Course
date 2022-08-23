package com.czaacza.lab03presidents

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            ShowList(navController);
            NavHost(
                navController = navController,
                startDestination = "showList"
            ) {
                composable(route = "showList") { ShowList(navController) }
                composable(route = "showInfo/{clickedPresident}") { navBackStackEntry ->
                    ShowInfo(
                        navBackStackEntry.arguments?.getString("clickedPresident"),
                        navController
                    )
                }


            }
        }
    }

}


@Composable
fun ShowList(navControler: NavController) {
    val list = DataProvider.presidents
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
    ) {
        for (president in list) {
            Text(
                text = president.name, modifier = Modifier
                    .selectable(selected = true, onClick = {
                        navControler.navigate("showInfo/$president") {
                            popUpTo("showList") {
                                inclusive = true;
                            }
                        }
                    }),
                fontSize = 30.sp
            )

        }
    }
}

@Composable
fun ShowInfo(string: String?, navControler: NavController) {
    if (string != null) {
        val foundPresident = DataProvider.presidents.find { it.toString() == string }
        Log.i("FoundPresident", foundPresident.toString())

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(20.dp)
        ) {
            Text(text = "President Info", fontSize = 48.sp)
            Spacer(modifier = Modifier.height(50.dp))
            Text(text = "name: " + foundPresident?.name, fontSize = 30.sp)
            Text(text = "start office year: " + foundPresident?.startOfficeYear, fontSize = 30.sp)
            Text(text = "end office year: " + foundPresident?.endOfficeYear, fontSize = 30.sp)
            Text(text = "description: " + foundPresident?.description, fontSize = 30.sp)
            Spacer(modifier = Modifier.height(50.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                Button(onClick = { navControler.navigate("showList") }) {
                    Text(text = "Get back", fontSize = 20.sp)
                }
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

}