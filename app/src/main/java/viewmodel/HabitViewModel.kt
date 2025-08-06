package viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.tictachabitss.data.Habit
import com.example.tictachabitss.data.HabitCategory
import com.example.tictachabitss.data.Priority

class HabitViewModel : ViewModel() {

    private val _habits = MutableStateFlow(
        listOf(
            Habit(
                id = "1",
                name = "Beber 8 vasos de agua",
                icon = "💧",
                category = HabitCategory.HEALTH,
                priority = Priority.HIGH,
                streak = 1
            ),
            Habit(
                id = "2",
                name = "Meditar 10 minutos",
                icon = "🧘",
                category = HabitCategory.MINDFULNESS,
                priority = Priority.MEDIUM,
                streak = 1
            ),
            Habit(
                id = "3",
                name = "Hacer ejercicio",
                icon = "🏃",
                category = HabitCategory.EXERCISE,
                priority = Priority.HIGH,
                streak = 1
            ),
            Habit(
                id = "4",
                name = "Leer por 30 minutos",
                icon = "📚",
                category = HabitCategory.PRODUCTIVITY,
                priority = Priority.MEDIUM,
                streak = 1
            ),
            Habit(
                id = "5",
                name = "Escribir en diario",
                icon = "📝",
                category = HabitCategory.MINDFULNESS,
                priority = Priority.LOW,
                streak = 1
            ),
            Habit(
                id = "6",
                name = "Caminar 10,000 pasos",
                icon = "👟",
                category = HabitCategory.EXERCISE,
                priority = Priority.MEDIUM,
                streak = 1
            )
        )
    )

    val habits: StateFlow<List<Habit>> = _habits.asStateFlow()

    fun toggleHabit(id: String) {
        val currentHabits = _habits.value.toMutableList()
        val index = currentHabits.indexOfFirst { it.id == id }

        if (index != -1) {
            val habit = currentHabits[index]
            val updatedHabit = habit.copy(
                done = !habit.done,
                streak = if (!habit.done) habit.streak + 1 else maxOf(0, habit.streak - 1)
            )
            currentHabits[index] = updatedHabit
            _habits.value = currentHabits
        }
    }

    fun addHabit(name: String, icon: String, category: HabitCategory, priority: Priority) {
        val currentHabits = _habits.value.toMutableList()
        val newHabit = Habit(
            id = System.currentTimeMillis().toString(),
            name = name,
            icon = icon,
            category = category,
            priority = priority
        )
        currentHabits.add(newHabit)
        _habits.value = currentHabits
    }

    fun editHabit(id: String, name: String, icon: String, category: HabitCategory, priority: Priority) {
        val currentHabits = _habits.value.toMutableList()
        val index = currentHabits.indexOfFirst { it.id == id }

        if (index != -1) {
            val habit = currentHabits[index]
            val updatedHabit = habit.copy(
                name = name,
                icon = icon,
                category = category,
                priority = priority
            )
            currentHabits[index] = updatedHabit
            _habits.value = currentHabits
        }
    }

    fun removeHabit(id: String) {
        val currentHabits = _habits.value.toMutableList()
        currentHabits.removeAll { it.id == id }
        _habits.value = currentHabits
    }

    fun getHabitById(id: String): Habit? {
        return _habits.value.find { it.id == id }
    }

    fun getCompletedHabitsCount(): Int {
        return habits.value.count { it.done }
    }

    fun getTotalHabitsCount(): Int {
        return habits.value.size
    }

    fun getCompletionPercentage(): Float {
        val total = getTotalHabitsCount()
        return if (total > 0) getCompletedHabitsCount().toFloat() / total else 0f
    }
}