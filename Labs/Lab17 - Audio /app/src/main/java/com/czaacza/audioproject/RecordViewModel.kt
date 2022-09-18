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

class RecordViewModel(application: Application, val activity: Activity, val storageDir: File?) :
    AndroidViewModel(application) {
    val app = application


    var recRunning = false
    lateinit var recFile: File
    lateinit var recorder: AudioRecord
    lateinit var audioData: ByteArray
    var minBufferSize : Int = 0

    init {
        hasPermissions()
    }

    private fun createFile(recFileName: String) {
        try {
            recFile = File(storageDir.toString() + "/" + recFileName)
        } catch (e: IOException) {
            Log.e("DBG", "Can't create audio file $e")
        }
    }

    fun record() {
        viewModelScope.launch(Dispatchers.IO) {
            createFile("test.raw")
            startRecording()
            try {
                val outputStream = FileOutputStream(recFile)
                val bufferedOutputStream = BufferedOutputStream(outputStream)
                val dataOutputStream = DataOutputStream(bufferedOutputStream)

                while (recRunning) {
                    val numofBytes = recorder.read(audioData, 0, minBufferSize)
                    if (numofBytes > 0) {
                        dataOutputStream.write(audioData)
                    }
                }
                recorder.stop()
                dataOutputStream.close()
            } catch (e: IOException) {
                Log.d("DBG", "Recording error $e")
            }
        }
    }

    private fun startRecording() {
        minBufferSize = AudioRecord.getMinBufferSize(
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
            return
        }

        recorder = AudioRecord.Builder().setAudioSource(MediaRecorder.AudioSource.MIC)
            .setAudioFormat(aFormat).setBufferSizeInBytes(minBufferSize)
            .build()

        audioData = ByteArray(minBufferSize)

        recorder.startRecording()
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