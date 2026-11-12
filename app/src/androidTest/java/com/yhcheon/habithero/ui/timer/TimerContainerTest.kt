package com.yhcheon.habithero.ui.timer

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class TimerContainerTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun `타이머_시작시_05시00초가_표시된다`() {
        composeRule.setContent {
            TimerContainer(
                initialSeconds = 300,
                onGiveUp = {},
                onComplete = {}
            )
        }
        composeRule.onNodeWithText("05:00").assertIsDisplayed()
    }

    @Test
    fun `일시정지_클릭시_재개_버튼으로_변경된다`() {
        composeRule.setContent {
            TimerContainer(
                initialSeconds = 300,
                onGiveUp = {},
                onComplete = {}
            )
        }
        composeRule.onNodeWithText("일시정지").performClick()
        composeRule.onNodeWithText("재개").assertIsDisplayed()
    }

    @Test
    fun `포기_클릭시_콜백이_호출된다`() {
        var gaveUp = false
        composeRule.setContent {
            TimerContainer(
                initialSeconds = 300,
                onGiveUp = { gaveUp = true },
                onComplete = {}
            )
        }
        composeRule.onNodeWithText("포기").performClick()
        assertTrue(gaveUp)
    }
}
