package ni.edu.uam.navegacionpantallas

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage

private val DeliveryRed = Color(0xFFE53935)
private val DeliveryDarkRed = Color(0xFFC62828)
private val DeliveryBackground = Color(0xFFFFF7F6)
private val CardWhite = Color(0xFFFFFFFF)
private val TextDark = Color(0xFF222222)
private val TextGray = Color(0xFF666666)
private val PromoYellow = Color(0xFFFFD54F)
private val SoftRed = Color(0xFFFFEBEE)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = DeliveryBackground
                ) {
                    AppDelivery()
                }
            }
        }
    }
}

@Composable
fun AppDelivery() {
    val navController = rememberNavController()

    var nombreUsuario by rememberSaveable { mutableStateOf("") }
    var ubicacionUsuario by rememberSaveable { mutableStateOf("") }
    var fotoPerfil by rememberSaveable { mutableStateOf("") }

    var cantidadCarrito by rememberSaveable { mutableIntStateOf(0) }

    NavHost(
        navController = navController,
        startDestination = "registro"
    ) {
        composable("registro") {
            PantallaRegistro(
                nombreUsuario = nombreUsuario,
                ubicacionUsuario = ubicacionUsuario,
                fotoPerfil = fotoPerfil,
                onNombreChange = { nombreUsuario = it },
                onUbicacionChange = { ubicacionUsuario = it },
                onFotoSeleccionada = { fotoPerfil = it },
                onEntrar = {
                    navController.navigate("inicio") {
                        popUpTo("registro") {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable("inicio") {
            PantallaInicio(
                navController = navController,
                nombreUsuario = nombreUsuario,
                ubicacionUsuario = ubicacionUsuario,
                cantidadCarrito = cantidadCarrito
            )
        }

        composable("detalle") {
            PantallaDetalle(
                navController = navController,
                onAgregarCarrito = {
                    cantidadCarrito++
                    navController.navigate("carrito")
                }
            )
        }

        composable("carrito") {
            PantallaCarrito(
                navController = navController,
                cantidadCarrito = cantidadCarrito,
                onVaciarCarrito = {
                    cantidadCarrito = 0
                }
            )
        }

        composable("perfil") {
            PantallaPerfil(
                navController = navController,
                nombreUsuario = nombreUsuario,
                ubicacionUsuario = ubicacionUsuario,
                fotoPerfil = fotoPerfil,
                cantidadCarrito = cantidadCarrito
            )
        }
    }
}

@Composable
fun PantallaRegistro(
    nombreUsuario: String,
    ubicacionUsuario: String,
    fotoPerfil: String,
    onNombreChange: (String) -> Unit,
    onUbicacionChange: (String) -> Unit,
    onFotoSeleccionada: (String) -> Unit,
    onEntrar: () -> Unit
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            onFotoSeleccionada(uri.toString())
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeliveryBackground)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "FoodGo",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = DeliveryRed
        )

        Text(
            text = "Registrate para empezar tu pedido",
            fontSize = 16.sp,
            color = TextGray
        )

        Spacer(modifier = Modifier.height(28.dp))

        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(SoftRed)
                .clickable {
                    launcher.launch("image/*")
                },
            contentAlignment = Alignment.Center
        ) {
            if (fotoPerfil.isNotEmpty()) {
                AsyncImage(
                    model = fotoPerfil,
                    contentDescription = "Foto de perfil",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Text(
                    text = "📷",
                    fontSize = 42.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Tocar para subir foto",
            color = DeliveryRed,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = nombreUsuario,
            onValueChange = onNombreChange,
            label = {
                Text(text = "Nombre")
            },
            placeholder = {
                Text(text = "Ej: Gabriel")
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words
            )
        )

        Spacer(modifier = Modifier.height(14.dp))

        OutlinedTextField(
            value = ubicacionUsuario,
            onValueChange = onUbicacionChange,
            label = {
                Text(text = "Ubicación")
            },
            placeholder = {
                Text(text = "Ej: Managua, Nicaragua")
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onEntrar,
            enabled = nombreUsuario.isNotBlank() && ubicacionUsuario.isNotBlank(),
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = DeliveryRed
            ),
            shape = RoundedCornerShape(18.dp)
        ) {
            Text(
                text = "Entrar a la app",
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun PantallaInicio(
    navController: NavHostController,
    nombreUsuario: String,
    ubicacionUsuario: String,
    cantidadCarrito: Int
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(DeliveryBackground)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Hola, $nombreUsuario 👋",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )

                    Text(
                        text = "📍 $ubicacionUsuario",
                        fontSize = 15.sp,
                        color = TextGray
                    )
                }

                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .background(DeliveryRed)
                        .clickable {
                            navController.navigate("carrito")
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "🛒$cantidadCarrito",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        item {
            BusquedaFalsa()
        }

        item {
            CardPromocion()
        }

        item {
            Text(
                text = "Restaurantes populares",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )
        }

        item {
            RestauranteCard(
                nombre = "Pizza Express",
                descripcion = "Pizza familiar, pastas y bebidas",
                tiempo = "25 - 35 min",
                precio = "Envío C$35",
                emoji = "🍕",
                onClick = {
                    navController.navigate("detalle")
                }
            )
        }

        item {
            RestauranteCard(
                nombre = "Burger House",
                descripcion = "Hamburguesas, papas y combos",
                tiempo = "20 - 30 min",
                precio = "Envío C$25",
                emoji = "🍔",
                onClick = {
                    navController.navigate("detalle")
                }
            )
        }

        item {
            RestauranteCard(
                nombre = "Sushi Go",
                descripcion = "Rolls, ramen y comida japonesa",
                tiempo = "30 - 40 min",
                precio = "Envío C$45",
                emoji = "🍣",
                onClick = {
                    navController.navigate("detalle")
                }
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = {
                        navController.navigate("carrito")
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DeliveryRed
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(text = "Carrito")
                }

                OutlinedButton(
                    onClick = {
                        navController.navigate("perfil")
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = "Perfil",
                        color = DeliveryRed
                    )
                }
            }
        }
    }
}

@Composable
fun PantallaDetalle(
    navController: NavHostController,
    onAgregarCarrito: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(DeliveryBackground)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Detalle del pedido",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )

            Text(
                text = "Revisá el restaurante y agregá productos al carrito",
                fontSize = 16.sp,
                color = TextGray
            )
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = CardWhite),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(SoftRed),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "🍕",
                            fontSize = 70.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Pizza Express",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )

                    Text(
                        text = "Pizza familiar, pastas, bebidas y combos especiales.",
                        color = TextGray
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    ProductoPedido(
                        nombre = "Pizza Pepperoni Grande",
                        precio = "C$320"
                    )

                    ProductoPedido(
                        nombre = "Papas familiares",
                        precio = "C$110"
                    )

                    ProductoPedido(
                        nombre = "Gaseosa 1.5L",
                        precio = "C$65"
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Total estimado: C$495",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = DeliveryRed
                    )
                }
            }
        }

        item {
            Button(
                onClick = onAgregarCarrito,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DeliveryRed
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = "Agregar al carrito")
            }
        }

        item {
            OutlinedButton(
                onClick = {
                    navController.navigate("inicio")
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Volver al inicio",
                    color = DeliveryRed
                )
            }
        }
    }
}

@Composable
fun PantallaCarrito(
    navController: NavHostController,
    cantidadCarrito: Int,
    onVaciarCarrito: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(DeliveryBackground)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Mi carrito",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )

            Text(
                text = "Revisá los productos antes de confirmar",
                fontSize = 16.sp,
                color = TextGray
            )
        }

        if (cantidadCarrito == 0) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = CardWhite),
                    elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(28.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "🛒",
                            fontSize = 58.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Tu carrito está vacío",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextDark
                        )

                        Text(
                            text = "Agregá un pedido desde la pantalla de inicio.",
                            color = TextGray
                        )
                    }
                }
            }
        } else {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = CardWhite),
                    elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = "Pedido agregado",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextDark
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        ProductoPedido(
                            nombre = "Combo Pizza Express x$cantidadCarrito",
                            precio = "C$495"
                        )

                        ProductoPedido(
                            nombre = "Envío",
                            precio = "C$35"
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(Color(0xFFE0E0E0))
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Total",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextDark
                            )

                            Text(
                                text = "C$${cantidadCarrito * 495 + 35}",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = DeliveryRed
                            )
                        }
                    }
                }
            }

            item {
                Button(
                    onClick = {
                        onVaciarCarrito()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DeliveryRed
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(text = "Confirmar pedido")
                }
            }
        }

        item {
            OutlinedButton(
                onClick = {
                    navController.navigate("inicio")
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Seguir comprando",
                    color = DeliveryRed
                )
            }
        }

        item {
            OutlinedButton(
                onClick = {
                    navController.navigate("perfil")
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Ver perfil",
                    color = DeliveryRed
                )
            }
        }
    }
}

