package com.yhcheon.habithero.data.repository

import com.yhcheon.habithero.data.local.dao.ProfileDao
import com.yhcheon.habithero.data.local.entity.ProfileEntity
import java.time.LocalDate

/**
 * 프로필(아바타) 데이터 Repository
 * - UseCase가 직접 DAO에 접근하지 않고, 이 Repository를 통해 데이터에 접근합니다.
 * - 단일 사용자 로컬 앱이므로 id는 1로 고정됩니다.
 */
class ProfileRepository(
    private val profileDao: ProfileDao
) {
    /**
     * 프로필을 저장합니다. (신규 유저 온보딩용)
     * - 초기 등급: B, Rank 0, 조각 0, XP 0, 스트릭 0
     */
    suspend fun saveProfile(nickname: String, hero: String) {
        val profile = ProfileEntity(
            id = 1L,
            nickname = nickname,
            hero = hero,          // "RUNE" 또는 "ARIA"
            tier = "B",
            rank = 0,
            totalFragments = 0,
            xp = 0,
            streak = 0,
            lastOpenDate = LocalDate.now().toString()
        )
        profileDao.insert(profile)
    }

    /**
     * 프로필을 조회합니다. (없으면 null)
     */
    suspend fun getProfile(): ProfileEntity? {
        return profileDao.getProfile()
    }
}
