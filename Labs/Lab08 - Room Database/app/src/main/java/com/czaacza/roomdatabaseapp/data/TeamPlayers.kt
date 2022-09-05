package com.czaacza.roomdatabaseapp.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

class TeamPlayers() {
    @Embedded val team: Team? = null
    @Relation(parentColumn = "tid", entityColumn = "team")
    val players : List<Player>? = null 
}