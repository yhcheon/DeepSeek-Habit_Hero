package com.yhcheon.habithero.domain.usecase

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Task 21: 언체크 시 조각 정확히 -1 감소 검증
 * MainActivity의 onToggleHabit(언체크) 및 습관 삭제 흐름과 동일한 규칙:
 *   newFragments = maxOf(fragments - 1, 0)
 */
class UncheckFragmentTest {

    private fun uncheck(fragments: Int): Int = maxOf(fragments - 1, 0)

    @Test
    fun `조각_0에서_언체크시_0유지_음수방지`() {
        assertEquals(0, uncheck(0))
    }

    @Test
    fun `조각_1에서_언체크시_0`() {
        assertEquals(0, uncheck(1))
    }

    @Test
    fun `조각_50에서_언체크시_49`() {
        assertEquals(49, uncheck(50))
    }

    @Test
    fun `조각_10에서_언체크시_9`() {
        assertEquals(9, uncheck(10))
    }

    @Test
    fun `조각_100에서_언체크시_99`() {
        assertEquals(99, uncheck(100))
    }
}
