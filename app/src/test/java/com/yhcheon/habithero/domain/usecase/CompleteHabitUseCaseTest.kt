package com.yhcheon.habithero.domain.usecase

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * CompleteHabitUseCase 테스트 (RED - 최소 스펙 검증)
 * 프로덕션 코드가 없으므로 컴파일 실패가 정상입니다.
 */
class CompleteHabitUseCaseTest {

    @Test
    fun `정상_완료시_조각_1개와_XP를_획득한다`() {
        val result = CompleteHabitUseCase.execute(
            profileFragments = 5,
            profileXp = 100,
            profileStreak = 3,
            habitRewardXp = 40,
            heroPassiveXpRate = 0.0,
            todayFragmentCount = 5
        )

        assertEquals(1, result.fragmentEarned)
        assertEquals(40, result.xpEarned)
        assertEquals(4, result.newStreak)
        assertEquals(6, result.newTotalFragments)
        assertEquals(140, result.newXp)
        assertFalse(result.isCapReached)
    }

    @Test
    fun `9개에서_1개_완료시_조각10개_상한_도달`() {
        val result = CompleteHabitUseCase.execute(
            profileFragments = 9,
            profileXp = 0,
            profileStreak = 0,
            habitRewardXp = 40,
            heroPassiveXpRate = 0.0,
            todayFragmentCount = 9
        )
        assertEquals(1, result.fragmentEarned)
        assertEquals(10, result.newTotalFragments)
        assertTrue(result.isCapReached)
    }

    @Test
    fun `일일_상한_10개_도달후_완료시_조각은_0`() {
        val result = CompleteHabitUseCase.execute(
            profileFragments = 10,
            profileXp = 0,
            profileStreak = 0,
            habitRewardXp = 40,
            heroPassiveXpRate = 0.0,
            todayFragmentCount = 10
        )
        assertEquals(0, result.fragmentEarned)
        assertEquals(40, result.xpEarned)
        assertTrue(result.isCapReached)
    }

    @Test
    fun `룬_패시브_적용시_XP가_5퍼센트_증가한다`() {
        val result = CompleteHabitUseCase.execute(
            profileFragments = 0,
            profileXp = 0,
            profileStreak = 0,
            habitRewardXp = 40,
            heroPassiveXpRate = 0.05,
            todayFragmentCount = 0
        )
        assertEquals(42, result.xpEarned)
    }
}
