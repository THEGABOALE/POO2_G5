class Estudiante{
    var nombre:String = ""
    var carnet:Int = 0
    var carrera:String = ""
    var año:Int = 0
    fun mostrarDatos(){
        println(nombre)
        println(carnet)
        println(carrera)
        println(año)
    }
}

fun main(args:Array<String>){
    val estudiante = Estudiante()
    estudiante.nombre = "Gabo"
    estudiante.carnet = 23021430
    estudiante.carrera = "Ing. Sistemas"
    estudiante.año = 2026

    val estudiante2 = Estudiante()
}