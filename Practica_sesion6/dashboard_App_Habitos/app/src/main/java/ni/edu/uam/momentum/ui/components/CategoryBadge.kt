package ni.edu.uam.momentum.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun CategoryBadge(category: String) {
    val badgeColor = when (category) {
        "Salud" -> MaterialTheme.colorScheme.tertiaryContainer
        "Estudio" -> MaterialTheme.colorScheme.primaryContainer
        "Trabajo" -> MaterialTheme.colorScheme.secondaryContainer
        else -> MaterialTheme.colorScheme.surfaceVariant
    }

    Box(
        modifier = Modifier
            .background(
                color = badgeColor,
                shape = RoundedCornerShape(50)
            )
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = category,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Medium
        )
    }
}