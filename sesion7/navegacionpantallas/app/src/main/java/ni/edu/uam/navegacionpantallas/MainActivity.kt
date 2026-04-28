package ni.edu.uam.navegacionpantallas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    NavegacionPantallas()
                }
            }
        }
    }
}

@Composable
fun NavegacionPantallas() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "inicio"
    ) {
        composable("inicio") {
            PantallaInicio(navController)
        }

        composable("detalle") {
            PantallaDetalle(navController)
        }

        composable("perfil") {
            PantallaPerfil(navController)
        }
    }
}

@Composable
fun PantallaInicio(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Pantalla de Inicio",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Bienvenido a la aplicación de navegación entre pantallas.")

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                navController.navigate("detalle")
            }
        ) {
            Text(text = "Ir a Detalle")
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = {
                navController.navigate("perfil")
            }
        ) {
            Text(text = "Ir a Perfil")
        }
    }
}

@Composable
fun PantallaDetalle(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Pantalla de Detalle",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Aquí se muestra información adicional sobre la aplicación.")

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                navController.navigate("perfil")
            }
        ) {
            Text(text = "Ir a Perfil")
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = {
                navController.navigate("inicio")
            }
        ) {
            Text(text = "Volver a Inicio")
        }
    }
}

@Composable
fun PantallaPerfil(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Pantalla de Perfil",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Resumen del usuario dentro de la aplicación.")

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                navController.navigate("inicio")
            }
        ) {
            Text(text = "Volver a Inicio")
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = {
                navController.navigate("detalle")
            }
        ) {
            Text(text = "Ir a Detalle")
        }
    }
}