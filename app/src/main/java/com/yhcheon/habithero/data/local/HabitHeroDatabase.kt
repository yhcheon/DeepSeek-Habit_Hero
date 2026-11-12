package com.yhcheon.habithero.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yhcheon.habithero.data.local.dao.CheckInDao
import com.yhcheon.habithero.data.local.dao.HabitDao
import com.yhcheon.habithero.data.local.dao.ProfileDao
import com.yhcheon.habithero.data.local.entity.CheckInEntity
import com.yhcheon.habithero.data.local.entity.HabitEntity
import com.yhcheon.habithero.data.local.entity.ProfileEntity

@Database(
    entities = [HabitEntity::class, ProfileEntity::class, CheckInEntity::class],
    version = 5,
    exportSchema = false
)
abstract class HabitHeroDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao
    abstract fun profileDao(): ProfileDao
    abstract fun checkInDao(): CheckInDao

    companion object {
        @Volatile
        private var INSTANCE: HabitHeroDatabase? = null

        fun getInstance(context: Context): HabitHeroDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HabitHeroDatabase::class.java,
                    "habit_hero.db"
                )
                .fallbackToDestructiveMigration(dropAllTables = true)
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
