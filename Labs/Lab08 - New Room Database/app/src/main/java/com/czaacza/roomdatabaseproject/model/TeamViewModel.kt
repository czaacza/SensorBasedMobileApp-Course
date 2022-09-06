package com.czaacza.roomdatabaseproject.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.czaacza.roomdatabaseproject.data.Player
import com.czaacza.roomdatabaseproject.data.Team
import com.czaacza.roomdatabaseproject.data.TeamDB
import kotlinx.coroutines.launch

class TeamViewModel(application: Application) : AndroidViewModel(application) {
    val teamDB: TeamDB? = TeamDB.get(application)

    fun getAllTeams(): LiveData<List<Team>>? = teamDB?.teamDao()?.getAll()

    fun getTeamById(teamId: Long) : LiveData<Team>? = teamDB?.teamDao()?.getTeamByID(teamId)

    fun insert(team : Team){
        viewModelScope.launch {
            teamDB?.teamDao()?.insert(team)
        }
    }

    fun getPlayersByTeamId(teamId: Long): LiveData<List<Player>> {
        return teamDB?.playerDao()!!.getPlayersByTeamID(teamId)
    }

    fun insertPlayer(player: Player){
        viewModelScope.launch {
            teamDB?.playerDao()?.insert(player)
        }
    }
}