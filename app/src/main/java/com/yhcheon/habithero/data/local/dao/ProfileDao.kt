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

    /**
     * 프로필 통계 업데이트 (조각 / XP / 스트릭)
     */
    @Query("""
        UPDATE profiles 
        SET totalFragments = :fragments,
            xp = :xp,
            streak = :streak
        WHERE id = 1
    """)
    suspend fun updateStats(fragments: Int, xp: Int, streak: Int)
}
