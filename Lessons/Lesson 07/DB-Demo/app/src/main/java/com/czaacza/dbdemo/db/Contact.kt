package com.czaacza.dbdemo.db

import androidx.room.PrimaryKey

data class Contact(
    @PrimaryKey(autoGenerate = true) val value: String,
    val User: Long,
    val Type: String
)