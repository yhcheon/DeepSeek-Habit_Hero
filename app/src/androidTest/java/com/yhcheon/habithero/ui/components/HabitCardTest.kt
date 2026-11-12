package com.yhcheon.habithero.ui.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.longClick
import com.yhcheon.habithero.domain.model.Habit
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class HabitCardTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val sampleHabit = Habit(
        id = 1,
        name = "물 2L 마시기",
        rewardXp = 40,
        category = "Health"
    )

    @Test
    fun `습관_카드에_습관명이_표시된다`() {
        composeRule.setContent {
            HabitCard(
                habit = sampleHabit,
                streak = 14,
                isCompleted = false,
                onToggle = {},
                onLongClick = {}
            )
        }
        composeRule.onNodeWithText("물 2L 마시기", substring = true).assertIsDisplayed()
    }

    @Test
    fun `습관_카드에_스트릭_배지가_표시된다`() {
        composeRule.setContent {
            HabitCard(
                habit = sampleHabit,
                streak = 14,
                isCompleted = false,
                onToggle = {},
                onLongClick = {}
            )
        }
        composeRule.onNodeWithText("14일", substring = true).assertIsDisplayed()
    }

    @Test
    fun `습관_카드에_XP_보상이_표시된다`() {
        composeRule.setContent {
            HabitCard(
                habit = sampleHabit,
                streak = 14,
                isCompleted = false,
                onToggle = {},
                onLongClick = {}
            )
        }
        composeRule.onNodeWithText("40 XP", substring = true).assertIsDisplayed()
    }

    @Test
    fun `습관_카드를_길게_누르면_콜백이_호출된다`() {
        var longClicked = false
        composeRule.setContent {
            HabitCard(
                habit = sampleHabit,
                streak = 14,
                isCompleted = false,
                onToggle = {},
                onLongClick = { longClicked = true }
            )
        }

        // testTag가 붙은 카드 찾기 (없으면 텍스트로 카드 찾기)
        composeRule.onNodeWithText("물 2L 마시기", substring = true)
            .performTouchInput { longClick() }

        assertTrue("길게 누르기 콜백이 호출되지 않음", longClicked)
    }
}
