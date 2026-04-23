package ni.edu.uam.momentum.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun WeeklySummary() {
    val days = listOf(
        "L" to true,
        "M" to true,
        "X" to false,
        "J" to true,
        "V" to false,
        "S" to true,
        "D" to false
    )

    Column {
        Text(text = "Resumen semanal")

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            days.forEach { (day, completed) ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(
                                color = if (completed) Color(0xFF6FCF97) else Color.LightGray,
                                shape = CircleShape
                            )
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(text = day)
                }
            }
        }
    }
}