package com.czaacza.audioproject

import android.app.Application
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream

class PlayViewModel(application: Application, val storageDir: File?) :
    AndroidViewModel(application) {
    lateinit var inputStream: InputStream
    lateinit var recFile: File
    lateinit var track: AudioTrack
    var minBufferSize = 0
    var playRunning = false


    suspend fun playAudio() {
        readFile("test.raw")
        setupTrack()
        track.play()
        playRunning = true

        var i = 0
        val buffer = ByteArray(minBufferSize)

        try {
            i = inputStream.read(buffer, 0, minBufferSize)

            while (i != -1 && playRunning) {
                track.write(buffer, 0, i)
                i = inputStream.read(buffer, 0, minBufferSize)

            }

        } catch (e: IOException) {
            Log.e("DBG", "Stream read error $e")
        }

        try {
            inputStream.close()
        } catch (e: IOException) {
            Log.e("DBG", "Close error $e")
        }

        track.stop()
        track.release()
        playRunning = false
    }

    private fun setupTrack() {
        val minBufferSize = AudioTrack.getMinBufferSize(
            44100, AudioFormat.CHANNEL_OUT_STEREO,
            AudioFormat.ENCODING_PCM_16BIT
        )
        val aBuilder = AudioTrack.Builder()

        val aAttr = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build()

        val aFormat = AudioFormat.Builder()
            .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
            .setSampleRate(44100)
            .setChannelMask(AudioFormat.CHANNEL_OUT_STEREO)
            .build()

        track = aBuilder.setAudioAttributes(aAttr)
            .setAudioFormat(aFormat)
            .setBufferSizeInBytes(minBufferSize)
            .build()

        track.setVolume(0.2f)
    }

    private fun readFile(recFileName: String) {
        try {
            recFile = File(storageDir.toString() + "/" + recFileName)
            inputStream = FileInputStream(recFile)
        } catch (e: IOException) {
            Log.d("DBG", "Can't find an audio file $e")

        }
    }


}