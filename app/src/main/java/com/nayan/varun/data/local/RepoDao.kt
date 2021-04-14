package com.nayan.varun.data.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.nayan.varun.data.model.Repo

@Dao
interface RepoDao {

    @Query("SELECT * FROM repo")
    fun getRepos(): List<Repo>

    @JvmSuppressWildcards
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepos(users: List<Repo>)

}