package com.yhcheon.habithero.domain.usecase

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate

class CalculateHabitStreakUseCaseTest {

    private val today = LocalDate.of(2026, 11, 12)

    @Test
    fun `체크_기록이_없으면_0을_반환한다`() {
        val result = CalculateHabitStreakUseCase.execute(
            checkedDates = emptySet(),
            today = today
        )
        assertEquals(0, result)
    }

    @Test
    fun `오늘만_체크했으면_1을_반환한다`() {
        val result = CalculateHabitStreakUseCase.execute(
            checkedDates = setOf("2026-11-12"),
            today = today
        )
        assertEquals(1, result)
    }

    @Test
    fun `오늘과_어제_체크했으면_2를_반환한다`() {
        val result = CalculateHabitStreakUseCase.execute(
            checkedDates = setOf("2026-11-12", "2026-11-11"),
            today = today
        )
        assertEquals(2, result)
    }

    @Test
    fun `3일_연속_체크했으면_3을_반환한다`() {
        val result = CalculateHabitStreakUseCase.execute(
            checkedDates = setOf("2026-11-12", "2026-11-11", "2026-11-10"),
            today = today
        )
        assertEquals(3, result)
    }

    @Test
    fun `어제는_안_했고_오늘만_했으면_1을_반환한다`() {
        // 어제 빠짐 → 연속 리셋, 오늘부터 1
        val result = CalculateHabitStreakUseCase.execute(
            checkedDates = setOf("2026-11-12", "2026-11-10"),
            today = today
        )
        assertEquals(1, result)
    }

    @Test
    fun `오늘_체크_안_했으면_어제까지의_연속일을_반환한다`() {
        // 어제까지 3일 연속 (오늘 아직 안 함)
        val result = CalculateHabitStreakUseCase.execute(
            checkedDates = setOf("2026-11-11", "2026-11-10", "2026-11-09"),
            today = today
        )
        assertEquals(3, result)
    }

    @Test
    fun `오늘_체크_안_했고_어제도_안_했으면_0을_반환한다`() {
        val result = CalculateHabitStreakUseCase.execute(
            checkedDates = setOf("2026-11-09"),
            today = today
        )
        assertEquals(0, result)
    }

    @Test
    fun `7일_연속_체크했으면_7을_반환한다`() {
        val dates = (0..6).map { today.minusDays(it.toLong()).toString() }.toSet()
        val result = CalculateHabitStreakUseCase.execute(
            checkedDates = dates,
            today = today
        )
        assertEquals(7, result)
    }

    @Test
    fun `잘못된_날짜_문자열은_무시한다`() {
        val result = CalculateHabitStreakUseCase.execute(
            checkedDates = setOf("2026-11-12", "invalid-date", "2026-11-11"),
            today = today
        )
        assertEquals(2, result)
    }
}
