package com.yhcheon.habithero.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.yhcheon.habithero.data.local.entity.HabitEntity
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HabitDaoTest {

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
    fun `습관을_추가하면_DB에_저장된다`() = runTest {
        db.habitDao().insert(
            HabitEntity(profileId = 1, name = "물 마시기", cycle = "DAILY", rewardXp = 40)
        )
        val habits = db.habitDao().getAllHabits()
        assertEquals(1, habits.size)
        assertEquals("물 마시기", habits[0].name)
    }

    @Test
    fun `습관을_수정하면_DB에_반영된다`() = runTest {
        db.habitDao().insert(
            HabitEntity(profileId = 1, name = "물 마시기", cycle = "DAILY", rewardXp = 40)
        )
        val habits = db.habitDao().getAllHabits()
        val target = habits[0]

        val updated = target.copy(name = "물 2L 마시기", rewardXp = 50)
        db.habitDao().update(updated)

        val reloaded = db.habitDao().getAllHabits()
        assertEquals("물 2L 마시기", reloaded[0].name)
        assertEquals(50, reloaded[0].rewardXp)
    }

    @Test
    fun `습관을_삭제하면_DB에서_제거된다`() = runTest {
        db.habitDao().insert(
            HabitEntity(profileId = 1, name = "물 마시기", cycle = "DAILY", rewardXp = 40)
        )
        db.habitDao().insert(
            HabitEntity(profileId = 1, name = "독서", cycle = "DAILY", rewardXp = 40)
        )
        val habits = db.habitDao().getAllHabits()
        assertEquals(2, habits.size)

        db.habitDao().delete(habits[0])

        val reloaded = db.habitDao().getAllHabits()
        assertEquals(1, reloaded.size)
        assertEquals("독서", reloaded[0].name)
    }
}
