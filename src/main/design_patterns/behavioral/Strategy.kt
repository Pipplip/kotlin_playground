// Das Strategy-Pattern kapselt veränderbares Verhalten in eigene Klassen oder Funktionen,
// sodass man es zur Laufzeit austauschen kann, ohne die Hauptklasse zu ändern.

// PaymentStrategy kapselt die Zahlungslogik
interface PaymentStrategy {
    fun pay(amount: Double)
}

// Konkrete Strategien implementieren die PaymentStrategy
class CreditCardPayment(private val cardNumber: String) : PaymentStrategy {
    override fun pay(amount: Double) {
        println("Paid $amount € with Credit Card: $cardNumber")
    }
}

class PaypalPayment(private val email: String) : PaymentStrategy {
    override fun pay(amount: Double) {
        println("Paid $amount € via PayPal account: $email")
    }
}

class BitcoinPayment(private val walletAddress: String) : PaymentStrategy {
    override fun pay(amount: Double) {
        println("Paid $amount € with Bitcoin wallet: $walletAddress")
    }
}

// Context-Klasse, die eine PaymentStrategy verwendet
class ShoppingCart {
    private val items = mutableListOf<Pair<String, Double>>() // Pair<Artikel, Preis>

    fun addItem(name: String, price: Double) {
        items.add(name to price)
    }

    fun totalAmount(): Double = items.sumOf { it.second }

    fun checkout(paymentStrategy: PaymentStrategy) {
        val amount = totalAmount()
        paymentStrategy.pay(amount)
        println("Checkout completed for total: $amount €")
    }
}
// Nutzung des Strategy-Patterns
fun main() {
    val cart = ShoppingCart()
    cart.addItem("Laptop", 1200.0)
    cart.addItem("Mouse", 25.0)

    // Kunde zahlt per Kreditkarte
    val creditCard = CreditCardPayment("1234-5678-9876-5432")
    cart.checkout(creditCard)
    println("-----")

    // Kunde zahlt per PayPal
    val paypal = PaypalPayment("user@example.com")
    cart.checkout(paypal)
    println("-----")

    // Kunde zahlt per Bitcoin
    val bitcoin = BitcoinPayment("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa")
    cart.checkout(bitcoin)

    // Output:
    //Paid 1225.0 € with Credit Card: 1234-5678-9876-5432
    //Checkout completed for total: 1225.0 €
    //-----
    //Paid 1225.0 € via PayPal account: user@example.com
    //Checkout completed for total: 1225.0 €
    //-----
    //Paid 1225.0 € with Bitcoin wallet: 1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa
    //Checkout completed for total: 1225.0 €

}

