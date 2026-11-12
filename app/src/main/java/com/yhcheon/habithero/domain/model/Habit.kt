package com.yhcheon.habithero.domain.model

data class Habit(
    val id: Long = 0,
    val name: String,
    val rewardXp: Int,
    val category: String = "",
    val cycle: String = "DAILY"
)
