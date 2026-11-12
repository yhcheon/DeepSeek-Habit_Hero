package com.yhcheon.habithero.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/**
 * 습관 추가/수정 다이얼로그 (HBT-01, HBT-03, Task 20-6)
 * - initialName이 있으면 수정 모드
 * - title/confirmButtonText로 모드별 텍스트 구분
 * - XP 입력 필드 제거 (Task 20: XP 폐지)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddHabitDialog(
    initialName: String = "",
    title: String = "새 습관 추가",
    confirmButtonText: String = "추가",
    onDismiss: () -> Unit,
    onConfirm: (name: String) -> Unit
) {
    var habitName by remember { mutableStateOf(initialName) }

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
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val name = habitName.trim()
                    if (name.isNotEmpty()) {
                        onConfirm(name)
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
