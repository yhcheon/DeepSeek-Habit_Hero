package com.yhcheon.habithero.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yhcheon.habithero.domain.model.Habit
import com.yhcheon.habithero.ui.components.AddHabitDialog
import com.yhcheon.habithero.ui.components.HabitCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    nickname: String,
    tier: String,
    level: Int,
    fragments: Int,
    fragmentCap: Int,
    habits: List<Habit>,
    completedHabitIds: Set<Long>,
    onSettingsClick: () -> Unit,
    onStartTimerClick: () -> Unit,
    onAddHabit: (name: String, xp: Int) -> Unit,
    onToggleHabit: (habitId: Long) -> Unit,
    onHabitLongClick: (habitId: Long) -> Unit = {}  // ← 추가
) {
    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = nickname.take(1).ifEmpty { "H" },
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                        Spacer(Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Habit Hero",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "${tier}급 · Lv.${level}",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                },
                actions = {
                    Surface(
                        shape = RoundedCornerShape(50),
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "💎", style = MaterialTheme.typography.bodyMedium)
                            Spacer(Modifier.width(4.dp))
                            Text(
                                text = "$fragments / $fragmentCap",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    }
                    IconButton(onClick = onSettingsClick) {
                        Icon(Icons.Default.Settings, contentDescription = "설정")
                    }
                }
            )
        },
        floatingActionButton = {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ExtendedFloatingActionButton(
                    onClick = onStartTimerClick,
                    icon = { Icon(Icons.Default.PlayArrow, contentDescription = null) },
                    text = { Text("지금 5분만 하기") },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
                FloatingActionButton(
                    onClick = { showAddDialog = true },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    Icon(Icons.Default.Add, contentDescription = "습관 추가")
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Spacer(Modifier.height(8.dp))

            Text(
                text = "오늘의 루틴",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            if (habits.isEmpty()) {
                Text(
                    text = "아직 등록된 습관이 없습니다. + 버튼으로 추가해 보세요!",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                habits.forEach { habit ->
                    HabitCard(
                        habit = habit,
                        streak = 14,
                        isCompleted = habit.id in completedHabitIds,
                        onToggle = { onToggleHabit(habit.id) },
                        onLongClick = { onHabitLongClick(habit.id) }  // ← 추가
                    )
                }
            }

            Spacer(Modifier.height(80.dp))
        }
    }

    if (showAddDialog) {
        AddHabitDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { name, xp ->
                onAddHabit(name, xp)
                showAddDialog = false
            }
        )
    }
}
