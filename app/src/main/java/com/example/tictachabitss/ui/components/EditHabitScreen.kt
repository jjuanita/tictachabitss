package com.example.tictachabitss.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.*
import com.example.tictachabitss.data.Habit
import com.example.tictachabitss.data.HabitCategory
import com.example.tictachabitss.data.Priority

@Composable
fun EditHabitScreen(
    habit: Habit,
    onSave: (String, String, HabitCategory, Priority) -> Unit,
    onCancel: () -> Unit
) {
    var habitName by remember { mutableStateOf(habit.name) }
    var habitIcon by remember { mutableStateOf(habit.icon) }
    var selectedCategory by remember { mutableStateOf(habit.category) }
    var selectedPriority by remember { mutableStateOf(habit.priority) }
    var showIconSelector by remember { mutableStateOf(false) }

    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

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
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Título
            Text(
                text = "Editar Hábito",
                style = MaterialTheme.typography.title2,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            // Campo de nombre personalizable
            Column {
                Text(
                    text = "Nombre del hábito:",
                    color = Color.White.copy(0.8f),
                    style = MaterialTheme.typography.body2,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color.White.copy(0.1f),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(16.dp)
                ) {
                    BasicTextField(
                        value = habitName,
                        onValueChange = { habitName = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester),
                        textStyle = TextStyle(
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        ),
                        cursorBrush = SolidColor(Color.White),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                keyboardController?.hide()
                            }
                        ),
                        singleLine = true,
                        decorationBox = { innerTextField ->
                            if (habitName.isEmpty()) {
                                Text(
                                    text = "Escribe el nombre de tu hábito...",
                                    color = Color.White.copy(0.5f),
                                    fontSize = 16.sp
                                )
                            }
                            innerTextField()
                        }
                    )
                }
            }

            // Selector de icono
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Icono:",
                        color = Color.White.copy(0.8f),
                        style = MaterialTheme.typography.body2,
                        fontWeight = FontWeight.Medium
                    )

                    Text(
                        text = if (showIconSelector) "Menos iconos" else "Más iconos",
                        color = Color(0xFF667EEA),
                        style = MaterialTheme.typography.caption1,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.clickable {
                            showIconSelector = !showIconSelector
                        }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Icono actual seleccionado
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                color = Color(0xFF667EEA),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = habitIcon,
                            fontSize = 24.sp
                        )
                    }

                    Text(
                        text = "Icono seleccionado",
                        color = Color.White.copy(0.7f),
                        style = MaterialTheme.typography.body2
                    )
                }

                // Lista de iconos disponibles
                if (showIconSelector) {
                    Spacer(modifier = Modifier.height(12.dp))

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        val availableIcons = listOf(
                            "💧", "🏃", "📚", "🧘", "📝", "👟", "🍎", "💪", "🎯", "⭐",
                            "☕", "🌅", "🛏️", "🚶", "🧠", "❤️", "🍽️", "💤", "📱", "🎵",
                            "🌱", "🏋️", "🚴", "🏊", "🧘‍♀️", "🎨", "📷", "✍️", "🎸", "🔬"
                        )
                        items(availableIcons) { icon ->
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(
                                        color = if (habitIcon == icon)
                                            Color(0xFF667EEA)
                                        else
                                            Color.White.copy(0.2f),
                                        shape = CircleShape
                                    )
                                    .clickable { habitIcon = icon },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = icon,
                                    fontSize = 20.sp
                                )
                            }
                        }
                    }
                }
            }

            // Selector de categoría
            Column {
                Text(
                    text = "Categoría:",
                    color = Color.White.copy(0.8f),
                    style = MaterialTheme.typography.body2,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(HabitCategory.values()) { category ->
                        Chip(
                            onClick = { selectedCategory = category },
                            colors = ChipDefaults.chipColors(
                                backgroundColor = if (selectedCategory == category)
                                    category.color
                                else
                                    Color.White.copy(0.2f)
                            ),
                            label = {
                                Text(
                                    text = category.displayName,
                                    color = Color.White,
                                    style = MaterialTheme.typography.caption1
                                )
                            }
                        )
                    }
                }
            }

            // Selector de prioridad
            Column {
                Text(
                    text = "Prioridad:",
                    color = Color.White.copy(0.8f),
                    style = MaterialTheme.typography.body2,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Priority.values().forEach { priority ->
                        Chip(
                            onClick = { selectedPriority = priority },
                            modifier = Modifier.weight(1f),
                            colors = ChipDefaults.chipColors(
                                backgroundColor = if (selectedPriority == priority)
                                    priority.color
                                else
                                    Color.White.copy(0.2f)
                            ),
                            label = {
                                Text(
                                    text = priority.displayName,
                                    color = Color.White,
                                    style = MaterialTheme.typography.caption1,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Botones de acción
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Chip(
                    onClick = onCancel,
                    modifier = Modifier.weight(1f),
                    colors = ChipDefaults.chipColors(
                        backgroundColor = Color.Gray
                    ),
                    label = {
                        Text(
                            "Cancelar",
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                )

                Chip(
                    onClick = {
                        if (habitName.isNotBlank() && habitIcon.isNotBlank()) {
                            onSave(habitName.trim(), habitIcon, selectedCategory, selectedPriority)
                        }
                    },
                    modifier = Modifier.weight(1f),
                    colors = ChipDefaults.chipColors(
                        backgroundColor = Color(0xFF667EEA)
                    ),
                    enabled = habitName.isNotBlank() && habitIcon.isNotBlank(),
                    label = {
                        Text(
                            "Guardar",
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                )
            }
        }
    }

    // Auto-focus en el campo de texto al abrir
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}