package com.yhcheon.habithero.data.repository

import com.yhcheon.habithero.data.local.dao.ProfileDao
import com.yhcheon.habithero.data.local.entity.ProfileEntity
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import java.time.LocalDate

class ProfileRepositoryTest {

    private class FakeProfileDao : ProfileDao {
        private var profile: ProfileEntity? = null
        var insertCallCount = 0
        var updateNicknameCallCount = 0
        var updateHeroCallCount = 0
        var lastOpenDateCallCount = 0
        var updateTierRankCallCount = 0

        override suspend fun insert(profile: ProfileEntity) {
            this.profile = profile
            insertCallCount++
        }
        override suspend fun getProfile(): ProfileEntity? = profile

        // Task 21: updateStats(fragments, streak) 시그니처로 변경 (xp 제거)
        override suspend fun updateStats(fragments: Int, streak: Int) {
            profile = profile?.copy(
                totalFragments = fragments,
                streak = streak
            )
        }

        override suspend fun updateNickname(nickname: String) {
            profile = profile?.copy(nickname = nickname)
            updateNicknameCallCount++
        }
        override suspend fun updateHero(hero: String) {
            profile = profile?.copy(hero = hero)
            updateHeroCallCount++
        }
        override suspend fun updateLastOpenDate(date: String) {
            profile = profile?.copy(lastOpenDate = date)
            lastOpenDateCallCount++
        }
        override suspend fun updateTierRank(tier: String, rank: Int) {
            profile = profile?.copy(tier = tier, rank = rank)
            updateTierRankCallCount++
        }
    }

    @Test
    fun `프로필을_저장하면_Repository에서_조회된다`() = runTest {
        val fakeDao = FakeProfileDao()
        val repository = ProfileRepository(fakeDao)

        repository.saveProfile("테스터", "RUNE")
        val loaded = repository.getProfile()

        assertEquals("테스터", loaded?.nickname)
        assertEquals("RUNE", loaded?.hero)
        assertEquals(0, loaded?.totalFragments)
        assertEquals("B", loaded?.tier)
    }

    @Test
    fun `updateNickname_은_기존_프로필의_닉네임을_변경한다`() = runTest {
        val fakeDao = FakeProfileDao()
        val repository = ProfileRepository(fakeDao)
        repository.saveProfile("기존닉", "RUNE")

        repository.updateNickname("새닉네임")

        val loaded = repository.getProfile()
        assertEquals("새닉네임", loaded?.nickname)
        assertEquals("RUNE", loaded?.hero)
        assertEquals(1, fakeDao.updateNicknameCallCount)
        assertEquals(0, fakeDao.updateHeroCallCount)
    }

    @Test
    fun `updateHero_는_기존_프로필의_히어로를_변경한다`() = runTest {
        val fakeDao = FakeProfileDao()
        val repository = ProfileRepository(fakeDao)
        repository.saveProfile("닉", "RUNE")

        repository.updateHero("ARIA")

        val loaded = repository.getProfile()
        assertEquals("ARIA", loaded?.hero)
        assertEquals("닉", loaded?.nickname)
        assertEquals(1, fakeDao.updateHeroCallCount)
        assertEquals(0, fakeDao.updateNicknameCallCount)
    }

    @Test
    fun `updateNickname_은_1자_닉네임도_허용한다`() = runTest {
        val fakeDao = FakeProfileDao()
        val repository = ProfileRepository(fakeDao)
        repository.saveProfile("기존", "RUNE")

        repository.updateNickname("A")

        assertEquals("A", repository.getProfile()?.nickname)
    }

    @Test
    fun `updateNickname_은_20자_닉네임도_허용한다`() = runTest {
        val fakeDao = FakeProfileDao()
        val repository = ProfileRepository(fakeDao)
        repository.saveProfile("기존", "RUNE")
        val twenty = "가".repeat(20)

        repository.updateNickname(twenty)

        assertEquals(twenty, repository.getProfile()?.nickname)
    }

    @Test
    fun `updateNickname_은_빈_문자열이면_DAO를_호출하지_않는다`() = runTest {
        val fakeDao = FakeProfileDao()
        val repository = ProfileRepository(fakeDao)
        repository.saveProfile("기존", "RUNE")

        repository.updateNickname("")

        assertEquals("기존", repository.getProfile()?.nickname)
        assertEquals(0, fakeDao.updateNicknameCallCount)
    }

    @Test
    fun `updateHero_는_빈_문자열이면_DAO를_호출하지_않는다`() = runTest {
        val fakeDao = FakeProfileDao()
        val repository = ProfileRepository(fakeDao)
        repository.saveProfile("닉", "RUNE")

        repository.updateHero("")

        assertEquals("RUNE", repository.getProfile()?.hero)
        assertEquals(0, fakeDao.updateHeroCallCount)
    }

    @Test
    fun `updateNickname_은_프로필이_없으면_DAO를_호출하지_않는다`() = runTest {
        val fakeDao = FakeProfileDao()
        val repository = ProfileRepository(fakeDao)

        repository.updateNickname("아무나")

        assertNull(repository.getProfile())
        assertEquals(0, fakeDao.updateNicknameCallCount)
    }

    @Test
    fun `updateHero_는_프로필이_없으면_DAO를_호출하지_않는다`() = runTest {
        val fakeDao = FakeProfileDao()
        val repository = ProfileRepository(fakeDao)

        repository.updateHero("ARIA")

        assertNull(repository.getProfile())
        assertEquals(0, fakeDao.updateHeroCallCount)
    }

    @Test
    fun `markTodayOpened_는_lastOpenDate를_오늘로_갱신한다`() = runTest {
        val fakeDao = FakeProfileDao()
        val repository = ProfileRepository(fakeDao)
        repository.saveProfile("닉", "RUNE")
        fakeDao.updateLastOpenDate("2026-11-01")

        val today = LocalDate.of(2026, 11, 12)
        repository.markTodayOpened(today)

        assertEquals("2026-11-12", repository.getProfile()?.lastOpenDate)
        assertEquals(2, fakeDao.lastOpenDateCallCount)
    }

    @Test
    fun `markTodayOpened_는_프로필이_없으면_DAO를_호출하지_않는다`() = runTest {
        val fakeDao = FakeProfileDao()
        val repository = ProfileRepository(fakeDao)

        repository.markTodayOpened(LocalDate.of(2026, 11, 12))

        assertNull(repository.getProfile())
        assertEquals(0, fakeDao.lastOpenDateCallCount)
    }

    // ─────────────────────────────────────────────
    // [Task 20-5c] updateTierRank 위임 테스트
    // ─────────────────────────────────────────────

    @Test
    fun `updateTierRank_는_기존_프로필의_tier와_rank를_변경한다`() = runTest {
        val fakeDao = FakeProfileDao()
        val repository = ProfileRepository(fakeDao)
        repository.saveProfile("닉", "RUNE")
        assertEquals("B", repository.getProfile()?.tier)
        assertEquals(0, repository.getProfile()?.rank)

        repository.updateTierRank("A", 0)

        assertEquals("A", repository.getProfile()?.tier)
        assertEquals(0, repository.getProfile()?.rank)
        assertEquals(1, fakeDao.updateTierRankCallCount)
    }
}
