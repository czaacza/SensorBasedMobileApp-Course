package com.czaacza.roomdatabaseproject.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Player(
    @ColumnInfo(name = "team") val team: Long,
    @PrimaryKey(autoGenerate = true) val pid: Long,
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "last_name") val lastName: String,
    @ColumnInfo(name = "number") val number: Int
){
    override fun toString(): String {
        return "$firstName $lastName - $number"
    }
}