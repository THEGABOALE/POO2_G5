package ni.edu.uam.registroapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import ni.edu.uam.registroapi.data.model.Carrera
import ni.edu.uam.registroapi.ui.theme.RegistroApiTheme
import ni.edu.uam.registroapi.viewmodel.CarreraViewModel
import ni.edu.uam.registroapi.viewmodel.UiState
import java.text.NumberFormat
import java.util.Locale
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RegistroApiTheme {
                Surface(
                    modifier = androidx.compose.ui.Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CarreraScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarreraScreen(
    viewModel: CarreraViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Carreras disponibles")
                }
            )
        }
    ) { paddingValues ->
        when (val state = uiState) {
            is UiState.Loading -> {
                LoadingContent(paddingValues)
            }

            is UiState.Success -> {
                CarreraListContent(
                    carreras = state.data,
                    paddingValues = paddingValues
                )
            }

            is UiState.Error -> {
                ErrorContent(
                    message = state.message,
                    paddingValues = paddingValues,
                    onRetry = {
                        viewModel.cargarCarreras()
                    }
                )
            }
        }
    }
}

@Composable
fun LoadingContent(
    paddingValues: PaddingValues
) {
    Box(
        modifier = androidx.compose.ui.Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun CarreraListContent(
    carreras: List<Carrera>,
    paddingValues: PaddingValues
) {
    if (carreras.isEmpty()) {
        Box(
            modifier = androidx.compose.ui.Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            Text(
                text = "No hay carreras registradas.",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    } else {
        LazyColumn(
            modifier = androidx.compose.ui.Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(carreras) { carrera ->
                CarreraCard(carrera = carrera)
            }
        }
    }
}

@Composable
fun CarreraCard(
    carrera: Carrera
) {
    val formatoMoneda = NumberFormat.getCurrencyInstance(Locale("es", "NI"))

    Card(
        modifier = androidx.compose.ui.Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = androidx.compose.ui.Modifier.padding(16.dp)
        ) {
            Text(
                text = carrera.nombre,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = androidx.compose.ui.Modifier.height(8.dp))

            Text(
                text = carrera.descripcion,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = androidx.compose.ui.Modifier.height(12.dp))

            Row(
                modifier = androidx.compose.ui.Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "ID: ${carrera.id}",
                    style = MaterialTheme.typography.labelMedium
                )

                Text(
                    text = formatoMoneda.format(carrera.costo),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun ErrorContent(
    message: String,
    paddingValues: PaddingValues,
    onRetry: () -> Unit
) {
    Column(
        modifier = androidx.compose.ui.Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        Text(
            text = "Ocurrió un problema",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.error
        )

        Spacer(modifier = androidx.compose.ui.Modifier.height(12.dp))

        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = androidx.compose.ui.Modifier.height(20.dp))

        Button(
            onClick = onRetry
        ) {
            Text("Intentar de nuevo")
        }
    }
}