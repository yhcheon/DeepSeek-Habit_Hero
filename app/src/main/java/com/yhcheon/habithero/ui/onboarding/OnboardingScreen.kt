package com.yhcheon.habithero.ui.onboarding

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/**
 * 온보딩 화면 (ONB-01)
 * - 닉네임 입력
 * - 히어로 선택 (Rune/Aria)
 * - "모험 시작" 버튼 → 콜백으로 nickname, hero 반환
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingScreen(
    onComplete: (nickname: String, hero: String) -> Unit
) {
    var nickname by remember { mutableStateOf("") }
    var selectedHero by remember { mutableStateOf("RUNE") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Choose Your Hero", fontWeight = FontWeight.Bold) }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(Modifier.height(8.dp))

            // 안내 문구
            Text(
                text = "함께 성장할 파트너 히어로를 선택해 주세요!",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "선택한 캐릭터가 매일 아침 격려의 말풍선과 함께 당신의 습관 달성을 돕습니다.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // 닉네임 입력
            OutlinedTextField(
                value = nickname,
                onValueChange = { nickname = it },
                label = { Text("닉네임을 입력하세요") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // 히어로 카드 2종 (Task 21: XP/패시브 문구 제거)
            HeroCard(
                name = "루키 룬",
                description = "성실한 열정보이",
                passive = "묵묵히 쌓아가는 성실함",
                selected = selectedHero == "RUNE",
                onClick = { selectedHero = "RUNE" }
            )
            HeroCard(
                name = "스파크 아리아",
                description = "당찬 긍정소녀",
                passive = "긍정의 에너지로 이어가는 힘",
                selected = selectedHero == "ARIA",
                onClick = { selectedHero = "ARIA" }
            )

            Spacer(Modifier.height(8.dp))

            // 모험 시작 버튼
            Button(
                onClick = {
                    if (nickname.isNotBlank()) {
                        onComplete(nickname, selectedHero)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(26.dp),
                enabled = nickname.isNotBlank()
            ) {
                Text(
                    text = "모험 시작",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
private fun HeroCard(
    name: String,
    description: String,
    passive: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (selected) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        border = if (selected) {
            BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
        } else {
            BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
        }
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                if (selected) {
                    Text(
                        text = "✓ 선택됨",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = passive,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
