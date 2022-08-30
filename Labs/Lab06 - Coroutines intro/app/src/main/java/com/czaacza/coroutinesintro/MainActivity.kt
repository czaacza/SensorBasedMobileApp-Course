package com.czaacza.coroutinesintro

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class MainActivity : AppCompatActivity() {

    class Account {
        private var amount: Double = 0.0
        val mutex = Mutex()

        suspend fun deposit_coroutine(amount: Double) {
            mutex.withLock {
                val x = this.amount
                delay(1) // simulates processing time
                this.amount = x + amount
            }
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
                }
            }
            findViewById<TextView>(R.id.idSaldo2).text = "Saldo2: ${tili2.saldo()}"
        }

        withTimeMeasurement("Two coroutines together", isActive = true) {
            runBlocking {
                val job1 = launch {
                    for (i in 1..1000) tili2.deposit_coroutine(1.0)
                }
                val job2 = launch {
                    for (i in 1..1000) tili2.deposit_coroutine(1.0)
                }
                joinAll(job1, job2)
                findViewById<TextView>(R.id.idSaldo2).text = "Saldo2: ${tili2.saldo()}"
            }
        }
    }
}