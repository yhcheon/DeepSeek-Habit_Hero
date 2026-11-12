package com.yhcheon.habithero.ui.morning

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.yhcheon.habithero.ui.theme.HabitHeroTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class MorningPopupScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun `Start_Quests_버튼을_누르면_onStart가_호출된다`() {
        var startCalled = false
        composeRule.setContent {
            HabitHeroTheme {
                MorningPopupScreen(
                    nickname = "테스터",
                    tier = "B",
                    rank = 40,
                    fragments = 3,
                    fragmentCap = 10,
                    streak = 5,
                    onStart = { startCalled = true },
                    onRemindLater = {}
                )
            }
        }

        composeRule.onNodeWithTag("morning_start_button").performClick()
        composeRule.runOnIdle { assertEquals(true, startCalled) }
    }

    @Test
    fun `Remind_Me_Later_버튼을_누르면_onRemindLater가_호출된다`() {
        var remindCalled = false
        composeRule.setContent {
            HabitHeroTheme {
                MorningPopupScreen(
                    nickname = "테스터",
                    tier = "B",
                    rank = 40,
                    fragments = 3,
                    fragmentCap = 10,
                    streak = 5,
                    onStart = {},
                    onRemindLater = { remindCalled = true }
                )
            }
        }

        composeRule.onNodeWithTag("morning_remind_button").performClick()
        composeRule.runOnIdle { assertEquals(true, remindCalled) }
    }

    @Test
    fun `닉네임_조각_스트릭_정보가_화면에_표시된다`() {
        composeRule.setContent {
            HabitHeroTheme {
                MorningPopupScreen(
                    nickname = "히어로",
                    tier = "B",
                    rank = 40,
                    fragments = 3,
                    fragmentCap = 10,
                    streak = 5,
                    onStart = {},
                    onRemindLater = {}
                )
            }
        }

        // testTag 기반 매칭으로 노드 특정
        composeRule.onNodeWithTag("morning_nickname").assertExists()
        composeRule.onNodeWithTag("morning_fragments").assertExists()
        composeRule.onNodeWithTag("morning_streak").assertExists()
    }
}
