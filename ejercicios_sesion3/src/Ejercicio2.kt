data class Producto(
    var nombre: String,
    var precio: Double,
    var cantidad: Int
)

fun main() {
    val producto1 = Producto("Arroz", 25.0, 10)
    val producto2 = Producto("Frijoles", 30.0, 8)
    val producto3 = Producto("Azúcar", 20.0, 12)
    val producto4 = Producto("Aceite", 75.0, 5)
    val producto5 = Producto("Jabón", 15.0, 20)

    println(producto1)
    println(producto2)
    println(producto3)
    println(producto4)
    println(producto5)
}