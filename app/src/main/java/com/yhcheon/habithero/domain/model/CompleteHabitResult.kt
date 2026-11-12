package com.yhcheon.habithero.domain.model

/**
 * 습관 완료 처리 결과 (Task 21: XP 필드 제거)
 */
data class CompleteHabitResult(
    val fragmentEarned: Int,
    val newStreak: Int,
    val isCapReached: Boolean,
    val newTotalFragments: Int,
    // ─── Task 20: Tier/Rank 진화 정보 ───
    val tierEvolved: Boolean,
    val newTier: String,
    val newRank: Int
)
