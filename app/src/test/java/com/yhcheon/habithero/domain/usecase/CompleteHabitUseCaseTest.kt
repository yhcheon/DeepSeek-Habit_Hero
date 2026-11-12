package com.yhcheon.habithero.domain.usecase

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

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

    // ─────────────────────────────────────────────
    // [Task 20-3] Tier 진화 테스트
    // ─────────────────────────────────────────────

    @Test
    fun `조각_99에서_완료시_100도달_Tier_B에서_A로_진화`() {
        val result = CompleteHabitUseCase.execute(
            profileFragments = 99,
            profileXp = 0,
            profileStreak = 0,
            habitRewardXp = 40,
            heroPassiveXpRate = 0.0,
            todayFragmentCount = 0
        )
        assertEquals(100, result.newTotalFragments)
        assertTrue(result.tierEvolved)
        assertEquals("A", result.newTier)
        assertEquals(0, result.newRank)
    }

    @Test
    fun `조각_199에서_완료시_200도달_Tier_A에서_S로_진화`() {
        val result = CompleteHabitUseCase.execute(
            profileFragments = 199,
            profileXp = 0,
            profileStreak = 0,
            habitRewardXp = 40,
            heroPassiveXpRate = 0.0,
            todayFragmentCount = 0
        )
        assertEquals(200, result.newTotalFragments)
        assertTrue(result.tierEvolved)
        assertEquals("S", result.newTier)
        assertEquals(0, result.newRank)
    }

    @Test
    fun `조각_299에서_완료시_300도달_Tier_S에서_신화로_진화`() {
        val result = CompleteHabitUseCase.execute(
            profileFragments = 299,
            profileXp = 0,
            profileStreak = 0,
            habitRewardXp = 40,
            heroPassiveXpRate = 0.0,
            todayFragmentCount = 0
        )
        assertEquals(300, result.newTotalFragments)
        assertTrue(result.tierEvolved)
        assertEquals("MYTH", result.newTier)
        assertEquals(0, result.newRank)
    }

    @Test
    fun `조각_100에서_완료시_101도달_진화하지_않음_Rank_1`() {
        val result = CompleteHabitUseCase.execute(
            profileFragments = 100,
            profileXp = 0,
            profileStreak = 0,
            habitRewardXp = 40,
            heroPassiveXpRate = 0.0,
            todayFragmentCount = 0
        )
        assertEquals(101, result.newTotalFragments)
        assertFalse(result.tierEvolved)
        assertEquals("A", result.newTier)
        assertEquals(1, result.newRank)
    }

    @Test
    fun `조각_50에서_완료시_51도달_진화하지_않음_Rank_51`() {
        val result = CompleteHabitUseCase.execute(
            profileFragments = 50,
            profileXp = 0,
            profileStreak = 0,
            habitRewardXp = 40,
            heroPassiveXpRate = 0.0,
            todayFragmentCount = 0
        )
        assertEquals(51, result.newTotalFragments)
        assertFalse(result.tierEvolved)
        assertEquals("B", result.newTier)
        assertEquals(51, result.newRank)
    }
}
