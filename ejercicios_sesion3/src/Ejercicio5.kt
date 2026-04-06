class CuentaBancaria(
    private var numeroCuenta: String,
    private var saldo: Double
) {

    fun depositar(monto: Double) {
        if (monto > 0) {
            saldo += monto
            println("Depósito realizado: $monto")
        } else {
            println("El monto debe ser mayor que 0")
        }
    }

    fun retirar(monto: Double) {
        if (monto <= 0) {
            println("El monto debe ser mayor que 0")
        } else if (monto > saldo) {
            println("Saldo insuficiente")
        } else {
            saldo -= monto
            println("Retiro realizado: $monto")
        }
    }

    fun consultarSaldo() {
        println("Número de cuenta: $numeroCuenta")
        println("Saldo actual: $saldo")
    }
}

fun main() {
    val cuenta1 = CuentaBancaria("123456789", 500.0)

    cuenta1.consultarSaldo()
    cuenta1.depositar(200.0)
    cuenta1.retirar(100.0)
    cuenta1.consultarSaldo()
}