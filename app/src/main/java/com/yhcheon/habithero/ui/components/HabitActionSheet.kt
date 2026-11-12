package com.yhcheon.habithero.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yhcheon.habithero.domain.model.Habit

/**
 * 습관 카드 길게 누르기 → 표시되는 액션 시트
 * - 습관 이름
 * - 수정 / 삭제 메뉴
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitActionSheet(
    habit: Habit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = habit.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                ActionRow(
                    emoji = "✏️",
                    label = "수정",
                    onClick = {
                        onEdit()
                        onDismiss()
                    }
                )
                HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                ActionRow(
                    emoji = "🗑️",
                    label = "삭제",
                    onClick = {
                        onDelete()
                        onDismiss()
                    },
                    isDestructive = true
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("취소")
            }
        }
    )
}

@Composable
private fun ActionRow(
    emoji: String,
    label: String,
    onClick: () -> Unit,
    isDestructive: Boolean = false
) {
    val textColor = if (isDestructive) MaterialTheme.colorScheme.error
                    else MaterialTheme.colorScheme.onSurface
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp, horizontal = 8.dp),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        Text(text = emoji, style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.width(12.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            color = textColor
        )
    }
}
