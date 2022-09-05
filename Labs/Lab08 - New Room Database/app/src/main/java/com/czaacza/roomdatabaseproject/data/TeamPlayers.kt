package com.czaacza.roomdatabaseproject.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

class TeamPlayers() {
    @Embedded var team: Team? = null
    @Relation(parentColumn = "tid", entityColumn = "team")
    var players : List<Player>? = null
}