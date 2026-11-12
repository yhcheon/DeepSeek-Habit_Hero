package com.yhcheon.habithero.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yhcheon.habithero.data.local.entity.ProfileEntity

@Dao
interface ProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(profile: ProfileEntity)

    @Query("SELECT * FROM profiles WHERE id = 1")
    suspend fun getProfile(): ProfileEntity?

    @Query("""
        UPDATE profiles
        SET totalFragments = :fragments,
            xp = :xp,
            streak = :streak
        WHERE id = 1
    """)
    suspend fun updateStats(fragments: Int, xp: Int, streak: Int)

    @Query("UPDATE profiles SET nickname = :nickname WHERE id = 1")
    suspend fun updateNickname(nickname: String)

    @Query("UPDATE profiles SET hero = :hero WHERE id = 1")
    suspend fun updateHero(hero: String)

    @Query("UPDATE profiles SET lastOpenDate = :date WHERE id = 1")
    suspend fun updateLastOpenDate(date: String)

    /**
     * Tier/Rank 갱신 (Task 20)
     */
    @Query("UPDATE profiles SET tier = :tier, rank = :rank WHERE id = 1")
    suspend fun updateTierRank(tier: String, rank: Int)
}
