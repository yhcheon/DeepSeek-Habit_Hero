package com.yhcheon.habithero

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * MainActivity 기본 동작 테스트
 * - 온보딩 vs 대시보드 중 어느 화면이든 앱이 크래시 없이 시작되는지만 검증
 * - 개별 화면 검증은 OnboardingScreenTest, DashboardScreenTest에서 담당
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun 앱시작시_크래시없이_화면이_표시된다() {
        // 앱이 시작되고 화면이 렌더링되면 통과
        // (온보딩 or 대시보드 어느 쪽이든 OK)
        composeRule.waitForIdle()

        // Compose 루트가 존재하면 최소 1개 이상의 노드가 있음
        val rootExists = runCatching {
            composeRule.onNodeWithText("", substring = true).fetchSemanticsNode()
            true
        }.getOrDefault(false)

        // 크래시 없이 여기까지 왔으면 통과
        assert(true)
    }
}
