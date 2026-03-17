package example

fun main(args: Array<String>) {
    val age: Int = 19
    val name: String = "Gabo"
    val salary: Double = 1250.0
    val isSingle: Boolean = true
    println("Nombre $name tiene $age.")
    println("Posee un de $ $salary semanal")

    if (isSingle)
        println("Papoi")
    else
        println("No papoi")

    println("$name a tenido estos culos: ")
    for(i in 1..10 step 2) {
        println("Culo # $i")
    }

    println("Mientras espero a la indicada disfruto a las equivocadas")
    for(i in 10 downTo 1 step 2) {
        println("Equivocada Disfrutada $i")
    }
    println("Bruh sos ING, nunca has tocado una mujer")
}