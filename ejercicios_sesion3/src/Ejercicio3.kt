class Bus(
    var numeroRuta: Int,
    var capacidad: Int,
    var conductor: String
) {
    fun iniciarRuta() {
        println("Ruta: $numeroRuta")
        println("Capacidad: $capacidad")
        println("Conductor: $conductor")
        println("La ruta ha iniciado.")
        println("-------------------")
    }
}

fun main() {
    val bus1 = Bus(101, 30, "Juan Pérez")
    val bus2 = Bus(205, 40, "María López")

    bus1.iniciarRuta()
    bus2.iniciarRuta()
}