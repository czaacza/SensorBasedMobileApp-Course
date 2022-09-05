package com.example.db_demo.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val cid: Long,
    val contactInfo:String
)
