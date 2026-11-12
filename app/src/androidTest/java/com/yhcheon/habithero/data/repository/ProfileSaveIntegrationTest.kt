package com.yhcheon.habithero.data.repository

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.yhcheon.habithero.data.local.HabitHeroDatabase
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * 온보딩 → DB 저장 통합 테스트
 * - saveProfile() 호출 시 실제 Room DB에 저장되고 조회되는지 검증
 */
@RunWith(AndroidJUnit4::class)
class ProfileSaveIntegrationTest {

    private lateinit var db: HabitHeroDatabase
    private lateinit var profileRepo: ProfileRepository

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            HabitHeroDatabase::class.java
        ).allowMainThreadQueries().build()
        profileRepo = ProfileRepository(db.profileDao())
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `saveProfile_후_getProfile로_조회된다`() = runTest {
        // Given: 프로필 저장
        profileRepo.saveProfile(nickname = "테스터", hero = "RUNE")

        // When: 프로필 조회
        val loaded = profileRepo.getProfile()

        // Then: 정확히 저장됨
        assertNotNull(loaded)
        assertEquals("테스터", loaded?.nickname)
        assertEquals("RUNE", loaded?.hero)
        assertEquals("B", loaded?.tier)
        assertEquals(0, loaded?.totalFragments)
    }
}
