package com.yhcheon.habithero.domain.usecase

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate

class ShouldShowMorningPopupUseCaseTest {

    private val today = LocalDate.of(2026, 11, 12)

    @Test
    fun `lastOpenDate가_오늘이면_팝업을_띄우지_않는다`() {
        val result = ShouldShowMorningPopupUseCase.execute(
            lastOpenDate = "2026-11-12",
            today = today
        )
        assertFalse(result)
    }

    @Test
    fun `lastOpenDate가_어제면_팝업을_띄운다`() {
        val result = ShouldShowMorningPopupUseCase.execute(
            lastOpenDate = "2026-11-11",
            today = today
        )
        assertTrue(result)
    }

    @Test
    fun `lastOpenDate가_과거면_팝업을_띄운다`() {
        val result = ShouldShowMorningPopupUseCase.execute(
            lastOpenDate = "2026-01-01",
            today = today
        )
        assertTrue(result)
    }

    @Test
    fun `lastOpenDate가_빈_문자열이면_팝업을_띄운다`() {
        val result = ShouldShowMorningPopupUseCase.execute(
            lastOpenDate = "",
            today = today
        )
        assertTrue(result)
    }

    @Test
    fun `lastOpenDate가_미래_날짜면_팝업을_띄운다`() {
        val result = ShouldShowMorningPopupUseCase.execute(
            lastOpenDate = "2026-12-31",
            today = today
        )
        assertTrue(result)
    }

    @Test
    fun `lastOpenDate가_잘못된_형식이면_팝업을_띄운다`() {
        val result = ShouldShowMorningPopupUseCase.execute(
            lastOpenDate = "not-a-date",
            today = today
        )
        assertTrue(result)
    }
}
