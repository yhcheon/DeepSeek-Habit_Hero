package com.yhcheon.habithero.domain.usecase

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.yhcheon.habithero.data.local.HabitHeroDatabase
import com.yhcheon.habithero.data.local.entity.HabitEntity
import com.yhcheon.habithero.data.repository.HabitRepository
import com.yhcheon.habithero.data.repository.ProfileRepository
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * CompleteHabitUseCase 통합 테스트 (Room in-memory DB)
 */
@RunWith(AndroidJUnit4::class)
class CompleteHabitIntegrationTest {

    private lateinit var db: HabitHeroDatabase
    private lateinit var habitRepo: HabitRepository
    private lateinit var profileRepo: ProfileRepository

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            HabitHeroDatabase::class.java
        ).allowMainThreadQueries().build()
        habitRepo = HabitRepository(db.habitDao())
        profileRepo = ProfileRepository(db.profileDao())
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `습관_완료시_결과값이_정상적으로_계산된다`() = runTest {
        // Given: 습관 1개 + 프로필 초기 상태
        val habit = HabitEntity(
            profileId = 1L,
            name = "물 2L 마시기",
            cycle = "DAILY",
            rewardXp = 40
        )
        db.habitDao().insert(habit)
        profileRepo.saveProfile(nickname = "테스터", hero = "RUNE")

        // When: UseCase 실행
        val result = CompleteHabitUseCase.execute(
            profileFragments = 0,
            profileXp = 0,
            profileStreak = 0,
            habitRewardXp = 40,
            heroPassiveXpRate = 0.0,
            todayFragmentCount = 0
        )

        // Then
        assertEquals(1, result.fragmentEarned)
        assertEquals(40, result.xpEarned)
        assertEquals(1, result.newStreak)
    }
}
