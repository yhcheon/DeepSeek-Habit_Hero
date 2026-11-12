package com.yhcheon.habithero.data.repository

import com.yhcheon.habithero.data.local.dao.ProfileDao
import com.yhcheon.habithero.data.local.entity.ProfileEntity
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class ProfileRepositoryTest {

    // 테스트용 가짜 DAO
    private class FakeProfileDao : ProfileDao {
        private var profile: ProfileEntity? = null
        override suspend fun insert(profile: ProfileEntity) {
            this.profile = profile
        }
        override suspend fun getProfile(): ProfileEntity? = profile
    }

    @Test
    fun `프로필을_저장하면_Repository에서_조회된다`() = runTest {
        val fakeDao = FakeProfileDao()
        val repository = ProfileRepository(fakeDao)

        repository.saveProfile("테스터", "RUNE")
        val loaded = repository.getProfile()

        assertEquals("테스터", loaded?.nickname)
        assertEquals("RUNE", loaded?.hero)
        assertEquals(0, loaded?.totalFragments)  // 초기 조각 0
        assertEquals("B", loaded?.tier)          // 초기 등급 B
    }
}
