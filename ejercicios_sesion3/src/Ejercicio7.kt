interface Vehiculo {
    fun mover()
}

class Camion : Vehiculo {
    override fun mover() {
        println("El camión está moviendo la carga.")
    }
}

class Motocicleta : Vehiculo {
    override fun mover() {
        println("La motocicleta se está moviendo.")
    }
}

fun main() {
    val camion1 = Camion()
    val moto1 = Motocicleta()

    camion1.mover()
    moto1.mover()
}