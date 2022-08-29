package com.czaacza.coroutinesintro

import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.czaacza.coroutinesintro.databinding.ActivityMainBinding
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    val TAG = "Main Activity"

    class Account {
        private var amount: Double = 0.0
        suspend fun deposit_coroutine(amount: Double) {
            val x = this.amount
            delay(1) // simulates processing time
            this.amount = x + amount
        }

        fun saldo(): Double = amount
    }

    /* Approximate measurement of the given block's execution time */
    fun withTimeMeasurement(title: String, isActive: Boolean = true, code: () -> Unit) {
        if (!isActive) return
        val timeStart = System.currentTimeMillis()
        code()
        val timeEnd = System.currentTimeMillis()
        println("operation in '$title' took ${(timeEnd - timeStart)} ms")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tili2 = Account();

        withTimeMeasurement("Single coroutine deposit 1000 times") {
            runBlocking {
                launch {
                    for (i in 1..1000)
                        tili2.deposit_coroutine(0.0)
                    Log.d(TAG, "Here i am")
                }
            }
            findViewById<TextView>(R.id.textView).text = "Saldo2: ${tili2.saldo()}"
        }

        withTimeMeasurement("Two coroutines together", isActive = true) {
            runBlocking {
                val deposit1 = async {
                    for (i in 1..1000) tili2.deposit_coroutine(1.0)
                }
                deposit1.await()
                val deposit2 = async {
                    for (i in 1..1000) tili2.deposit_coroutine(1.0)
                }
                deposit2.await()

                findViewById<TextView>(R.id.textView).text = "Saldo2: ${tili2.saldo()}"
            }
        }
    }
}