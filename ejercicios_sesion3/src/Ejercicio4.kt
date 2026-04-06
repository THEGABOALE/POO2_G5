open class Empleado(
    var nombre: String,
    var salario: Double
)

class Programador(
    nombre: String,
    salario: Double,
    var lenguaje: String
) : Empleado(nombre, salario) {

    fun mostrarDatos() {
        println("Nombre: $nombre")
        println("Salario: $salario")
        println("Lenguaje: $lenguaje")
    }
}

fun main() {
    val programador1 = Programador("Valeria", 1200.0, "Kotlin")
    programador1.mostrarDatos()
}