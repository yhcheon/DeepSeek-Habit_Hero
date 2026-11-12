package com.yhcheon.habithero.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.yhcheon.habithero.data.local.entity.CheckInEntity
import com.yhcheon.habithero.data.local.entity.HabitEntity
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate

@RunWith(AndroidJUnit4::class)
class CheckInDaoTest {

    private lateinit var db: HabitHeroDatabase

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            HabitHeroDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `오늘_완료한_습관_ID_목록을_조회한다`() = runTest {
        // Given: 습관 2개 추가
        val today = LocalDate.now().toString()
        db.habitDao().insert(HabitEntity(profileId = 1, name = "물 마시기", cycle = "DAILY", rewardXp = 40))
        db.habitDao().insert(HabitEntity(profileId = 1, name = "독서", cycle = "DAILY", rewardXp = 40))
        val habits = db.habitDao().getAllHabits()

        // When: 습관 1번만 오늘 완료
        db.checkInDao().insert(
            CheckInEntity(
                habitId = habits[0].id,
                date = today,
                completedAt = System.currentTimeMillis(),
                streakAtTime = 1
            )
        )

        // Then: 오늘 완료한 습관 ID 목록에 1개만
        val completedIds = db.checkInDao().getTodayCompletedHabitIds(today)
        assertEquals(1, completedIds.size)
        assertTrue(completedIds.contains(habits[0].id))
    }

    @Test
    fun `오늘_완료한_습관_개수를_조회한다`() = runTest {
        val today = LocalDate.now().toString()
        db.habitDao().insert(HabitEntity(profileId = 1, name = "물 마시기", cycle = "DAILY", rewardXp = 40))
        val habits = db.habitDao().getAllHabits()

        db.checkInDao().insert(
            CheckInEntity(
                habitId = habits[0].id,
                date = today,
                completedAt = System.currentTimeMillis(),
                streakAtTime = 1
            )
        )

        val count = db.checkInDao().getTodayCheckInCount(today)
        assertEquals(1, count)
    }
}
