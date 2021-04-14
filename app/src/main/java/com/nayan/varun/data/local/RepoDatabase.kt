package com.nayan.varun.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.nayan.varun.data.local.converters.ObjectConverter
import com.nayan.varun.data.model.Repo

@Database(entities = [(Repo::class)], version = 1, exportSchema = false)
@TypeConverters((ObjectConverter::class))
abstract class RepoDatabase: RoomDatabase() {
    abstract fun repoDao(): RepoDao
}