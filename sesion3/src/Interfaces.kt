import kotlin.math.PI

interface Calculadora{

    fun add(num1: Int, num2: Int): Int

    fun max(num1: Int, num2: Int): Int

    fun mix(num1: Int, num2: Int): Int
}

class MiCalculadora: Calculadora{
    override fun add(num1: Int, num2: Int): Int {
        return num1 + num2
    }

    override fun max(num1: Int, num2: Int): Int {
        if (num1 > num2) return num1
        else return num2
    }

    override fun mix(num1: Int, num2: Int): Int {
        if (num1 < num2 ) return num1
        else return num2
    }
}

interface Area {
    fun area(): Double
}

class Rectangulo(val ancho: Double, val altura: Double): Area {
    override fun area(): Double {
        return ancho * altura
    }
}
class Circulo(val radio: Double): Area {
    override fun area(): Double {
        return radio * radio * PI
    }
}

fun main(args: Array<String>) {
    val calc = MiCalculadora()
    println(calc.add(2, 3))
    println(calc.max(6, 3))
    println(calc.mix(2, 3))

    val rect = Rectangulo(6, 12.5)
    val circ = Circulo(8.5)
    println(rect.area())
    println(circ.area())
}