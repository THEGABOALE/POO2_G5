class Estudiante {
    var nombre = ""
    var edad = 0

    fun estudiar(){
        println("$nombre está estudiando...")
    }
}

class Asignatura(private val nombre: String, private val precio: Double) {

    fun getNombre(): String {
        return "La asignatura se llama: $nombre"
    }

    fun getPrecio(): Double {
        return precio
    }
}

fun main() {
    val estudiante = Estudiante()

    estudiante.nombre = "Gabriel Garcia"
    estudiante.edad = 19
    estudiante.estudiar()

    estudiante.nombre = "Manolo Pinolo"
    estudiante.edad = 27
    estudiante.estudiar()

    val poo = Asignatura("POO", 250.0)
    println(poo.getNombre())
    println(poo.getPrecio())
}

