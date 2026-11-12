package com.yhcheon.habithero.ui.timer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerScreen(
    remainingSeconds: Int,
    isPaused: Boolean,
    onTogglePause: () -> Unit,
    onGiveUp: () -> Unit,
    onComplete: () -> Unit
) {
    val minutes = remainingSeconds / 60
    val seconds = remainingSeconds % 60
    val timeText = "%02d:%02d".format(minutes, seconds)
    // 5분 완주 후에만 완료 버튼 활성화
    val canComplete = remainingSeconds == 0

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("5분 집중 타이머", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onGiveUp) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "뒤로"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Spacer(Modifier.height(32.dp))

            Text(
                text = "⚡ 딱 5분만 집중해 볼까요?",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = if (canComplete) "완료! 조각을 받아가세요" else "시작이 반이에요!",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .size(220.dp)
                    .clip(CircleShape)
                    .background(
                        if (canComplete) MaterialTheme.colorScheme.tertiaryContainer
                        else MaterialTheme.colorScheme.primaryContainer
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = timeText,
                        fontSize = 56.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (canComplete) MaterialTheme.colorScheme.onTertiaryContainer
                                else MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    if (isPaused && !canComplete) {
                        Text(
                            text = "⏸ 일시정지",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(
                    onClick = onTogglePause,
                    modifier = Modifier.weight(1f).height(52.dp),
                    shape = RoundedCornerShape(26.dp),
                    enabled = !canComplete
                ) {
                    Text(if (isPaused) "재개" else "일시정지", fontWeight = FontWeight.Bold)
                }
                OutlinedButton(
                    onClick = onGiveUp,
                    modifier = Modifier.weight(1f).height(52.dp),
                    shape = RoundedCornerShape(26.dp)
                ) {
                    Text("포기", fontWeight = FontWeight.Bold)
                }
            }

            Spacer(Modifier.weight(1f))

            Button(
                onClick = onComplete,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(28.dp),
                enabled = canComplete  // ← 5분 완주 후에만 활성화
            ) {
                Text(
                    text = if (canComplete) "완료하고 조각 받기" else "5분 후 활성화",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}
