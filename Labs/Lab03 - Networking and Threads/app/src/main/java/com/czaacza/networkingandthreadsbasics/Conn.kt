package com.czaacza.networkingandthreadsbasics
import android.os.Handler
import android.util.Log
import java.lang.Exception
import java.net.URL

const val TAG = "ConnTAG"

class Conn(
    val mHandler: Handler,
    val url: URL

) : Runnable {

    override fun run() {
        try {
            val myConn = url.openConnection()
            val istream = myConn.getInputStream()
            val allText = istream.bufferedReader().use { it.readText() }
            val result = StringBuilder()
            result.append(allText)
            val str = result.toString()

            val msg = mHandler.obtainMessage()
            msg.what = 0
            msg.obj = str

            mHandler.sendMessage(msg)
        } catch (e: Exception) {
            Log.d(TAG, "Sorry, I could not get the message from the network service.")
            Log.d(TAG, e.toString())
        }
    }

}