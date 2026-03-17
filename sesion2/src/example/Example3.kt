package example

fun saludar(){
    println("Hola mundo")
}

fun saludar(nombre: String):String{
    return "Hola $nombre, mucho gusto."
}

fun sumar(num1: Int, num2: Int):Int = num1 + num2

fun login(name: String, pw: String):Boolean{
    return name == "admin" && pw == "123"
}

fun calculateSalary(salary: Double, inss: Double = 0.07):Double = salary * (1 + inss)

fun main(args: Array<String>) {
    saludar()
    println(saludar("Gabo"))
    println("Suma = ${sumar(1, 2)}")

    val status = login("admin", "123")
    println(status)

    val salary = calculateSalary(1000.0)
    println(salary)

    val netSalary = calculateSalary(1000.0 , 0.08)
    println(netSalary)

    val nSalary = calculateSalary(inss = 0.1, salary = 1000.0)
    println(nSalary)
}