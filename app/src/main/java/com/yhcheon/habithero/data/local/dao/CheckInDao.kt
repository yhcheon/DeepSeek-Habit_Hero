package com.yhcheon.habithero.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.yhcheon.habithero.data.local.entity.CheckInEntity

@Dao
interface CheckInDao {
    @Insert
    suspend fun insert(checkIn: CheckInEntity)

    @Query("SELECT COUNT(*) FROM check_ins WHERE date = :date")
    suspend fun getTodayCheckInCount(date: String): Int

    @Query("SELECT habitId FROM check_ins WHERE date = :date")
    suspend fun getTodayCompletedHabitIds(date: String): List<Long>

    // ─── 추가: 언체크 시 오늘의 체크인 삭제 ───
    @Query("DELETE FROM check_ins WHERE habitId = :habitId AND date = :date")
    suspend fun deleteByHabitAndDate(habitId: Long, date: String)
}
