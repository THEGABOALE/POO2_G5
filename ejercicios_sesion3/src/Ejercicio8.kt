data class Pedido(
    var cliente: String,
    var platillo: String,
    var precio: Double
)

fun main() {
    val pedido1 = Pedido ("Ana", "Carne asada", 120.0)
    val pedido2 = Pedido ("Luis", "Enchilada", 90.0)
    val pedido3 = Pedido ("María", "Tajadas con queso", 70.0)

    println("Cliente: ${pedido1.cliente}, pedido: ${pedido1.platillo}, precio: ${pedido1.precio}")
    println("Cliente: ${pedido2.cliente}, pedido: ${pedido2.platillo}, precio: ${pedido2.precio}")
    println("Cliente: ${pedido3.cliente}, pedido: ${pedido3.platillo}, precio: ${pedido3.precio}")
}
