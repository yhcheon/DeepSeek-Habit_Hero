package com.yhcheon.habithero.domain.usecase

import com.yhcheon.habithero.domain.model.CompleteHabitResult

/**
 * 습관 완료 처리 UseCase
 *
 * 규칙:
 * - 조각 +1 (일일 최대 10개)
 * - XP = 습관 보상 XP × (1 + 히어로 패시브) + 화염 보너스
 * - 스트릭 +1
 * - 상한 초과 시 조각 미적립, XP만 적립
 */
object CompleteHabitUseCase {

    private const val DAILY_FRAGMENT_CAP = 10

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

        // 2. XP 계산
        val baseXp = habitRewardXp
        val passiveBonus = (baseXp * heroPassiveXpRate).toInt()
        val xpEarned = baseXp + passiveBonus + fireBonus

        // 3. 결과 반환
        return CompleteHabitResult(
            fragmentEarned = fragmentEarned,
            xpEarned = xpEarned,
            newStreak = profileStreak + 1,
            isCapReached = isCapReached,
            newTotalFragments = profileFragments + fragmentEarned,
            newXp = profileXp + xpEarned
        )
    }
}
