package com.yhcheon.habithero.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.yhcheon.habithero.data.local.entity.ProfileEntity
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProfileDaoTest {

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
    fun `프로필을_저장하고_조회할_수_있다`() = runTest {
        val profile = ProfileEntity(
            id = 1,
            nickname = "테스터",
            hero = "RUNE",
            tier = "B",
            rank = 0,
            totalFragments = 0,
            xp = 0,
            streak = 0,
            lastOpenDate = "2026-11-11"
        )
        db.profileDao().insert(profile)

        val loaded = db.profileDao().getProfile()
        assertEquals("테스터", loaded?.nickname)
        assertEquals("RUNE", loaded?.hero)
    }

    @Test
    fun `프로필_통계를_업데이트하면_DB에_반영된다`() = runTest {
        // Given: 초기 프로필
        val profile = ProfileEntity(
            id = 1,
            nickname = "테스터",
            hero = "RUNE",
            tier = "B",
            rank = 0,
            totalFragments = 0,
            xp = 0,
            streak = 0,
            lastOpenDate = "2026-11-11"
        )
        db.profileDao().insert(profile)

        // When: 통계 업데이트
        db.profileDao().updateStats(
            fragments = 5,
            xp = 200,
            streak = 3
        )

        // Then: DB에 반영됨
        val loaded = db.profileDao().getProfile()
        assertEquals(5, loaded?.totalFragments)
        assertEquals(200, loaded?.xp)
        assertEquals(3, loaded?.streak)
    }
}
