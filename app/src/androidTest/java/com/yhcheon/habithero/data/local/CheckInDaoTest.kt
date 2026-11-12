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
        val today = LocalDate.now().toString()
        db.habitDao().insert(HabitEntity(profileId = 1, name = "물 마시기", cycle = "DAILY"))
        db.habitDao().insert(HabitEntity(profileId = 1, name = "독서", cycle = "DAILY"))
        val habits = db.habitDao().getAllHabits()

        db.checkInDao().insert(
            CheckInEntity(
                habitId = habits[0].id,
                date = today,
                completedAt = System.currentTimeMillis(),
                streakAtTime = 1
            )
        )

        val completedIds = db.checkInDao().getTodayCompletedHabitIds(today)
        assertEquals(1, completedIds.size)
        assertTrue(completedIds.contains(habits[0].id))
    }

    @Test
    fun `오늘_완료한_습관_개수를_조회한다`() = runTest {
        val today = LocalDate.now().toString()
        db.habitDao().insert(HabitEntity(profileId = 1, name = "물 마시기", cycle = "DAILY"))
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

    @Test
    fun `습관별_체크인_날짜_목록을_조회한다`() = runTest {
        db.habitDao().insert(HabitEntity(profileId = 1, name = "물 마시기", cycle = "DAILY"))
        db.habitDao().insert(HabitEntity(profileId = 1, name = "독서", cycle = "DAILY"))
        val habits = db.habitDao().getAllHabits()
        val habitId = habits[0].id

        db.checkInDao().insert(CheckInEntity(habitId = habitId, date = "2026-11-10", completedAt = 0, streakAtTime = 1))
        db.checkInDao().insert(CheckInEntity(habitId = habitId, date = "2026-11-11", completedAt = 0, streakAtTime = 2))
        db.checkInDao().insert(CheckInEntity(habitId = habitId, date = "2026-11-12", completedAt = 0, streakAtTime = 3))

        val dates = db.checkInDao().getCheckedDatesByHabit(habitId)

        assertEquals(3, dates.size)
        assertTrue(dates.contains("2026-11-10"))
        assertTrue(dates.contains("2026-11-11"))
        assertTrue(dates.contains("2026-11-12"))
    }
}
