package com.yhcheon.habithero.ui.timer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay

@Composable
fun TimerContainer(
    initialSeconds: Int,
    onGiveUp: () -> Unit,
    onComplete: () -> Unit
) {
    var remainingSeconds by remember { mutableIntStateOf(initialSeconds) }
    var isPaused by remember { mutableStateOf(false) }

    LaunchedEffect(isPaused, remainingSeconds) {
        if (!isPaused && remainingSeconds > 0) {
            delay(1000)
            remainingSeconds -= 1
        }
    }

    TimerScreen(
        remainingSeconds = remainingSeconds,
        isPaused = isPaused,
        onTogglePause = { isPaused = !isPaused },
        onGiveUp = onGiveUp,
        onComplete = onComplete  // 5분 완주 후에만 버튼 활성화됨
    )
}
