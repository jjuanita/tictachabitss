package com.example.tictachabitss.ui.components

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
import androidx.compose.ui.draw.clip
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
fun EditHabitDialog(
    habit: Habit,
    onDismiss: () -> Unit,
    onSave: (String, String, HabitCategory, Priority) -> Unit
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
            .background(Color.Black.copy(alpha = 0.8f))
            .clickable { onDismiss() },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight(0.9f)
                .clip(RoundedCornerShape(16.dp))
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF2E2E54),
                            Color(0xFF1A1A2E)
                        )
                    )
                )
                .clickable { /* Evita que se cierre al tocar el contenido */ }
                .padding(20.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Título
                Text(
                    text = "Editar Hábito",
                    style = MaterialTheme.typography.title3,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                // Campo de nombre personalizable
                Column {
                    Text(
                        text = "Nombre:",
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
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(12.dp)
                    ) {
                        BasicTextField(
                            value = habitName,
                            onValueChange = { habitName = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .focusRequester(focusRequester),
                            textStyle = TextStyle(
                                color = Color.White,
                                fontSize = 14.sp
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
                                        text = "Nombre del hábito...",
                                        color = Color.White.copy(0.5f),
                                        fontSize = 14.sp
                                    )
                                }
                                innerTextField()
                            }
                        )
                    }
                }

                // Icono actual y selector
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
                            text = if (showIconSelector) "Ocultar" else "Cambiar",
                            color = Color(0xFF667EEA),
                            style = MaterialTheme.typography.caption1,
                            modifier = Modifier.clickable {
                                showIconSelector = !showIconSelector
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Icono actual
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(
                                color = Color(0xFF667EEA),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = habitIcon,
                            fontSize = 20.sp
                        )
                    }

                    // Selector de iconos
                    if (showIconSelector) {
                        Spacer(modifier = Modifier.height(8.dp))

                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            val icons = listOf(
                                "💧", "🏃", "📚", "🧘", "📝", "👟", "🍎", "💪", "🎯", "⭐",
                                "☕", "🌅", "🛏️", "🚶", "🧠", "❤️", "🍽️", "💤", "📱", "🎵"
                            )
                            items(icons) { icon ->
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
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
                                        fontSize = 16.sp
                                    )
                                }
                            }
                        }
                    }
                }

                // Categorías
                Column {
                    Text(
                        text = "Categoría:",
                        color = Color.White.copy(0.8f),
                        style = MaterialTheme.typography.body2,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        items(HabitCategory.values()) { category ->
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = if (selectedCategory == category)
                                            category.color
                                        else
                                            Color.White.copy(0.2f),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .clickable { selectedCategory = category }
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = category.displayName,
                                    color = Color.White,
                                    style = MaterialTheme.typography.caption1,
                                    fontSize = 10.sp
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                // Botones de acción
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Chip(
                        onClick = onDismiss,
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
    }

    // Auto-focus en el campo de texto
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}