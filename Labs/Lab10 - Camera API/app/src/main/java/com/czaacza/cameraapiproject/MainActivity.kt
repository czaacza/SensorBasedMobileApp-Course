package com.czaacza.cameraapiproject

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.content.FileProvider
import java.io.File


class MainActivity : ComponentActivity() {

    val PHOTO_NAME = "temp_foto"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val imgFile: File = createFile(PHOTO_NAME)
        val imgUri: Uri = getImgUri(imgFile)
        val imgAbsolutePath = imgFile.absolutePath

        setContent {
            ShowImage(imgUri, imgAbsolutePath)
        }
    }

    fun createFile(fileName: String): File {
        val filePath = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", filePath)
    }

    fun getImgUri(imgFile: File): Uri {
        val photoURI: Uri =
            FileProvider.getUriForFile(
                this, "com.czaacza.cameraapiproject.fileprovider", imgFile
            )
        return photoURI
    }
}


@Composable
fun ShowImage(imgUri : Uri, imgFileAbsolutePath: String) {

    var result by remember { mutableStateOf<Bitmap?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
        if(it){
            result = BitmapFactory.decodeFile(imgFileAbsolutePath)
        } else {
            Log.d("ERROR", "Sorry, I was not able to take a picture.")
        }
    }

    Column() {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(onClick = {
                launcher.launch(imgUri)
            }) {
                Text("Take a photo")
            }
        }
        result?.let { image ->
            Image(image.asImageBitmap(), null, modifier = Modifier.fillMaxWidth())
        }
    }
}
