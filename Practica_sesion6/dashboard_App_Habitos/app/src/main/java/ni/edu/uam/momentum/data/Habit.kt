package ni.edu.uam.momentum.data

enum class HabitType {
    MANUAL,
    TIMER,
    DEADLINE
}

data class Habit(
    val name: String,
    val goal: String,
    val completed: Boolean = false,
    val category: String,
    val type: HabitType = HabitType.MANUAL,
    val dueDate: String? = null,
    val duration: String? = null
)