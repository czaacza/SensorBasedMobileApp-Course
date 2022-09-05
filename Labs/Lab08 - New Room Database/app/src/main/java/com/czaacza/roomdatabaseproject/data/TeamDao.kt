package com.czaacza.roomdatabaseproject.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TeamDao {
    //    GET ALL THE TEAMS
    @Query("SELECT * FROM team")
    fun getAll(): LiveData<List<Team>>

    //    GET THE TEAM WITH PLAYERS OF GIVEN ID
    @Query("SELECT * FROM team WHERE team.tid = :teamid")
    fun getTeamByID(teamid: Long): LiveData<Team>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(team: Team): Long

    @Update
    suspend fun update(team: Team)

    @Delete
    suspend fun delete(team: Team)
}