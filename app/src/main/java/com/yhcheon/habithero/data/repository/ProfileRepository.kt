package com.yhcheon.habithero.data.repository

import com.yhcheon.habithero.data.local.dao.ProfileDao
import com.yhcheon.habithero.data.local.entity.ProfileEntity
import java.time.LocalDate

class ProfileRepository(
    private val profileDao: ProfileDao
) {
    suspend fun saveProfile(nickname: String, hero: String) {
        val profile = ProfileEntity(
            id = 1L,
            nickname = nickname,
            hero = hero,
            tier = "B",
            rank = 0,
            totalFragments = 0,
            xp = 0,
            streak = 0,
            lastOpenDate = LocalDate.now().toString()
        )
        profileDao.insert(profile)
    }

    suspend fun getProfile(): ProfileEntity? {
        return profileDao.getProfile()
    }

    suspend fun updateNickname(nickname: String) {
        if (nickname.isBlank()) return
        if (profileDao.getProfile() == null) return
        profileDao.updateNickname(nickname)
    }

    suspend fun updateHero(hero: String) {
        if (hero.isBlank()) return
        if (profileDao.getProfile() == null) return
        profileDao.updateHero(hero)
    }

    suspend fun markTodayOpened(today: LocalDate = LocalDate.now()) {
        if (profileDao.getProfile() == null) return
        profileDao.updateLastOpenDate(today.toString())
    }

    /**
     * Tier/Rank 갱신 (Task 20)
     * - 프로필 없으면 무시
     */
    suspend fun updateTierRank(tier: String, rank: Int) {
        if (profileDao.getProfile() == null) return
        profileDao.updateTierRank(tier, rank)
    }
}
