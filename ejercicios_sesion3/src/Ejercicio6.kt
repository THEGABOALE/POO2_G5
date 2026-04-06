class Taxi(
    var placa: String,
    var conductor: String,
    var modelo: String
) {
    fun iniciarServicio() {
        println("El taxi con placa $placa, modelo $modelo, conducido por $conductor, ha iniciado servicio.")
    }
}

fun main() {
    val taxi1 = Taxi("GR 1234", "Valeria Grijalva", "Toyota Corolla")
    val taxi2 = Taxi("GR 5678", "Manuel Chamorro", "Hyundai Accent")
    val taxi3 = Taxi("GR 9012", "Carlos Ruiz", "Kia Rio")

    taxi1.iniciarServicio()
    taxi2.iniciarServicio()
    taxi3.iniciarServicio()
}