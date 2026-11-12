package com.yhcheon.habithero.ui.onboarding

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class OnboardingScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun `온보딩화면_닉네임입력과_히어로선택_UI가_표시된다`() {
        var savedNickname = ""
        var savedHero = ""

        composeRule.setContent {
            OnboardingScreen(
                onComplete = { nickname, hero ->
                    savedNickname = nickname
                    savedHero = hero
                }
            )
        }

        // 1) 타이틀 확인
        composeRule.onNodeWithText("Choose Your Hero").assertIsDisplayed()

        // 2) 닉네임 입력 필드에 텍스트 입력
        composeRule.onNodeWithText("닉네임을 입력하세요").performTextInput("테스터")

        // 3) 룬 선택 카드 확인
        composeRule.onNodeWithText("루키 룬").assertIsDisplayed()

        // 4) 모험 시작 버튼 클릭
        composeRule.onNodeWithText("모험 시작").performClick()

        // 5) 콜백 값 검증
        assert(savedNickname == "테스터") { "닉네임이 저장되지 않았습니다: $savedNickname" }
        assert(savedHero == "RUNE") { "히어로가 저장되지 않았습니다: $savedHero" }
    }
}
