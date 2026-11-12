package com.yhcheon.habithero.domain.usecase

import com.yhcheon.habithero.domain.model.CompleteHabitResult

/**
 * 습관 완료 처리 UseCase (Task 20 개편)
 *
 * 규칙:
 * - 조각 +1 (일일 최대 10개)
 * - XP = 습관 보상 XP × (1 + 히어로 패시브) + 화염 보너스 (표시용, 미사용)
 * - 전체 스트릭 +1 (모닝 팝업 요약용)
 * - **조각 100 누적마다 Tier 진화** (B→A→S→MYTH), 진화 시 Rank 0 리셋
 * - Rank = totalFragments % 100
 */
object CompleteHabitUseCase {

    private const val DAILY_FRAGMENT_CAP = 10
    private const val FRAGMENTS_PER_TIER = 100

    private val TIER_ORDER = listOf("B", "A", "S", "MYTH")

    fun execute(
        profileFragments: Int,
        profileXp: Int,
        profileStreak: Int,
        habitRewardXp: Int,
        heroPassiveXpRate: Double,
        todayFragmentCount: Int,
        fireBonus: Int = 0
    ): CompleteHabitResult {
        // 1. 조각 계산
        val newFragmentCount = todayFragmentCount + 1
        val fragmentEarned = if (newFragmentCount > DAILY_FRAGMENT_CAP) 0 else 1
        val isCapReached = newFragmentCount >= DAILY_FRAGMENT_CAP
        val newTotalFragments = profileFragments + fragmentEarned

        // 2. XP 계산 (표시용, 미사용)
        val baseXp = habitRewardXp
        val passiveBonus = (baseXp * heroPassiveXpRate).toInt()
        val xpEarned = baseXp + passiveBonus + fireBonus

        // 3. Tier/Rank 계산 (조각 100 누적마다 진화)
        val tierLevel = newTotalFragments / FRAGMENTS_PER_TIER
        val newRank = newTotalFragments % FRAGMENTS_PER_TIER
        val newTier = TIER_ORDER.getOrElse(tierLevel) { "MYTH" }

        // 진화 판정: 이번 완료로 조각 100의 배수에 도달했는가?
        val prevTierLevel = profileFragments / FRAGMENTS_PER_TIER
        val tierEvolved = tierLevel > prevTierLevel

        // 4. 결과 반환
        return CompleteHabitResult(
            fragmentEarned = fragmentEarned,
            xpEarned = xpEarned,
            newStreak = profileStreak + 1,
            isCapReached = isCapReached,
            newTotalFragments = newTotalFragments,
            newXp = profileXp + xpEarned,
            tierEvolved = tierEvolved,
            newTier = newTier,
            newRank = newRank
        )
    }
}
