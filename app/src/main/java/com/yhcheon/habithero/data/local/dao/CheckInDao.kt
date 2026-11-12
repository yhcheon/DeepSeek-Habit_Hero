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

    @Query("DELETE FROM check_ins WHERE habitId = :habitId AND date = :date")
    suspend fun deleteByHabitAndDate(habitId: Long, date: String)

    /**
     * 특정 습관의 모든 체크인 날짜 조회 (Task 20-5)
     * - 습관별 연속일 계산에 사용
     */
    @Query("SELECT date FROM check_ins WHERE habitId = :habitId")
    suspend fun getCheckedDatesByHabit(habitId: Long): List<String>
}
