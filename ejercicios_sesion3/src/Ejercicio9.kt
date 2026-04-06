open class Persona(
    var nombre: String,
    var edad: Int
)

class Docente(
    nombre: String,
    edad: Int,
    var materia: String
) : Persona(nombre, edad)

class Estudiante1(
    nombre: String,
    edad: Int,
    var carrera: String
) : Persona(nombre, edad)

fun main() {
    val docente1 = Docente("José Durán", 40, "Programación")
    val estudiante1 = Estudiante1("Valeria Grijalva", 19, "Ingeniería en Sistemas")

    println("Docente: ${docente1.nombre}, Edad: ${docente1.edad}, Materia: ${docente1.materia}")
    println("Estudiante: ${estudiante1.nombre}, Edad: ${estudiante1.edad}, Carrera: ${estudiante1.carrera}")
}