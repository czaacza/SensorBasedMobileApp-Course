package com.czaacza.materialdesignbasics

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material.*
import androidx.compose.material.SnackbarResult.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.czaacza.materialdesignbasics.ui.theme.MaterialDesignBasicsTheme
import kotlinx.coroutines.launch

val CHANNEL_ID: String = "ActualChannel"

class MainActivity : ComponentActivity() {

    @SuppressLint("PrivateResource")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            createNotificationChannel()
            val context : Context = LocalContext.current

            val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(androidx.core.R.drawable.notification_icon_background)
                .setContentTitle("Congratulations!")
                .setContentText("You just pressed the button. I'm impressed!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setVibrate(longArrayOf(1000, 1000))
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .build()

            val scaffoldState = rememberScaffoldState()
            val scope = rememberCoroutineScope()

            var showNextCard by remember { mutableStateOf(false) }
            MaterialDesignBasicsTheme {
                Scaffold(
                    scaffoldState = scaffoldState,
                    topBar = {
                        TopBarDemo()
                    },
                    content = {
                        Column {
                            CardDemo()
                            if (showNextCard == true) {
                                CardDemo()
                            }
                        }
                    },
                    floatingActionButton = {
                        FloatingActionButton(onClick = {
                            NotificationManagerCompat.from(context).notify(123, notification)
                            showNextCard = true;
                            scope.launch {
                                scaffoldState.snackbarHostState.showSnackbar(
                                    getString(R.string.snackbar_msg),
                                    getString(R.string.snackbar_undo),
                                )
                                    .let {
                                        when (it) {
                                            ActionPerformed -> showNextCard = false
                                            Dismissed -> showNextCard = true
                                        }
                                    }
                            }
                        }, backgroundColor = MaterialTheme.colors.primary) {
                            Icon(
                                Icons.Filled.Add,
                                tint = Color.White,
                                contentDescription = "Localized description"
                            )
                        }
                    },
                    floatingActionButtonPosition = FabPosition.Center
                )
            }
        }
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}


@Composable
fun TopBarDemo() {
    TopAppBar(
        title = { Text("Simple TopAppBar") },
        navigationIcon = {
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(Icons.Filled.Menu, contentDescription = null)
            }
        },
        actions = {
            // RowScope here, so these icons will be placed horizontally
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = "Localized description"
                )
            }
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = "Localized description"
                )
            }
        }
    )
}

@Composable
fun CardDemo() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clickable { },
        elevation = 16.dp
    ) {
        Column(
            modifier = Modifier.padding(15.dp)
        ) {
            Text(
                text = "What a superduper card",
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.h5
            )
            Text(
                buildAnnotatedString {
                    append("welcome to ")
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.W900,
                            color = MaterialTheme.colors.secondary
                        )
                    ) {
                        append("Jetpack Compose Playground")
                    }
                }
            )
            Text(
                buildAnnotatedString {
                    append("Now you are in the ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.W900)) {
                        append("Card")
                    }
                    append(" section")
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { /* Do something! */ },
                shape = MaterialTheme.shapes.small.copy(
                    bottomStart = ZeroCornerSize,
                    bottomEnd = ZeroCornerSize,
                    topStart = CornerSize(16.dp),
                    topEnd = ZeroCornerSize,
                )
            ) {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = null,
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text("Like")
            }
        }
    }
}

