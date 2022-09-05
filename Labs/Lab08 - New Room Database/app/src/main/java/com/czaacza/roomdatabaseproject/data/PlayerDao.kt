package com.czaacza.roomdatabaseproject.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface PlayerDao {
    @Query("SELECT * FROM player")
    suspend fun getAll(): LiveData<List<Player>>

    @Query("SELECT * FROM player WHERE team = :teamid")
    fun getPlayersByTeamID(teamid: Long): LiveData<List<Team>>
}
