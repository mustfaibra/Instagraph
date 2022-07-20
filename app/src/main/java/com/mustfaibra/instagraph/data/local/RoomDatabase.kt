package com.mustfaibra.instagraph.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mustfaibra.instagraph.models.Version

// 3/20/2022

@Database(
    entities = [Version::class],
    version = 1, exportSchema = false)
abstract class RoomDatabase : RoomDatabase() {

    /** A function that used to retrieve Room's related dao instance */
    abstract fun getDao(): RoomDao
}