package com.czaacza.roomdatabaseapp.data

import androidx.room.Dao
import androidx.room.Query

@Dao
interface PlayerDao {
    @Query("SELECT * FROM player")
    suspend fun getAll()

    @Query("SELECT * FROM player WHERE team = :teamid")
    fun getPlayersByTeamID(teamid : Long)
}
