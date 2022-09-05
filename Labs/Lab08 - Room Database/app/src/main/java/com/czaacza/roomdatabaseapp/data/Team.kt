package com.czaacza.roomdatabaseapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Team(
    @PrimaryKey(autoGenerate = true) val tid: Long,
    @ColumnInfo(name = "name") val name: String
) {
    override fun toString(): String = "$tid $name"
}