@Composable
fun PantallaPerfil(
    navController: NavHostController,
    nombreUsuario: String,
    ubicacionUsuario: String,
    fotoPerfil: String,
    cantidadCarrito: Int
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(DeliveryBackground)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Mi perfil",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )

            Text(
                text = "Información registrada del usuario",
                fontSize = 16.sp,
                color = TextGray
            )
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = CardWhite),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(
                    modifier = Modifier.padding(22.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(110.dp)
                            .clip(CircleShape)
                            .background(SoftRed),
                        contentAlignment = Alignment.Center
                    ) {
                        if (fotoPerfil.isNotEmpty()) {
                            AsyncImage(
                                model = fotoPerfil,
                                contentDescription = "Foto de perfil",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Text(
                                text = "👤",
                                fontSize = 48.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = nombreUsuario,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )

                    Text(
                        text = "📍 $ubicacionUsuario",
                        color = TextGray,
                        fontSize = 16.sp
                    )
                }
            }
        }

        item {
            CardResumen(
                titulo = "Pedidos en carrito",
                valor = cantidadCarrito.toString(),
                emoji = "🛒"
            )
        }

        item {
            CardResumen(
                titulo = "Restaurantes favoritos",
                valor = "5",
                emoji = "❤️"
            )
        }

        item {
            CardResumen(
                titulo = "Cupones disponibles",
                valor = "3",
                emoji = "🎟️"
            )
        }

        item {
            Button(
                onClick = {
                    navController.navigate("inicio")
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DeliveryRed
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = "Volver al inicio")
            }
        }

        item {
            OutlinedButton(
                onClick = {
                    navController.navigate("carrito")
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Ver carrito",
                    color = DeliveryRed
                )
            }
        }
    }
}

