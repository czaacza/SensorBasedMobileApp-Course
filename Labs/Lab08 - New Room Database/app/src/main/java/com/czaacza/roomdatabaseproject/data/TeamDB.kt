package com.czaacza.roomdatabaseproject.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Team::class, Player::class], version = 1)
abstract class TeamDB : RoomDatabase() {
    abstract fun teamDao(): TeamDao
//    abstract fun playerDao(): PlayerDao

    companion object Instance {
        private var instance: TeamDB? = null

        @Synchronized
        fun get(context: Context): TeamDB? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context,
                    TeamDB::class.java, "team-db"
                ).build()
            }
            return instance
        }
    }
}