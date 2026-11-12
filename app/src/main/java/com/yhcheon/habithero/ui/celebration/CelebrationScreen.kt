package com.yhcheon.habithero.ui.celebration

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

/**
 * 축하 화면 (5분 완주 시)
 * - 별 5개 + "참 잘했어요!"
 * - 1.5초 후 자동 복귀 (onFinished)
 */
@Composable
fun CelebrationScreen(
    habitName: String,
    fragmentEarned: Int,
    onFinished: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(1500)  // 1.5초 후 자동 복귀
        onFinished()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(Modifier.height(24.dp))

        Text(
            text = "✨ 참 잘했어요! ✨",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )

        Spacer(Modifier.height(24.dp))

        // 별 5개
        Text(
            text = "★ ★ ★ ★ ★",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFF59E0B)
        )

        Spacer(Modifier.height(32.dp))

        Text(
            text = "5분 완주!",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "\"$habitName\"",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "습관이 등록되었습니다!",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )

        Spacer(Modifier.height(32.dp))

        Text(
            text = "+ $fragmentEarned 조각 💎",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.tertiary
        )
    }
}
