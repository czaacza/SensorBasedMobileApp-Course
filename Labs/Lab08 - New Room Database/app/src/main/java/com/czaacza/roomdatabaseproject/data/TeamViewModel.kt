package com.czaacza.roomdatabaseproject.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.launch

class TeamViewModel(application: Application) : AndroidViewModel(application) {
    val teamDB: TeamDB? = TeamDB.get(application)

    fun getAllTeams(): LiveData<List<Team>>? = teamDB?.teamDao()?.getAll()

    fun insert(team : Team){
        viewModelScope.launch {
            teamDB?.teamDao()?.insert(team)
        }
    }

    fun update(team : Team){
        viewModelScope.launch {
            teamDB?.teamDao()?.update(team)
        }
    }

    fun delete(team : Team){
        viewModelScope.launch {
            teamDB?.teamDao()?.delete(team)
        }
    }

//    fun getDetails(team : Team){
//        viewModelScope.launch {
//            teamDB?.playerDao()?.getPlayersByTeamID(team.tid)
//        }
//    }
}