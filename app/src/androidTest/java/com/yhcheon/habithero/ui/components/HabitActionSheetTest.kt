package com.yhcheon.habithero.ui.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.yhcheon.habithero.domain.model.Habit
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class HabitActionSheetTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val sampleHabit = Habit(
        id = 1,
        name = "물 2L 마시기",
        category = "Health"
    )

    @Test
    fun `액션시트에_습관이름과_수정삭제_메뉴가_표시된다`() {
        composeRule.setContent {
            HabitActionSheet(
                habit = sampleHabit,
                onEdit = {},
                onDelete = {},
                onDismiss = {}
            )
        }

        composeRule.onNodeWithText("물 2L 마시기").assertIsDisplayed()
        composeRule.onNodeWithText("수정").assertIsDisplayed()
        composeRule.onNodeWithText("삭제").assertIsDisplayed()
    }

    @Test
    fun `수정_버튼_클릭시_콜백이_호출된다`() {
        var editClicked = false
        composeRule.setContent {
            HabitActionSheet(
                habit = sampleHabit,
                onEdit = { editClicked = true },
                onDelete = {},
                onDismiss = {}
            )
        }

        composeRule.onNodeWithText("수정").performClick()
        assertTrue(editClicked)
    }

    @Test
    fun `삭제_버튼_클릭시_콜백이_호출된다`() {
        var deleteClicked = false
        composeRule.setContent {
            HabitActionSheet(
                habit = sampleHabit,
                onEdit = {},
                onDelete = { deleteClicked = true },
                onDismiss = {}
            )
        }

        composeRule.onNodeWithText("삭제").performClick()
        assertTrue(deleteClicked)
    }
}
