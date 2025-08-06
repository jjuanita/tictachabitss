package com.example.tictachabitss.data

import androidx.compose.ui.graphics.Color

data class Habit(
    val id: String = "",
    val name: String = "",
    val icon: String = "",
    val done: Boolean = false,
    val category: HabitCategory = HabitCategory.GENERAL,
    val streak: Int = 0,
    val priority: Priority = Priority.MEDIUM,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

enum class HabitCategory(val displayName: String, val color: Color) {
    HEALTH("Salud", Color(0xFF4CAF50)),
    EXERCISE("Ejercicio", Color(0xFF2196F3)),
    PRODUCTIVITY("Productividad", Color(0xFF9C27B0)),
    MINDFULNESS("Mindfulness", Color(0xFF00BCD4)),
    GENERAL("General", Color(0xFF757575))
}

enum class Priority(val displayName: String, val color: Color) {
    HIGH("Alta", Color(0xFFFF5722)),
    MEDIUM("Media", Color(0xFF2196F3)),
    LOW("Baja", Color(0xFF4CAF50))
}