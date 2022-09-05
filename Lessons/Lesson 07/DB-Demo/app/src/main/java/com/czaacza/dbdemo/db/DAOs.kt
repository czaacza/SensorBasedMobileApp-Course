package com.example.db_demo.db;

import androidx.lifecycle.LiveData;
import androidx.room.*


@Dao
interface BaseDao<T> {

    fun getAll(): LiveData<List<T>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: T): Long

    @Update
    suspend fun update(item: T)

    @Delete
    suspend fun delete(item: T)
}

@Dao
interface UserDao: BaseDao<User> {
    @Query("SELECT * FROM user")
    override fun getAll(): LiveData<List<User>>
}

@Dao
interface ContactDao: BaseDao<Contact> {
    @Query("select * from contact")
    override fun getAll(): LiveData<List<Contact>>
}

