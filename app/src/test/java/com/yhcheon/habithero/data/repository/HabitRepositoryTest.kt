package com.yhcheon.habithero.data.repository

import com.yhcheon.habithero.data.local.dao.HabitDao
import com.yhcheon.habithero.data.local.entity.HabitEntity
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class HabitRepositoryTest {

    // 테스트용 가짜 DAO (인메모리 리스트 사용)
    private class FakeHabitDao : HabitDao {
        private val habits = mutableListOf<HabitEntity>()
        override suspend fun insert(habit: HabitEntity) {
            habits.add(habit.copy(id = habits.size + 1L))
        }
        override suspend fun getAllHabits(): List<HabitEntity> = habits.toList()
        override suspend fun update(habit: HabitEntity) {
            val idx = habits.indexOfFirst { it.id == habit.id }
            if (idx >= 0) habits[idx] = habit
        }
        override suspend fun delete(habit: HabitEntity) {
            habits.removeAll { it.id == habit.id }
        }
    }

    @Test
    fun `습관을_저장하면_Repository에서_조회된다`() = runTest {
        val fakeDao = FakeHabitDao()
        val repository = HabitRepository(fakeDao)

        repository.addHabit("물 2L 마시기", 40)
        val habits = repository.getAllHabits()

        assertEquals(1, habits.size)
        assertEquals("물 2L 마시기", habits[0].name)
        assertEquals(40, habits[0].rewardXp)
    }
}
