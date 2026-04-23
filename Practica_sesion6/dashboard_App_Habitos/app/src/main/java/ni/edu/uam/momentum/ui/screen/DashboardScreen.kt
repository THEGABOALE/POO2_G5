package ni.edu.uam.momentum.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ni.edu.uam.momentum.ui.components.HeaderSection
import ni.edu.uam.momentum.ui.components.ProgressCard
import ni.edu.uam.momentum.ui.components.WeeklySummary

@Composable
fun DashboardScreen() {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar hábito"
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
            HeaderSection()

            Spacer(modifier = Modifier.height(20.dp))

            ProgressCard()

            Spacer(modifier = Modifier.height(20.dp))

            WeeklySummary()
        }
    }
}