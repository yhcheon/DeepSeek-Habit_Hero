package com.yhcheon.habithero

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.yhcheon.habithero.data.local.HabitHeroDatabase
import com.yhcheon.habithero.data.local.entity.CheckInEntity
import com.yhcheon.habithero.data.local.entity.HabitEntity
import com.yhcheon.habithero.data.repository.HabitRepository
import com.yhcheon.habithero.data.repository.ProfileRepository
import com.yhcheon.habithero.domain.model.Habit
import com.yhcheon.habithero.domain.usecase.CompleteHabitUseCase
import com.yhcheon.habithero.ui.celebration.CelebrationScreen
import com.yhcheon.habithero.ui.components.AddHabitDialog
import com.yhcheon.habithero.ui.components.HabitActionSheet
import com.yhcheon.habithero.ui.dashboard.DashboardScreen
import com.yhcheon.habithero.ui.onboarding.OnboardingScreen
import com.yhcheon.habithero.ui.profile.ProfileScreen
import com.yhcheon.habithero.ui.theme.HabitHeroTheme
import com.yhcheon.habithero.ui.timer.TimerContainer
import kotlinx.coroutines.launch
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HabitHeroTheme {
                HabitHeroApp()
            }
        }
    }
}

private suspend fun loadHabits(db: HabitHeroDatabase, habitRepo: HabitRepository): List<Habit> {
    val entityList = habitRepo.getAllHabits()
    return entityList.map {
        Habit(
            id = it.id,
            name = it.name,
            rewardXp = it.rewardXp,
            category = it.category ?: ""
        )
    }
}

