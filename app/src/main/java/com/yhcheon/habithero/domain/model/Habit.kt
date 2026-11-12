package com.yhcheon.habithero.domain.model

/**
 * 습관 도메인 모델 (Task 21: rewardXp 제거)
 */
data class Habit(
    val id: Long,
    val name: String,
    val category: String = ""
)
