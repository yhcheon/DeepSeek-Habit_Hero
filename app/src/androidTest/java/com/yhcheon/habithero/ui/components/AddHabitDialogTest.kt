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
    fun `다이얼로그에_제목과_입력필드가_표시된다`() {
        composeRule.setContent {
            AddHabitDialog(
                onDismiss = {},
                onConfirm = { _, _ -> }
            )
        }

        composeRule.onNodeWithText("새 습관 추가").assertIsDisplayed()
        composeRule.onNodeWithText("습관 이름").assertIsDisplayed()
        composeRule.onNodeWithText("보상 XP").assertIsDisplayed()
    }

    @Test
    fun `습관이름과_XP를_입력하고_추가시_콜백이_호출된다`() {
        var savedName = ""
        var savedXp = 0

        composeRule.setContent {
            AddHabitDialog(
                onDismiss = {},
                onConfirm = { name, xp ->
                    savedName = name
                    savedXp = xp
                }
            )
        }

        // testTag로 입력 필드 찾기 (placeholder 대신)
        composeRule.onNodeWithTag("habitNameField").performTextInput("명상 10분")
        composeRule.onNodeWithTag("habitXpField").performTextInput("50")
        composeRule.onNodeWithText("추가").performClick()

        assertEquals("명상 10분", savedName)
        assertEquals(50, savedXp)
    }
}