@Composable
fun HabitHeroApp() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val db = remember { HabitHeroDatabase.getInstance(context) }
    val profileRepo = remember { ProfileRepository(db.profileDao()) }
    val habitRepo = remember { HabitRepository(db.habitDao()) }

    var isLoading by remember { mutableStateOf(true) }
    var nickname by remember { mutableStateOf("") }
    var hero by remember { mutableStateOf("RUNE") }
    var onboardingCompleted by remember { mutableStateOf(false) }
    var habits by remember { mutableStateOf<List<Habit>>(emptyList()) }
    var completedIds by remember { mutableStateOf<Set<Long>>(emptySet()) }
    var fragments by remember { mutableStateOf(0) }
    var xp by remember { mutableStateOf(0) }
    var streak by remember { mutableStateOf(0) }
    var tier by remember { mutableStateOf("B") }
    var level by remember { mutableStateOf(0) }

    var actionTarget by remember { mutableStateOf<Habit?>(null) }
    var editTarget by remember { mutableStateOf<Habit?>(null) }
    var deleteTarget by remember { mutableStateOf<Habit?>(null) }

    // ─── 화면 전환 상태 ───
    var showProfile by remember { mutableStateOf(false) }

    // ─── 즉석 습관 + 타이머 상태 ───
    var showQuickHabitDialog by remember { mutableStateOf(false) }
    var pendingHabitName by remember { mutableStateOf("") }
    var pendingHabitXp by remember { mutableStateOf(0) }
    var showTimer by remember { mutableStateOf(false) }
    var showCelebration by remember { mutableStateOf(false) }
    var lastEarnedFragments by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        val profile = profileRepo.getProfile()
        if (profile != null) {
            nickname = profile.nickname
            hero = profile.hero
            fragments = profile.totalFragments
            xp = profile.xp
            streak = profile.streak
            tier = profile.tier
            level = profile.rank
            onboardingCompleted = true
        }
        isLoading = false
    }

    LaunchedEffect(onboardingCompleted) {
        if (onboardingCompleted) {
            habits = loadHabits(db, habitRepo)
            val today = LocalDate.now().toString()
            completedIds = db.checkInDao().getTodayCompletedHabitIds(today).toSet()
        }
    }

    fun completeHabit(habitId: Long, onDone: (Int) -> Unit = {}) {
        scope.launch {
            val today = LocalDate.now().toString()
            val targetHabit = habits.find { it.id == habitId }
            if (targetHabit != null && habitId !in completedIds) {
                val todayCount = db.checkInDao().getTodayCheckInCount(today)
                val result = CompleteHabitUseCase.execute(
                    profileFragments = fragments,
                    profileXp = xp,
                    profileStreak = streak,
                    habitRewardXp = targetHabit.rewardXp,
                    heroPassiveXpRate = 0.0,
                    todayFragmentCount = todayCount
                )
                db.checkInDao().insert(
                    CheckInEntity(
                        habitId = habitId,
                        date = today,
                        completedAt = System.currentTimeMillis(),
                        streakAtTime = result.newStreak
                    )
                )
                db.profileDao().updateStats(
                    fragments = result.newTotalFragments,
                    xp = result.newXp,
                    streak = result.newStreak
                )
                completedIds = completedIds + habitId
                fragments = result.newTotalFragments
                xp = result.newXp
                streak = result.newStreak
                onDone(result.fragmentEarned)
            } else {
                onDone(0)
            }
        }
    }

    when {
        isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        !onboardingCompleted -> {
            OnboardingScreen(
                onComplete = { inputNickname, inputHero ->
                    scope.launch {
                        profileRepo.saveProfile(inputNickname, inputHero)
                        nickname = inputNickname
                        hero = inputHero
                        onboardingCompleted = true
                    }
                }
            )
        }
        showCelebration -> {
            CelebrationScreen(
                habitName = pendingHabitName,
                fragmentEarned = lastEarnedFragments,
                onFinished = {
                    showCelebration = false
                    pendingHabitName = ""
                    pendingHabitXp = 0
                    lastEarnedFragments = 0
                }
            )
        }
        showTimer -> {
            TimerContainer(
                initialSeconds = 300,
                onGiveUp = {
                    showTimer = false
                    pendingHabitName = ""
                    pendingHabitXp = 0
                },
                onComplete = {
                    scope.launch {
                        habitRepo.addHabit(pendingHabitName, pendingHabitXp)
                        habits = loadHabits(db, habitRepo)
                        val newHabit = habits.lastOrNull()
                        if (newHabit != null) {
                            completeHabit(newHabit.id) { earned ->
                                lastEarnedFragments = earned
                                showTimer = false
                                showCelebration = true
                            }
                        } else {
                            showTimer = false
                        }
                    }
                }
            )
        }
        showProfile -> {
            ProfileScreen(
                initialNickname = nickname,
                initialHero = hero,
                onSave = { newNickname, newHero ->
                    scope.launch {
                        profileRepo.updateNickname(newNickname)
                        profileRepo.updateHero(newHero)
                        nickname = newNickname
                        hero = newHero
                        showProfile = false
                    }
                },
                onBack = { showProfile = false }
            )
        }
        else -> {
            DashboardScreen(
                nickname = nickname,
                tier = tier,
                level = level,
                fragments = fragments,
                fragmentCap = 10,
                habits = habits,
                completedHabitIds = completedIds,
                onSettingsClick = { showProfile = true },
                onStartTimerClick = { showQuickHabitDialog = true },
                onAddHabit = { name, xpInput ->
                    scope.launch {
                        habitRepo.addHabit(name, xpInput)
                        habits = loadHabits(db, habitRepo)
                    }
                },
                onToggleHabit = { habitId ->
                    if (habitId in completedIds) {
                        scope.launch {
                            val today = LocalDate.now().toString()
                            val targetHabit = habits.find { it.id == habitId }
                            if (targetHabit != null) {
                                db.checkInDao().deleteByHabitAndDate(habitId, today)
                                val remainingCount = db.checkInDao().getTodayCheckInCount(today)
                                val newFragments = minOf(remainingCount, 10)
                                val newXp = (xp - targetHabit.rewardXp).coerceAtLeast(0)
                                db.profileDao().updateStats(fragments = newFragments, xp = newXp, streak = streak)
                                completedIds = completedIds - habitId
                                fragments = newFragments
                                xp = newXp
                            }
                        }
                    } else {
                        completeHabit(habitId)
                    }
                },
                onHabitLongClick = { habitId ->
                    actionTarget = habits.find { it.id == habitId }
                }
            )

            if (showQuickHabitDialog) {
                AddHabitDialog(
                    title = "지금 5분만 하기",
                    confirmButtonText = "5분 시작",
                    onDismiss = { showQuickHabitDialog = false },
                    onConfirm = { name, xpInput ->
                        pendingHabitName = name
                        pendingHabitXp = xpInput
                        showQuickHabitDialog = false
                        showTimer = true
                    }
                )
            }

            actionTarget?.let { target ->
                HabitActionSheet(
                    habit = target,
                    onEdit = {
                        editTarget = target
                        actionTarget = null
                    },
                    onDelete = {
                        deleteTarget = target
                        actionTarget = null
                    },
                    onDismiss = { actionTarget = null }
                )
            }

            editTarget?.let { target ->
                AddHabitDialog(
                    initialName = target.name,
                    initialXp = target.rewardXp.toString(),
                    title = "습관 수정",
                    confirmButtonText = "저장",
                    onDismiss = { editTarget = null },
                    onConfirm = { newName, newXp ->
                        scope.launch {
                            db.habitDao().update(
                                HabitEntity(
                                    id = target.id,
                                    profileId = 1,
                                    name = newName,
                                    cycle = "DAILY",
                                    rewardXp = newXp,
                                    category = target.category
                                )
                            )
                            habits = loadHabits(db, habitRepo)
                            editTarget = null
                        }
                    }
                )
            }

            deleteTarget?.let { target ->
                AlertDialog(
                    onDismissRequest = { deleteTarget = null },
                    title = { Text("습관 삭제") },
                    text = { Text("'${target.name}' 습관을 정말 삭제하시겠습니까?\n\n오늘 체크 기록도 함께 삭제됩니다.") },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                scope.launch {
                                    val today = LocalDate.now().toString()
                                    db.checkInDao().deleteByHabitAndDate(target.id, today)
                                    db.habitDao().delete(
                                        HabitEntity(
                                            id = target.id,
                                            profileId = 1,
                                            name = target.name,
                                            cycle = "DAILY",
                                            rewardXp = target.rewardXp,
                                            category = target.category
                                        )
                                    )
                                    val remainingCount = db.checkInDao().getTodayCheckInCount(today)
                                    val newFragments = minOf(remainingCount, 10)
                                    db.profileDao().updateStats(
                                        fragments = newFragments,
                                        xp = xp,
                                        streak = streak
                                    )
                                    fragments = newFragments
                                    habits = loadHabits(db, habitRepo)
                                    deleteTarget = null
                                }
                            }
                        ) {
                            Text("삭제", color = MaterialTheme.colorScheme.error)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { deleteTarget = null }) {
                            Text("취소")
                        }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HabitHeroAppPreview() {
    HabitHeroTheme {
        HabitHeroApp()
    }
}
