package ni.edu.uam.momentum.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import ni.edu.uam.momentum.data.Habit

@Composable
fun AppScreen() {
    var hasStarted by remember { mutableStateOf(false) }
    var userName by remember { mutableStateOf("") }
    var userHabits by remember { mutableStateOf(listOf<Habit>()) }

    if (!hasStarted) {
        SetupScreen { name, habits ->
            userName = name
            userHabits = habits
            hasStarted = true
        }
    } else {
        DashboardScreen(
            userName = userName,
            habits = userHabits
        )
    }
}