package com.example.tictachabitss.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.*

@Composable
fun ProgressCard(
    completionPercentage: Float,
    completedCount: Int,
    totalCount: Int
) {
    val animatedProgress by animateFloatAsState(
        targetValue = completionPercentage,
        animationSpec = tween(durationMillis = 1000)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 6.dp) // Reducido para Wear OS
            .clip(RoundedCornerShape(12.dp)) // Menos redondeado
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF667EEA),
                        Color(0xFF764BA2)
                    )
                )
            )
            .padding(12.dp) // Reducido
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Progreso",
                    style = MaterialTheme.typography.body2, // Más pequeño
                    color = Color.White,
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = "$completedCount/$totalCount",
                    style = MaterialTheme.typography.body1,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(6.dp)) // Reducido

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp) // Más delgada
                    .background(
                        color = Color.White.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(3.dp)
                    )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(animatedProgress)
                        .fillMaxHeight()
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(3.dp)
                        )
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "${(animatedProgress * 100).toInt()}%",
                style = MaterialTheme.typography.caption2, // Más pequeño
                color = Color.White.copy(alpha = 0.8f)
            )
        }
    }
}