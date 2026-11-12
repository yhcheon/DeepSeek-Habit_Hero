package com.yhcheon.habithero.domain.usecase

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class CompleteHabitUseCaseTest {

    @Test
    fun `정상_완료시_조각_1개를_획득한다`() {
        val result = CompleteHabitUseCase.execute(
            profileFragments = 5,
            profileStreak = 3,
            todayFragmentCount = 5
        )

        assertEquals(1, result.fragmentEarned)
        assertEquals(4, result.newStreak)
        assertEquals(6, result.newTotalFragments)
        assertFalse(result.isCapReached)
    }

    @Test
    fun `9개에서_1개_완료시_조각10개_상한_도달`() {
        val result = CompleteHabitUseCase.execute(
            profileFragments = 9,
            profileStreak = 0,
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
            profileStreak = 0,
            todayFragmentCount = 10
        )
        assertEquals(0, result.fragmentEarned)
        assertTrue(result.isCapReached)
    }

    // ─────────────────────────────────────────────
    // [Task 20-3] Tier 진화 테스트
    // ─────────────────────────────────────────────

    @Test
    fun `조각_99에서_완료시_100도달_Tier_B에서_A로_진화`() {
        val result = CompleteHabitUseCase.execute(
            profileFragments = 99,
            profileStreak = 0,
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
            profileStreak = 0,
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
            profileStreak = 0,
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
            profileStreak = 0,
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
            profileStreak = 0,
            todayFragmentCount = 0
        )
        assertEquals(51, result.newTotalFragments)
        assertFalse(result.tierEvolved)
        assertEquals("B", result.newTier)
        assertEquals(51, result.newRank)
    }
}
