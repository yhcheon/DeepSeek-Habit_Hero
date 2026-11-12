package com.yhcheon.habithero.ui.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class AddHabitDialogTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun `다이얼로그에_제목과_습관이름_입력필드가_표시된다`() {
        composeRule.setContent {
            AddHabitDialog(
                onDismiss = {},
                onConfirm = { _ -> }
            )
        }

        composeRule.onNodeWithText("새 습관 추가").assertIsDisplayed()
        composeRule.onNodeWithText("습관 이름").assertIsDisplayed()
    }

    @Test
    fun `보상_XP_입력필드가_더이상_표시되지_않는다`() {
        composeRule.setContent {
            AddHabitDialog(
                onDismiss = {},
                onConfirm = { _ -> }
            )
        }

        composeRule.onNodeWithText("보상 XP").assertDoesNotExist()
        composeRule.onNodeWithTag("habitXpField").assertDoesNotExist()
    }

    @Test
    fun `습관이름을_입력하고_추가시_콜백이_호출된다`() {
        var savedName = ""

        composeRule.setContent {
            AddHabitDialog(
                onDismiss = {},
                onConfirm = { name ->
                    savedName = name
                }
            )
        }

        composeRule.onNodeWithTag("habitNameField").performTextInput("명상 10분")
        composeRule.onNodeWithText("추가").performClick()

        assertEquals("명상 10분", savedName)
    }

    @Test
    fun `수정_모드에서_기존_이름이_표시된다`() {
        composeRule.setContent {
            AddHabitDialog(
                initialName = "기존 습관",
                title = "습관 수정",
                confirmButtonText = "저장",
                onDismiss = {},
                onConfirm = { _ -> }
            )
        }

        composeRule.onNodeWithText("기존 습관").assertIsDisplayed()
        composeRule.onNodeWithText("저장").assertIsDisplayed()
    }
}
