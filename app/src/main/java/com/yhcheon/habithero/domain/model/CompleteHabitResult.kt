package com.yhcheon.habithero.domain.model

/**
 * 습관 완료 처리 결과
 */
data class CompleteHabitResult(
    val fragmentEarned: Int,    // 실제 획득한 조각 (상한 초과 시 0)
    val xpEarned: Int,           // 실제 획득한 XP
    val newStreak: Int,          // 갱신된 스트릭
    val isCapReached: Boolean,   // 일일 조각 상한 도달 여부
    val newTotalFragments: Int,  // 누적 조각
    val newXp: Int               // 누적 XP
)
