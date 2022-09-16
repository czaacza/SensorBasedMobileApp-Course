package com.czaacza.audioproject

import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.os.Environment
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.PermissionChecker
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.*

class AudioViewModel(application: Application, val activity: Activity, val storageDir: File?) :
    AndroidViewModel(application) {
    val app = application

    var recRunning = false
    lateinit var recFile: File


    init {
        hasPermissions()
    }

    private fun createFile(recFileName: String) {
        try {
            recFile = File(storageDir.toString() + "/" + recFileName)
        } catch (ex: IOException) {
            Log.e("FYI", "Can't create audio file $ex")
        }
    }

    fun startRecording() {
        viewModelScope.launch(Dispatchers.IO) {
            createFile("test.raw")
            val minBufferSize = AudioRecord.getMinBufferSize(
                44100,
                AudioFormat.CHANNEL_OUT_STEREO,
                AudioFormat.ENCODING_PCM_16BIT
            )
            val aFormat =
                AudioFormat.Builder().setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                    .setSampleRate(44100)
                    .setChannelMask(AudioFormat.CHANNEL_OUT_STEREO).build()

            if (ActivityCompat.checkSelfPermission(
                    app,
                    Manifest.permission.RECORD_AUDIO
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return@launch
            }

            val recorder = AudioRecord.Builder().setAudioSource(MediaRecorder.AudioSource.MIC)
                .setAudioFormat(aFormat).setBufferSizeInBytes(minBufferSize)
                .build()

            val audioData = ByteArray(minBufferSize)
            recorder.startRecording()
            Log.d("DBG", "Recording started")

            try {
                val outputStream = FileOutputStream(recFile)
                val bufferedOutputStream = BufferedOutputStream(outputStream)
                val dataOutputStream = DataOutputStream(bufferedOutputStream)

                while (recRunning) {
                    val numofBytes = recorder.read(audioData, 0, minBufferSize)
                    Log.d("DBG", numofBytes.toString())
                    if (numofBytes > 0) {
                        dataOutputStream.write(audioData)
                    }
                }
                Log.d("DBG", "Record stopped")

                recorder.stop()
                dataOutputStream.close()
            } catch (e: IOException) {
                Log.e("FYI", "Recording error $e")
            }
        }
    }


    private fun hasPermissions(): Boolean {
        if (checkSelfPermission(
                app,
                Manifest.permission.RECORD_AUDIO
            ) != PermissionChecker.PERMISSION_GRANTED
        ) {
            Log.d("DBG", "No audio recorder access")
            requestPermissions(
                activity,
                arrayOf(Manifest.permission.RECORD_AUDIO),
                1
            ); return true // assuming that the user grants permission
        }
        return true
    }
}