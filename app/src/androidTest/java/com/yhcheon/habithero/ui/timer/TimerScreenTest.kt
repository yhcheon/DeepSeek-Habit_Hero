package com.yhcheon.habithero.ui.timer

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class TimerScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun `타이머화면에_제목과_카운트다운이_표시된다`() {
        composeRule.setContent {
            TimerScreen(
                remainingSeconds = 300,
                isPaused = false,
                onTogglePause = {},
                onGiveUp = {},
                onComplete = {}
            )
        }

        composeRule.onNodeWithText("5분 집중 타이머").assertIsDisplayed()
        composeRule.onNodeWithText("05:00").assertIsDisplayed()
    }

    @Test
    fun `포기_버튼_클릭시_콜백이_호출된다`() {
        var gaveUp = false
        composeRule.setContent {
            TimerScreen(
                remainingSeconds = 300,
                isPaused = false,
                onTogglePause = {},
                onGiveUp = { gaveUp = true },
                onComplete = {}
            )
        }

        composeRule.onNodeWithText("포기").performClick()
        assertTrue(gaveUp)
    }

    @Test
    fun `완료_버튼_클릭시_콜백이_호출된다`() {
        var completed = false
        composeRule.setContent {
            TimerScreen(
                remainingSeconds = 0,  // 5분 완주 상태 (canComplete = true)
                isPaused = false,
                onTogglePause = {},
                onGiveUp = {},
                onComplete = { completed = true }
            )
        }

        composeRule.onNodeWithText("완료하고 조각 받기").performClick()
        assertTrue(completed)
    }

    @Test
    fun `5분_미달_시_완료_버튼이_비활성_텍스트로_표시된다`() {
        composeRule.setContent {
            TimerScreen(
                remainingSeconds = 300,  // 5분 시작
                isPaused = false,
                onTogglePause = {},
                onGiveUp = {},
                onComplete = {}
            )
        }

        composeRule.onNodeWithText("5분 후 활성화").assertIsDisplayed()
    }
}
