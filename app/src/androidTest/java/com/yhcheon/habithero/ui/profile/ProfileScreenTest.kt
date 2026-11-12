package com.yhcheon.habithero.ui.profile

import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import com.yhcheon.habithero.ui.theme.HabitHeroTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class ProfileScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun `닉네임과_히어로를_변경하고_저장하면_콜백으로_전달된다`() {
        var savedNickname = ""
        var savedHero = ""
        composeRule.setContent {
            HabitHeroTheme {
                ProfileScreen(
                    initialNickname = "기존닉",
                    initialHero = "RUNE",
                    onSave = { n, h -> savedNickname = n; savedHero = h },
                    onBack = {}
                )
            }
        }

        // 닉네임 변경
        composeRule.onNodeWithTag("profile_nickname_input")
            .performTextClearance()
        composeRule.onNodeWithTag("profile_nickname_input")
            .performTextInput("새닉")

        // 히어로 변경 (ARIA 선택)
        composeRule.onNodeWithTag("profile_hero_ARIA").performClick()

        // 저장
        composeRule.onNodeWithTag("profile_save_button").performClick()

        composeRule.runOnIdle {
            assertEquals("새닉", savedNickname)
            assertEquals("ARIA", savedHero)
        }
    }

    @Test
    fun `닉네임이_비어있으면_저장_버튼이_비활성화된다`() {
        composeRule.setContent {
            HabitHeroTheme {
                ProfileScreen(
                    initialNickname = "기존닉",
                    initialHero = "RUNE",
                    onSave = { _, _ -> },
                    onBack = {}
                )
            }
        }

        composeRule.onNodeWithTag("profile_nickname_input").performTextClearance()
        composeRule.onNodeWithTag("profile_save_button").assertIsNotEnabled()

        composeRule.onNodeWithTag("profile_nickname_input").performTextInput("A")
        composeRule.onNodeWithTag("profile_save_button").assertIsEnabled()
    }

    @Test
    fun `뒤로가기_버튼을_누르면_onBack이_호출된다`() {
        var backCalled = false
        composeRule.setContent {
            HabitHeroTheme {
                ProfileScreen(
                    initialNickname = "닉",
                    initialHero = "RUNE",
                    onSave = { _, _ -> },
                    onBack = { backCalled = true }
                )
            }
        }

        composeRule.onNodeWithTag("profile_back_button").performClick()
        composeRule.runOnIdle { assertEquals(true, backCalled) }
    }

    @Test
    fun `초기_닉네임이_화면에_표시된다`() {
        composeRule.setContent {
            HabitHeroTheme {
                ProfileScreen(
                    initialNickname = "초기닉",
                    initialHero = "ARIA",
                    onSave = { _, _ -> },
                    onBack = {}
                )
            }
        }
        composeRule.onNodeWithText("초기닉").assertExists()
    }
}
