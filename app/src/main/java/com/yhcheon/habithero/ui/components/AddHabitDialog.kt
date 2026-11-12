package com.yhcheon.habithero.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

/**
 * 습관 추가/수정 다이얼로그 (HBT-01, HBT-03)
 * - initialName/initialXp가 있으면 수정 모드
 * - title/confirmButtonText로 모드별 텍스트 구분
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddHabitDialog(
    initialName: String = "",
    initialXp: String = "",
    title: String = "새 습관 추가",
    confirmButtonText: String = "추가",
    onDismiss: () -> Unit,
    onConfirm: (name: String, xp: Int) -> Unit
) {
    var habitName by remember { mutableStateOf(initialName) }
    var xpText by remember { mutableStateOf(initialXp) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = habitName,
                    onValueChange = { habitName = it },
                    label = { Text("습관 이름") },
                    placeholder = { Text("예: 물 2L 마시기") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("habitNameField"),
                    singleLine = true
                )

                OutlinedTextField(
                    value = xpText,
                    onValueChange = { input ->
                        if (input.all { it.isDigit() } || input.isEmpty()) {
                            xpText = input
                        }
                    },
                    label = { Text("보상 XP") },
                    placeholder = { Text("40") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("habitXpField"),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val name = habitName.trim()
                    val xp = xpText.toIntOrNull() ?: 40
                    if (name.isNotEmpty()) {
                        onConfirm(name, xp)
                    }
                }
            ) {
                Text(confirmButtonText, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("취소")
            }
        }
    )
}
