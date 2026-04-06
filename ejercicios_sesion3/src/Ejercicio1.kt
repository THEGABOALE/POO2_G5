class Estudiante(
    var nombre: String,
    var cif: String,
    var carrera: String,
    var anio: Int
) {
    fun mostrarDatos() {
        println("Nombre: $nombre")
        println("Cif: $cif")
        println("Carrera: $carrera")
        println("Año: $anio")
        println("----------------------")
    }
}

fun main() {
    val estudiante1 = Estudiante("Valeria Grijalva", "23021288", "Ingeniería en Sistemas", 3)
    val estudiante2 = Estudiante("Manuel Chamorro", "2023015", "Ingeniería en Sistemas", 3)
    val estudiante3 = Estudiante("Johaneris Ávalos", "2022030", "Ingeniería en Sistemas", 3)

    estudiante1.mostrarDatos()
    estudiante2.mostrarDatos()
    estudiante3.mostrarDatos()
}