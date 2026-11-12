package com.yhcheon.habithero.data.repository

import com.yhcheon.habithero.data.local.dao.HabitDao
import com.yhcheon.habithero.data.local.entity.HabitEntity

/**
 * 습관 데이터 Repository (Task 21: XP 필드 제거)
 */
class HabitRepository(
    private val habitDao: HabitDao
) {
    /**
     * 새 습관을 추가합니다.
     * @param name 습관 이름
     */
    suspend fun addHabit(name: String) {
        val habit = HabitEntity(
            profileId = 1L,
            name = name,
            cycle = "DAILY"
        )
        habitDao.insert(habit)
    }

    /**
     * 모든 습관 목록을 조회합니다.
     */
    suspend fun getAllHabits(): List<HabitEntity> {
        return habitDao.getAllHabits()
    }
}
