package com.yhcheon.habithero.domain.usecase

import java.time.LocalDate
import java.time.format.DateTimeParseException

/**
 * 모닝 팝업 노출 여부 판정 (ONB-02)
 * - 당일 최초 진입 시 1회 노출
 * - lastOpenDate가 오늘과 같으면 노출하지 않음
 * - 파싱 실패 / 빈 문자열 / 미래 날짜 등 비정상 상태에서는 방어적으로 노출
 */
object ShouldShowMorningPopupUseCase {

    fun execute(lastOpenDate: String, today: LocalDate): Boolean {
        if (lastOpenDate.isBlank()) return true
        return try {
            val parsed = LocalDate.parse(lastOpenDate)
            parsed != today
        } catch (e: DateTimeParseException) {
            true
        }
    }
}
