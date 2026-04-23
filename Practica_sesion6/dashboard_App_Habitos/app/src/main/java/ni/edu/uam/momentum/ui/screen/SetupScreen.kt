package ni.edu.uam.momentum.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ni.edu.uam.momentum.data.Habit

@Composable
fun SetupScreen(
    onContinue: (String, List<Habit>) -> Unit
) {
    var userName by remember { mutableStateOf("") }
    var habitName by remember { mutableStateOf("") }
    var habitGoal by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Salud") }

    val habits = remember { mutableStateListOf<Habit>() }

    val suggestedHabits = listOf(
        Habit("Beber agua", "2L", category = "Salud"),
        Habit("Leer 20 minutos", "8:00 AM", category = "Estudio"),
        Habit("Hacer ejercicio", "30 min", category = "Salud"),
        Habit("Planificar el día", "7:00 AM", category = "Trabajo")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Bienvenido a Momentum",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Configura tu perfil y tus hábitos antes de entrar al dashboard.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = userName,
            onValueChange = { userName = it },
            label = { Text("Tu nombre") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Agregar hábito personalizado",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = habitName,
            onValueChange = { habitName = it },
            label = { Text("Nombre del hábito") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = habitGoal,
            onValueChange = { habitGoal = it },
            label = { Text("Meta u hora") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("Salud", "Estudio", "Trabajo").forEach { category ->
                OutlinedButton(
                    onClick = { selectedCategory = category }
                ) {
                    Text(text = category)
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                if (habitName.isNotBlank() && habitGoal.isNotBlank()) {
                    habits.add(
                        Habit(
                            name = habitName,
                            goal = habitGoal,
                            category = selectedCategory
                        )
                    )
                    habitName = ""
                    habitGoal = ""
                    selectedCategory = "Salud"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Agregar hábito"
            )
            Spacer(modifier = Modifier.height(0.dp))
            Text(text = " Agregar hábito")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Hábitos sugeridos",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            suggestedHabits.take(2).forEach { habit ->
                TextButton(
                    onClick = {
                        if (habits.none { it.name == habit.name }) {
                            habits.add(habit)
                        }
                    }
                ) {
                    Text(text = habit.name)
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            suggestedHabits.drop(2).forEach { habit ->
                TextButton(
                    onClick = {
                        if (habits.none { it.name == habit.name }) {
                            habits.add(habit)
                        }
                    }
                ) {
                    Text(text = habit.name)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Tus hábitos seleccionados",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f, fill = true)
        ) {
            items(habits) { habit ->
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = habit.name,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "${habit.goal} • ${habit.category}",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (userName.isNotBlank() && habits.isNotEmpty()) {
                    onContinue(userName, habits)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Entrar al dashboard")
        }
    }
}