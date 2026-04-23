package ni.edu.uam.momentum.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ni.edu.uam.momentum.data.Habit
import ni.edu.uam.momentum.data.HabitType

@Composable
fun AddGoalDialog(
    onDismiss: () -> Unit,
    onAddGoal: (Habit) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Salud") }
    var type by remember { mutableStateOf(HabitType.MANUAL) }
    var manualGoal by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("") }
    var dueDate by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = {
                    if (name.isNotBlank()) {
                        val newHabit = when (type) {
                            HabitType.MANUAL -> Habit(
                                name = name,
                                goal = manualGoal.ifBlank { "Sin meta definida" },
                                category = category,
                                type = HabitType.MANUAL
                            )

                            HabitType.TIMER -> Habit(
                                name = name,
                                goal = duration.ifBlank { "Duración no definida" },
                                category = category,
                                type = HabitType.TIMER,
                                duration = duration.ifBlank { null }
                            )

                            HabitType.DEADLINE -> Habit(
                                name = name,
                                goal = dueDate.ifBlank { "Sin fecha definida" },
                                category = category,
                                type = HabitType.DEADLINE,
                                dueDate = dueDate.ifBlank { null }
                            )
                        }

                        onAddGoal(newHabit)
                    }
                }
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        },
        title = {
            Text(
                text = "Agregar meta",
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nombre de la meta") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text("Categoría")

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    listOf("Salud", "Estudio", "Trabajo").forEach { item ->
                        FilterChip(
                            selected = category == item,
                            onClick = { category = item },
                            label = { Text(item) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text("Tipo de meta")

                Spacer(modifier = Modifier.height(8.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        selected = type == HabitType.MANUAL,
                        onClick = { type = HabitType.MANUAL },
                        label = { Text("Manual") }
                    )

                    FilterChip(
                        selected = type == HabitType.TIMER,
                        onClick = { type = HabitType.TIMER },
                        label = { Text("Temporizador") }
                    )

                    FilterChip(
                        selected = type == HabitType.DEADLINE,
                        onClick = { type = HabitType.DEADLINE },
                        label = { Text("Fecha límite") }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                when (type) {
                    HabitType.MANUAL -> {
                        OutlinedTextField(
                            value = manualGoal,
                            onValueChange = { manualGoal = it },
                            label = { Text("Meta o detalle") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                    }

                    HabitType.TIMER -> {
                        OutlinedTextField(
                            value = duration,
                            onValueChange = { duration = it },
                            label = { Text("Duración (ej. 2 horas, 30 min)") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                    }

                    HabitType.DEADLINE -> {
                        OutlinedTextField(
                            value = dueDate,
                            onValueChange = { dueDate = it },
                            label = { Text("Fecha límite (ej. 25/04/2026)") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                        )
                    }
                }
            }
        }
    )
}