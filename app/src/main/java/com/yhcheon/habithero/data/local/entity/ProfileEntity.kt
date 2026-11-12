package com.yhcheon.habithero.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profiles")
data class ProfileEntity(
    @PrimaryKey val id: Long = 1,
    val nickname: String,
    val hero: String, // "RUNE" or "ARIA"
    val tier: String, // "B", "A", "S", "MYTH"
    val rank: Int,    // 0 ~ 100
    val totalFragments: Int,
    val streak: Int,
    val lastOpenDate: String // 모닝 팝업 판정용 (YYYY-MM-DD)
)
