package com.yhcheon.habithero.ui.dashboard

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import com.yhcheon.habithero.domain.model.Habit
import org.junit.Rule
import org.junit.Test

class DashboardScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val sampleHabits = listOf(
        Habit(id = 1, name = "물 2L 마시기", rewardXp = 0, category = "Health"),
        Habit(id = 2, name = "매일 독서", rewardXp = 0, category = "Mind")
    )

    private fun setDashboardContent(habitStreaks: Map<Long, Int> = emptyMap()) {
        composeRule.setContent {
            DashboardScreen(
                nickname = "테스터",
                tier = "B",
                level = 12,
                fragments = 8,
                fragmentCap = 10,
                habits = sampleHabits,
                completedHabitIds = emptySet(),
                habitStreaks = habitStreaks,
                onSettingsClick = {},
                onStartTimerClick = {},
                onAddHabit = { _ -> },
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

    @Test
    fun `습관별_연속일이_각각_표시된다`() {
        setDashboardContent(habitStreaks = mapOf(1L to 7, 2L to 3))
        composeRule.onNodeWithText("🔥 7일", substring = true).assertIsDisplayed()
        composeRule.onNodeWithText("🔥 3일", substring = true).assertIsDisplayed()
    }

    @Test
    fun `연속일이_없는_습관_2개는_모두_0일로_표시된다`() {
        setDashboardContent(habitStreaks = emptyMap())
        composeRule.onAllNodesWithText("🔥 0일", substring = true)
            .assertCountEquals(2)
    }
}
