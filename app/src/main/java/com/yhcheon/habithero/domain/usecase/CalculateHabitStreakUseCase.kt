package com.yhcheon.habithero.domain.usecase

import java.time.LocalDate
import java.time.format.DateTimeParseException

/**
 * 습관별 연속 달성일 계산 (Task 20)
 * - checkedDates: 해당 습관의 체크인 날짜 문자열 집합 (YYYY-MM-DD)
 * - today: 기준 날짜
 *
 * 규칙:
 * - 오늘 체크했으면: 오늘부터 연속으로 체크한 일수
 * - 오늘 체크 안 했으면: 어제부터 연속으로 체크한 일수 (오늘은 아직 안 했을 뿐)
 * - 어제도 안 했으면: 0
 * - 잘못된 날짜 문자열은 무시
 */
object CalculateHabitStreakUseCase {

    fun execute(checkedDates: Set<String>, today: LocalDate): Int {
        val parsed = checkedDates.mapNotNull { raw ->
            try {
                LocalDate.parse(raw)
            } catch (e: DateTimeParseException) {
                null
            }
        }.toSet()

        if (parsed.isEmpty()) return 0

        // 시작점: 오늘 체크했으면 오늘, 아니면 어제
        val start = if (today in parsed) today else today.minusDays(1)
        if (start !in parsed) return 0

        var count = 0
        var cursor = start
        while (cursor in parsed) {
            count++
            cursor = cursor.minusDays(1)
        }
        return count
    }
}
