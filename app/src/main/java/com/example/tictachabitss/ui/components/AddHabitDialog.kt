package com.example.tictachabitss.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.*
import com.example.tictachabitss.data.HabitCategory
import com.example.tictachabitss.data.Priority

@Composable
fun AddHabitDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String, HabitCategory, Priority) -> Unit
) {
    var habitName by remember { mutableStateOf("") }
    var habitIcon by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(HabitCategory.GENERAL) }
    var selectedPriority by remember { mutableStateOf(Priority.MEDIUM) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.8f))
            .clickable { onDismiss() },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
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
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Nuevo Hábito",
                    style = MaterialTheme.typography.title3,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )

                // Selector de iconos predefinidos
                Text(
                    text = "Selecciona un hábito:",
                    color = Color.White.copy(0.8f),
                    style = MaterialTheme.typography.body2
                )

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val predefinedHabits = listOf(
                        "💧" to "Beber agua",
                        "🏃" to "Ejercitarse",
                        "📚" to "Leer",
                        "🧘" to "Meditar",
                        "📝" to "Escribir",
                        "👟" to "Caminar",
                        "🍎" to "Comer sano",
                        "💪" to "Entrenar",
                        "🎯" to "Meta diaria",
                        "⭐" to "Rutina"
                    )
                    items(predefinedHabits) { (icon, name) ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(
                                    color = if (habitIcon == icon && habitName == name)
                                        Color(0xFF667EEA)
                                    else
                                        Color.White.copy(0.2f)
                                )
                                .clickable {
                                    habitIcon = icon
                                    habitName = name
                                }
                                .padding(8.dp)
                        ) {
                            Text(
                                text = icon,
                                fontSize = 20.sp
                            )
                            Text(
                                text = name,
                                color = Color.White,
                                style = MaterialTheme.typography.caption1,
                                textAlign = TextAlign.Center,
                                maxLines = 1
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

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
                                onConfirm(habitName, habitIcon, selectedCategory, selectedPriority)
                            }
                        },
                        modifier = Modifier.weight(1f),
                        colors = ChipDefaults.chipColors(
                            backgroundColor = Color(0xFF667EEA)
                        ),
                        enabled = habitName.isNotBlank() && habitIcon.isNotBlank(),
                        label = {
                            Text(
                                "Agregar",
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
}