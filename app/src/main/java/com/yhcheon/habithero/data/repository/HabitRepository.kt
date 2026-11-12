package com.yhcheon.habithero.data.repository

import com.yhcheon.habithero.data.local.dao.HabitDao
import com.yhcheon.habithero.data.local.entity.HabitEntity

/**
 * 습관 데이터 Repository
 * - UseCase가 직접 DAO에 접근하지 않고, 이 Repository를 통해 데이터에 접근합니다.
 * - 테스트 시 FakeHabitDao를 주입할 수 있도록 생성자로 Dao를 받습니다.
 */
class HabitRepository(
    private val habitDao: HabitDao
) {
    /**
     * 새 습관을 추가합니다.
     * @param name 습관 이름
     * @param rewardXp 완료 시 지급할 XP
     */
    suspend fun addHabit(name: String, rewardXp: Int) {
        val habit = HabitEntity(
            profileId = 1L,  // 단일 사용자
            name = name,
            cycle = "DAILY",
            rewardXp = rewardXp
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
