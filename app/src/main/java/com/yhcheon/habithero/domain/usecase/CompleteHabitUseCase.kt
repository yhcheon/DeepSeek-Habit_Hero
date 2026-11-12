package com.yhcheon.habithero.domain.usecase

import com.yhcheon.habithero.domain.model.CompleteHabitResult

/**
 * 습관 완료 처리 UseCase (Task 21: XP 제거)
 *
 * 규칙:
 * - 조각 +1 (일일 최대 10개)
 * - 전체 스트릭 +1 (모닝 팝업 요약용)
 * - 조각 100 누적마다 Tier 진화 (B→A→S→MYTH), 진화 시 Rank 0 리셋
 * - Rank = totalFragments % 100
 */
object CompleteHabitUseCase {

    private const val DAILY_FRAGMENT_CAP = 10
    private const val FRAGMENTS_PER_TIER = 100

    private val TIER_ORDER = listOf("B", "A", "S", "MYTH")

    fun execute(
        profileFragments: Int,
        profileStreak: Int,
        todayFragmentCount: Int
    ): CompleteHabitResult {
        // 1. 조각 계산
        val newFragmentCount = todayFragmentCount + 1
        val fragmentEarned = if (newFragmentCount > DAILY_FRAGMENT_CAP) 0 else 1
        val isCapReached = newFragmentCount >= DAILY_FRAGMENT_CAP
        val newTotalFragments = profileFragments + fragmentEarned

        // 2. Tier/Rank 계산 (조각 100 누적마다 진화)
        val tierLevel = newTotalFragments / FRAGMENTS_PER_TIER
        val newRank = newTotalFragments % FRAGMENTS_PER_TIER
        val newTier = TIER_ORDER.getOrElse(tierLevel) { "MYTH" }

        val prevTierLevel = profileFragments / FRAGMENTS_PER_TIER
        val tierEvolved = tierLevel > prevTierLevel

        // 3. 결과 반환
        return CompleteHabitResult(
            fragmentEarned = fragmentEarned,
            newStreak = profileStreak + 1,
            isCapReached = isCapReached,
            newTotalFragments = newTotalFragments,
            tierEvolved = tierEvolved,
            newTier = newTier,
            newRank = newRank
        )
    }
}
