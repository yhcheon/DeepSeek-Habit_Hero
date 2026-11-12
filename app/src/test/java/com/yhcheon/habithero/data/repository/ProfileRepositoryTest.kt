package com.yhcheon.habithero.data.repository

import com.yhcheon.habithero.data.local.dao.ProfileDao
import com.yhcheon.habithero.data.local.entity.ProfileEntity
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import java.time.LocalDate

class ProfileRepositoryTest {

    // 테스트용 가짜 DAO
    private class FakeProfileDao : ProfileDao {
        private var profile: ProfileEntity? = null
        var insertCallCount = 0
        var updateNicknameCallCount = 0
        var updateHeroCallCount = 0
        var lastOpenDateCallCount = 0

        override suspend fun insert(profile: ProfileEntity) {
            this.profile = profile
            insertCallCount++
        }
        override suspend fun getProfile(): ProfileEntity? = profile
        override suspend fun updateStats(fragments: Int, xp: Int, streak: Int) {
            profile = profile?.copy(
                totalFragments = fragments,
                xp = xp,
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

    // ─────────────────────────────────────────────
    // [Task 19-2] markTodayOpened 테스트
    // ─────────────────────────────────────────────

    @Test
    fun `markTodayOpened_는_lastOpenDate를_오늘로_갱신한다`() = runTest {
        val fakeDao = FakeProfileDao()
        val repository = ProfileRepository(fakeDao)
        repository.saveProfile("닉", "RUNE")
        // saveProfile에서 lastOpenDate는 오늘로 설정되지만, 검증을 위해 과거로 되돌림
        fakeDao.updateLastOpenDate("2026-11-01")

        val today = LocalDate.of(2026, 11, 12)
        repository.markTodayOpened(today)

        assertEquals("2026-11-12", repository.getProfile()?.lastOpenDate)
        assertEquals(2, fakeDao.lastOpenDateCallCount) // 과거 복원 1 + markTodayOpened 1
    }

    @Test
    fun `markTodayOpened_는_프로필이_없으면_DAO를_호출하지_않는다`() = runTest {
        val fakeDao = FakeProfileDao()
        val repository = ProfileRepository(fakeDao)

        repository.markTodayOpened(LocalDate.of(2026, 11, 12))

        assertNull(repository.getProfile())
        assertEquals(0, fakeDao.lastOpenDateCallCount)
    }
}
