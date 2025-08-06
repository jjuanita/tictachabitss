package com.example.tictachabitss.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.*
import com.example.tictachabitss.data.Habit
import com.example.tictachabitss.data.HabitCategory
import com.example.tictachabitss.data.Priority
import com.example.tictachabitss.ui.components.HabitCard
import com.example.tictachabitss.ui.components.ProgressCard
import com.example.tictachabitss.ui.components.AddHabitDialog
import com.example.tictachabitss.ui.components.EditHabitDialog
import viewmodel.HabitViewModel

@Composable
fun HabitListScreen(viewModel: HabitViewModel) {
    val habits by viewModel.habits.collectAsState()

    // Calcular valores reactivos basados directamente en la lista de hábitos
    val completedCount = habits.count { it.done }
    val totalCount = habits.size
    val completionPercentage = if (totalCount > 0) completedCount.toFloat() / totalCount else 0f

    var showAddDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }
    var habitToEdit: Habit? by remember { mutableStateOf(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF1A1A2E),
                        Color(0xFF16213E),
                        Color(0xFF0F0F23)
                    )
                )
            )
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 80.dp)
        ) {
            item {
                Text(
                    text = "Mis Hábitos",
                    style = MaterialTheme.typography.title2,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    textAlign = TextAlign.Center
                )
            }

            item {
                ProgressCard(
                    completionPercentage = completionPercentage,
                    completedCount = completedCount,
                    totalCount = totalCount
                )
            }

            items(habits) { habit ->
                HabitCard(
                    habit = habit,
                    onToggle = { viewModel.toggleHabit(habit.id) },
                    onEdit = {
                        habitToEdit = habit
                        showEditDialog = true
                    },
                    onDelete = { viewModel.removeHabit(habit.id) }
                )
            }
        }

        // Botón flotante para agregar hábitos
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .size(56.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF667EEA),
                            Color(0xFF764BA2)
                        )
                    ),
                    shape = CircleShape
                )
                .clickable { showAddDialog = true },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "+",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Diálogo para agregar nuevos hábitos
        if (showAddDialog) {
            AddHabitDialog(
                onDismiss = { showAddDialog = false },
                onConfirm = { name, icon, category, priority ->
                    viewModel.addHabit(name, icon, category, priority)
                    showAddDialog = false
                }
            )
        }

        // Diálogo para editar hábitos
        if (showEditDialog && habitToEdit != null) {
            EditHabitDialog(
                habit = habitToEdit!!,
                onDismiss = {
                    showEditDialog = false
                    habitToEdit = null
                },
                onSave = { name, icon, category, priority ->
                    viewModel.editHabit(habitToEdit!!.id, name, icon, category, priority)
                    showEditDialog = false
                    habitToEdit = null
                }
            )
        }
    }
}