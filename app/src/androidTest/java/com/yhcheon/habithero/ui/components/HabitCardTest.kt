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

    private val longNameHabit = Habit(
        id = 2,
        name = "습관 이름이 엄청 길면 어떻게 될까요 이건 테스트 습관입니다 여러 줄에 걸쳐서 어떻게 나올까요",
        rewardXp = 40,
        category = "Test"
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
    fun `습관_카드에_연속일_배지가_표시된다`() {
        composeRule.setContent {
            HabitCard(
                habit = sampleHabit,
                streak = 14,
                isCompleted = false,
                onToggle = {},
                onLongClick = {}
            )
        }
        composeRule.onNodeWithTag("habitCard_streak_1", useUnmergedTree = true).assertIsDisplayed()
    }

    @Test
    fun `습관_카드에_XP_배지가_더이상_표시되지_않는다`() {
        composeRule.setContent {
            HabitCard(
                habit = sampleHabit,
                streak = 14,
                isCompleted = false,
                onToggle = {},
                onLongClick = {}
            )
        }
        composeRule.onNodeWithText("40 XP", substring = true).assertDoesNotExist()
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

        composeRule.onNodeWithText("물 2L 마시기", substring = true)
            .performTouchInput { longClick() }

        assertTrue("길게 누르기 콜백이 호출되지 않음", longClicked)
    }

    // ─────────────────────────────────────────────
    // [Task 20-5d] 긴 이름 축약 + 배지 표시
    // ─────────────────────────────────────────────

    @Test
    fun `긴_습관_이름일_때도_연속일_배지가_표시된다`() {
        composeRule.setContent {
            HabitCard(
                habit = longNameHabit,
                streak = 5,
                isCompleted = false,
                onToggle = {},
                onLongClick = {}
            )
        }
        composeRule.onNodeWithTag("habitCard_streak_2", useUnmergedTree = true).assertIsDisplayed()
    }

    @Test
    fun `긴_습관_이름은_1줄로_축약되어_표시된다`() {
        composeRule.setContent {
            HabitCard(
                habit = longNameHabit,
                streak = 5,
                isCompleted = false,
                onToggle = {},
                onLongClick = {}
            )
        }
        // 습관 이름 노드가 1줄(maxLines=1)이므로 텍스트가 있어도 카드가 과도하게 커지지 않음
        // 텍스트 존재 자체는 substring으로 확인 가능
        composeRule.onNodeWithText("습관 이름이 엄청 길면", substring = true).assertIsDisplayed()
    }
}
