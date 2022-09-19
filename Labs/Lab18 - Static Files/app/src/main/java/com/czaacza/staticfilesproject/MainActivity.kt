package com.czaacza.staticfilesproject

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.czaacza.staticfilesproject.ui.theme.StaticFilesProjectTheme


class MainActivity : ComponentActivity() {

    val directoryUriLiveData = MutableLiveData<Uri>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
        startActivityForResult(intent, 101)


        setContent {
            StaticFilesProjectTheme {
                ShowContent(directoryUriLiveData, application)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 101
            && resultCode == Activity.RESULT_OK
        ) {
            // The result data contains a URI for the document or directory that
            // the user selected.
            data?.data?.also { uri ->
                // Perform operations on the document using its URI.
                directoryUriLiveData.value = uri
            }
        }
    }
}


@Composable
fun ShowContent(directoryUriLiveData: LiveData<Uri>, context: Context) {
    val directoryUri = directoryUriLiveData.observeAsState()
    if (directoryUri == null || directoryUri.value == null) {
        return
    }

    val documentsTree = DocumentFile.fromTreeUri(context, directoryUri.value!!)
    val childDocuments = documentsTree?.listFiles()

    childDocuments!!.forEach {
        Text(text = it.name.toString())
    }
}

//fun tree(file: File): File? {
//    // if file is null
//    if(file.isDirectory){
//        tree(file)
//    }
//
//
//}