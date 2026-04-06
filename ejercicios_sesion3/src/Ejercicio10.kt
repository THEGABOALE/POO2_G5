class Libro(
    var titulo: String,
    var autor: String,
    var anioPublicacion: Int
) {
    fun mostrarInformacion() {
        println("Título: $titulo")
        println("Autor: $autor")
        println("Año de publicación: $anioPublicacion")
        println("--------------------------")
    }
}

fun main() {
    val libro1 = Libro("El principito", "Antoine De Saint-Exupéry", 1943)
    val libro2 = Libro("Azul", "Rubén Darío", 1888)
    val libro3 = Libro("Cien años de soledad", "Gabriel García Márquez", 1967)
    val libro4 = Libro("La Metamorfosis", "Franz Kafka", 1915)

    libro1.mostrarInformacion()
    libro2.mostrarInformacion()
    libro3.mostrarInformacion()
    libro4.mostrarInformacion()
}