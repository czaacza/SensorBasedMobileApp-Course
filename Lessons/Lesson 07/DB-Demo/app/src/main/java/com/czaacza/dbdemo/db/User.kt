package com.czaacza.dbdemo.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val uid: Long,
    val firstname: String,
    val lastname: String
) {
    //constructor, getter and setter are implicit :)
    override fun toString() = "$firstname $lastname ($uid)"
}