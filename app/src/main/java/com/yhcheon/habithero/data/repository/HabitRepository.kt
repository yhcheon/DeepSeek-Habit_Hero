package com.yhcheon.habithero.data.repository

import com.yhcheon.habithero.data.local.dao.HabitDao
import com.yhcheon.habithero.data.local.entity.HabitEntity

/**
 * 습관 데이터 Repository
 * - Task 20: XP 폐지에 따라 addHabit(name) 시그니처로 단순화
 * - 내부적으로 rewardXp = 0 저장 (DB 필드는 마이그레이션 회피 위해 유지)
 */
class HabitRepository(
    private val habitDao: HabitDao
) {
    /**
     * 새 습관을 추가합니다. (XP 미사용)
     * @param name 습관 이름
     */
    suspend fun addHabit(name: String) {
        val habit = HabitEntity(
            profileId = 1L,
            name = name,
            cycle = "DAILY",
            rewardXp = 0
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
