package com.yhcheon.habithero.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.yhcheon.habithero.data.local.entity.HabitEntity

@Dao
interface HabitDao {
    @Insert
    suspend fun insert(habit: HabitEntity)

    @Query("SELECT * FROM habits")
    suspend fun getAllHabits(): List<HabitEntity>

    @Update
    suspend fun update(habit: HabitEntity)

    @Delete
    suspend fun delete(habit: HabitEntity)
}
