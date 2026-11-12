package com.yhcheon.habithero.ui.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/**
 * 프로필/설정 화면 (PRF-01, A안)
 * - 닉네임 변경 (1~20자)
 * - 히어로 변경 (RUNE / ARIA)
 * - 저장 시 onSave로 콜백 → MainActivity가 DB 반영
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    initialNickname: String,
    initialHero: String,
    onSave: (nickname: String, hero: String) -> Unit,
    onBack: () -> Unit
) {
    var nickname by remember { mutableStateOf(initialNickname) }
    var selectedHero by remember { mutableStateOf(initialHero) }

    val nicknameValid = nickname.isNotBlank() && nickname.length <= 20

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("프로필", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("profile_back_button")
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "뒤로")
                    }
                }
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

            Text(
                text = "프로필 설정",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            // 닉네임
            OutlinedTextField(
                value = nickname,
                onValueChange = { if (it.length <= 20) nickname = it },
                label = { Text("닉네임 (1~20자)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("profile_nickname_input"),
                singleLine = true,
                isError = !nicknameValid && nickname.isNotEmpty(),
                supportingText = {
                    Text("${nickname.length} / 20")
                }
            )

            // 히어로 선택
            Text(
                text = "히어로",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            HeroOptionCard(
                heroKey = "RUNE",
                name = "루키 룬",
                description = "성실한 열정보이",
                passive = "집중력 부스트 (+5% XP)",
                selected = selectedHero == "RUNE",
                onClick = { selectedHero = "RUNE" }
            )
            HeroOptionCard(
                heroKey = "ARIA",
                name = "스파크 아리아",
                description = "당찬 긍정소녀",
                passive = "연속 달성 실드 (+5% 회복력)",
                selected = selectedHero == "ARIA",
                onClick = { selectedHero = "ARIA" }
            )

            Spacer(Modifier.height(8.dp))

            Button(
                onClick = { onSave(nickname, selectedHero) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("profile_save_button"),
                shape = RoundedCornerShape(26.dp),
                enabled = nicknameValid
            ) {
                Text("저장", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
private fun HeroOptionCard(
    heroKey: String,
    name: String,
    description: String,
    passive: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .testTag("profile_hero_$heroKey"),
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
                Text(name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                if (selected) {
                    Text(
                        text = "✓ 선택됨",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Text(description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(passive, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
        }
    }
}
