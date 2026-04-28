package ni.edu.uam.navegacionpantallas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

private val DeliveryRed = Color(0xFFE53935)
private val DeliveryDarkRed = Color(0xFFC62828)
private val DeliveryBackground = Color(0xFFFFF7F6)
private val CardWhite = Color(0xFFFFFFFF)
private val TextDark = Color(0xFF222222)
private val TextGray = Color(0xFF666666)
private val PromoYellow = Color(0xFFFFD54F)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = DeliveryBackground
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
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(DeliveryBackground)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            EncabezadoPrincipal()
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
            Button(
                onClick = {
                    navController.navigate("perfil")
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DeliveryRed
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = "Ver mi perfil")
            }
        }
    }
}

@Composable
fun PantallaDetalle(navController: NavHostController) {
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
                text = "Revisá tu orden antes de continuar",
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(70.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color(0xFFFFEBEE)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "🍕",
                                fontSize = 36.sp
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column {
                            Text(
                                text = "Pizza Express",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextDark
                            )

                            Text(
                                text = "Pedido seleccionado",
                                fontSize = 14.sp,
                                color = TextGray
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

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

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Envío",
                            color = TextGray
                        )

                        Text(
                            text = "C$35",
                            color = TextDark,
                            fontWeight = FontWeight.Medium
                        )
                    }

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
                            text = "C$530",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = DeliveryRed
                        )
                    }
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE)),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp)
                ) {
                    Text(
                        text = "Tiempo estimado",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = TextDark
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Tu pedido llegaría aproximadamente en 25 a 35 minutos.",
                        color = TextGray
                    )
                }
            }
        }

        item {
            Button(
                onClick = {
                    navController.navigate("perfil")
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
fun PantallaPerfil(navController: NavHostController) {
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
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )

            Text(
                text = "Resumen de usuario y actividad",
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
                            .size(90.dp)
                            .clip(CircleShape)
                            .background(DeliveryRed),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "G",
                            color = Color.White,
                            fontSize = 38.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Gabriel García",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )

                    Text(
                        text = "Usuario activo de FoodGo",
                        color = TextGray
                    )
                }
            }
        }

        item {
            CardResumen(
                titulo = "Pedidos realizados",
                valor = "12",
                emoji = "🛵"
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
                    navController.navigate("detalle")
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Ver último pedido",
                    color = DeliveryRed
                )
            }
        }
    }
}

@Composable
fun EncabezadoPrincipal() {
    Column {
        Text(
            text = "FoodGo",
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold,
            color = DeliveryRed
        )

        Text(
            text = "Pedí tu comida favorita en minutos",
            fontSize = 16.sp,
            color = TextGray
        )
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
                        .background(Color(0xFFFFEBEE)),
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
                Text(text = "Ver pedido")
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
                    .background(Color(0xFFFFEBEE)),
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