package com.yhcheon.habithero.ui.dashboard

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.yhcheon.habithero.domain.model.Habit
import org.junit.Rule
import org.junit.Test

class DashboardScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val sampleHabits = listOf(
        Habit(id = 1, name = "물 2L 마시기", rewardXp = 40, category = "Health"),
        Habit(id = 2, name = "매일 독서", rewardXp = 40, category = "Mind")
    )

    private fun setDashboardContent() {
        composeRule.setContent {
            DashboardScreen(
                nickname = "테스터",
                tier = "B",
                level = 12,
                fragments = 8,
                fragmentCap = 10,
                habits = sampleHabits,
                completedHabitIds = emptySet(),
                onSettingsClick = {},
                onStartTimerClick = {},
                onAddHabit = { _, _ -> },
                onToggleHabit = {}
            )
        }
    }

    @Test
    fun `대시보드에_상단_프로필_정보가_표시된다`() {
        setDashboardContent()
        composeRule.onNodeWithText("Habit Hero").assertIsDisplayed()
        composeRule.onNodeWithText("B급 · Lv.12").assertIsDisplayed()
        composeRule.onNodeWithText("8 / 10").assertIsDisplayed()
    }

    @Test
    fun `대시보드에_오늘의_루틴_텍스트가_표시된다`() {
        setDashboardContent()
        composeRule.onNodeWithText("오늘의 루틴").assertIsDisplayed()
    }

    @Test
    fun `대시보드에_습관_목록이_표시된다`() {
        setDashboardContent()
        composeRule.onNodeWithText("물 2L 마시기", substring = true).assertIsDisplayed()
        composeRule.onNodeWithText("매일 독서", substring = true).assertIsDisplayed()
    }
}
