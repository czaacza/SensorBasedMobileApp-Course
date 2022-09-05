package com.czaacza.roomdatabaseproject.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PlayerDao {
    @Query("SELECT * FROM player")
    fun getAll(): LiveData<List<Player>>

    @Query("SELECT * FROM player WHERE team = :teamid")
    fun getPlayersByTeamID(teamid: Long): LiveData<List<Player>>

    @Insert
    suspend fun insert(player: Player) : Long
}
