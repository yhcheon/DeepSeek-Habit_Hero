package com.yhcheon.habithero.domain.model

/**
 * 습관 완료 처리 결과
 */
data class CompleteHabitResult(
    val fragmentEarned: Int,
    val xpEarned: Int,
    val newStreak: Int,
    val isCapReached: Boolean,
    val newTotalFragments: Int,
    val newXp: Int,
    // ─── Task 20: Tier/Rank 진화 정보 ───
    val tierEvolved: Boolean,
    val newTier: String,
    val newRank: Int
)
