package refactoring

// Dependeny Injection ist die Technik (Aufruf des Konstruktors)
// Dependeny Inversion ist das Prinzip (Abstraktion als Konstruktor)

// Dependency Inversion Principle (DIP) besagt, dass hochrangige Module nicht von niederrangigen Modulen abhängen sollten.
// Abhängigkeiten sollten über Interfaces abstrahiert werden, nicht über konkrete Implementierungen.

// schlecht: OrderService hängt direkt von PaypalPaymentGateway ab.
 class OrderService(
     private val paypalPaymentGateway: PaypalPaymentGateway
 )

// ##################
// refactor:
interface PaymentGateway { // = High-Level Modul
    fun pay(amount: Double)
}

class PaypalPaymentGateway : PaymentGateway { ... } // = Low-Level Modul
class VisaPaymentGateway : PaymentGateway { ... } // = Low-Level Modul

class OrderService(
    private val paymentGateway: PaymentGateway // nur Abstraktion
)

// Injection sieht dann so aus:
fun main() {
    val paypalGateway = PaypalPaymentGateway()
    val orderService = OrderService(paypalGateway) // hier wird injiziert
}