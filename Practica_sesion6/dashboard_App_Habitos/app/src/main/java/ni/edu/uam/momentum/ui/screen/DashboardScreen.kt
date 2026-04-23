package ni.edu.uam.momentum.ui.screen

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ni.edu.uam.momentum.data.Habit
import ni.edu.uam.momentum.ui.components.HabitItem
import ni.edu.uam.momentum.ui.components.HeaderSection
import ni.edu.uam.momentum.ui.components.ProgressCard
import ni.edu.uam.momentum.ui.components.WeeklySummary

@Composable
fun DashboardScreen(
    userName: String,
    profileImageUri: Uri?,
    habits: List<Habit>
) {
    val weeklyProgress = listOf(
        "L" to false,
        "M" to false,
        "X" to false,
        "J" to false,
        "V" to false,
        "S" to false,
        "D" to false
    )

    val completedCount = habits.count { it.completed }
    val progress = if (habits.isNotEmpty()) {
        completedCount.toFloat() / habits.size.toFloat()
    } else {
        0f
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Agregar meta"
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            HeaderSection(
                userName = userName,
                profileImageUri = profileImageUri
            )

            Spacer(modifier = Modifier.height(20.dp))

            ProgressCard(progress = progress)

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Tus metas",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (habits.isEmpty()) {
                Text(
                    text = "Todavía no has agregado metas. Usa el botón + para comenzar.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.weight(1f, fill = false)
                ) {
                    items(habits) { habit ->
                        HabitItem(habit = habit)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            WeeklySummary(weeklyProgress = weeklyProgress)
        }
    }
}