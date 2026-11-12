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

    // ─────────────────────────────────────────────
    // [Task 18-1] updateNickname / updateHero 테스트
    // ─────────────────────────────────────────────

    @Test
    fun `updateNickname_은_닉네임만_변경하고_나머지_필드는_유지한다`() = runTest {
        // Given: 초기 프로필 (모든 필드에 서로 다른 값)
        db.profileDao().insert(
            ProfileEntity(
                id = 1,
                nickname = "기존닉",
                hero = "RUNE",
                tier = "B",
                rank = 5,
                totalFragments = 3,
                xp = 120,
                streak = 2,
                lastOpenDate = "2026-11-12"
            )
        )

        // When
        db.profileDao().updateNickname("새닉네임")

        // Then: 닉네임만 변경, 나머지 유지
        val updated = db.profileDao().getProfile()
        assertEquals("새닉네임", updated?.nickname)
        assertEquals("RUNE", updated?.hero)
        assertEquals(5, updated?.rank)
        assertEquals(3, updated?.totalFragments)
        assertEquals(120, updated?.xp)
        assertEquals(2, updated?.streak)
    }

    @Test
    fun `updateHero_은_히어로만_변경하고_나머지_필드는_유지한다`() = runTest {
        // Given
        db.profileDao().insert(
            ProfileEntity(
                id = 1,
                nickname = "닉",
                hero = "RUNE",
                tier = "B",
                rank = 0,
                totalFragments = 0,
                xp = 0,
                streak = 0,
                lastOpenDate = "2026-11-12"
            )
        )

        // When
        db.profileDao().updateHero("ARIA")

        // Then: 히어로만 변경, 닉네임 유지
        val updated = db.profileDao().getProfile()
        assertEquals("ARIA", updated?.hero)
        assertEquals("닉", updated?.nickname)
    }

    @Test
    fun `updateNickname_은_프로필이_없어도_예외를_던지지_않는다`() = runTest {
        // Given: 프로필 없음 (insert 안 함)

        // When / Then: 예외 없이 통과
        db.profileDao().updateNickname("아무나")
    }
}
