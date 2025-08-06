package com.example.tictachabitss.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.*
import com.example.tictachabitss.data.Habit

@Composable
fun HabitCard(
    habit: Habit,
    onToggle: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    var showActions by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = tween(durationMillis = 150)
    )

    val cardColor = if (habit.done) {
        Brush.horizontalGradient(
            colors = listOf(
                Color(0xFF4CAF50),
                Color(0xFF45A049)
            )
        )
    } else {
        Brush.horizontalGradient(
            colors = listOf(
                Color(0xFF2E2E54),
                Color(0xFF3A3A6B)
            )
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .clip(RoundedCornerShape(12.dp)) // Bordes menos redondeados para Wear OS
            .background(brush = cardColor)
            .clickable {
                isPressed = true
                onToggle()
            }
            .padding(12.dp) // Padding reducido
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    // Icono del hábito - más pequeño
                    Box(
                        modifier = Modifier
                            .size(32.dp) // Reducido de 40dp
                            .background(
                                color = Color.White.copy(alpha = 0.2f),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = habit.icon,
                            fontSize = 16.sp, // Reducido
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp)) // Reducido

                    // Nombre del hábito
                    Column {
                        Text(
                            text = habit.name,
                            style = MaterialTheme.typography.body2, // Más pequeño
                            color = Color.White,
                            fontWeight = FontWeight.Medium,
                            maxLines = 1
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (habit.done) "✓" else "○", // Símbolos más simples
                                style = MaterialTheme.typography.caption2,
                                color = Color.White.copy(alpha = 0.7f)
                            )

                            if (habit.streak > 0) {
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "🔥${habit.streak}",
                                    style = MaterialTheme.typography.caption2,
                                    color = Color(0xFFFF9800)
                                )
                            }
                        }
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Indicador de estado más pequeño
                    AnimatedVisibility(
                        visible = habit.done,
                        enter = scaleIn() + fadeIn(),
                        exit = scaleOut() + fadeOut()
                    ) {
                        Box(
                            modifier = Modifier
                                .size(20.dp) // Reducido
                                .background(
                                    color = Color.White,
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "✓",
                                color = Color(0xFF4CAF50),
                                fontSize = 12.sp, // Reducido
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    // Botón de menú más pequeño
                    Box(
                        modifier = Modifier
                            .size(24.dp) // Reducido
                            .background(
                                color = Color.White.copy(alpha = 0.2f),
                                shape = CircleShape
                            )
                            .clickable { showActions = !showActions },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "⋮",
                            color = Color.White,
                            fontSize = 12.sp, // Reducido
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Botones de acción más compactos
            AnimatedVisibility(
                visible = showActions,
                enter = fadeIn() + scaleIn(),
                exit = fadeOut() + scaleOut()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp), // Reducido
                    horizontalArrangement = Arrangement.spacedBy(6.dp) // Reducido
                ) {
                    ActionButton(
                        text = "Editar",
                        color = Color(0xFF2196F3),
                        onClick = onEdit
                    )

                    ActionButton(
                        text = "Borrar",
                        color = Color(0xFFFF5722),
                        onClick = onDelete
                    )
                }
            }
        }
    }

    // Reset pressed state
    LaunchedEffect(isPressed) {
        if (isPressed) {
            kotlinx.coroutines.delay(150)
            isPressed = false
        }
    }
}