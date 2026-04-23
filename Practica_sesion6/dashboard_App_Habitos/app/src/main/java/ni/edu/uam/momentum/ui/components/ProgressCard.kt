package ni.edu.uam.momentum.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ProgressCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFEAF7EE)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Progreso de hoy")

            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
                    .background(Color.LightGray, RoundedCornerShape(20.dp))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.75f)
                        .height(16.dp)
                        .background(Color(0xFF6FCF97), RoundedCornerShape(20.dp))
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(text = "75%")
        }
    }
}