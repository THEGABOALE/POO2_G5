package ni.edu.uam.momentum.ui.screen

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import ni.edu.uam.momentum.data.Habit

@Composable
fun AppScreen() {
    var hasStarted by remember { mutableStateOf(false) }
    var userName by remember { mutableStateOf("") }
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }
    val habits = remember { mutableStateListOf<Habit>() }

    if (!hasStarted) {
        WelcomeScreen { name, imageUri ->
            userName = name
            profileImageUri = imageUri
            hasStarted = true
        }
    } else {
        DashboardScreen(
            userName = userName,
            profileImageUri = profileImageUri,
            habits = habits,
            onAddHabit = { newHabit ->
                habits.add(newHabit)
            }
        )
    }
}