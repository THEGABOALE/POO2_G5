package example

fun evaluarNota(nota: Int) {
    when (nota) {
        in 0..69 -> println("$nota Aprendizaje Inicial")
        in 70..79 -> println("$nota Aprendizaje Fundamental")
        in 80..89 -> println("$nota Aprendizaje Satisfactorio")
        in 90..100 -> println("$nota Aprendizaje Avanzado")
        else -> println("$nota invalida")
    }
}

fun main(args: Array<String>) {
    println("Dani posee una nota: ${evaluarNota(69)}")
}