@Composable
fun BusquedaFalsa() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CardWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(18.dp)
    ) {
        Row(
            modifier = Modifier.padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "🔍",
                fontSize = 22.sp
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "Buscar restaurantes o comidas",
                color = TextGray,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun CardPromocion() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = PromoYellow),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(24.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Promo especial",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )

                Text(
                    text = "Envío desde C$25 en restaurantes seleccionados",
                    fontSize = 15.sp,
                    color = TextDark
                )
            }

            Text(
                text = "🔥",
                fontSize = 42.sp
            )
        }
    }
}

@Composable
fun RestauranteCard(
    nombre: String,
    descripcion: String,
    tiempo: String,
    precio: String,
    emoji: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CardWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        shape = RoundedCornerShape(22.dp)
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .background(SoftRed),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = emoji,
                        fontSize = 34.sp
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = nombre,
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )

                    Text(
                        text = descripcion,
                        fontSize = 14.sp,
                        color = TextGray
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "$tiempo • $precio",
                        fontSize = 13.sp,
                        color = DeliveryDarkRed,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Button(
                onClick = onClick,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DeliveryRed
                ),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text(text = "Ver restaurante")
            }
        }
    }
}

@Composable
fun ProductoPedido(
    nombre: String,
    precio: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = nombre,
            color = TextDark,
            fontSize = 15.sp
        )

        Text(
            text = precio,
            color = TextDark,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun CardResumen(
    titulo: String,
    valor: String,
    emoji: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CardWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            modifier = Modifier.padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .clip(CircleShape)
                    .background(SoftRed),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = emoji,
                    fontSize = 26.sp
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = titulo,
                    fontSize = 16.sp,
                    color = TextGray
                )

                Text(
                    text = valor,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )
            }
        }
    }
}