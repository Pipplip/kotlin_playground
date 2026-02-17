package design_patterns.structural

// Ein Facade bietet eine einheitliche Schnittstelle für ein komplexes Subsystem.
// Beispiel Online Shop mit mehreren Subsystemen: Lagerverwaltung, Zahlungsabwicklung, Versanddienstleister.

class InventorySystem {
    fun checkStock(itemId: String): Boolean {
        println("Checking stock for item: $itemId")
        return true // Wir gehen davon aus, dass der Artikel auf Lager ist
    }
}

class PaymentSystem {
    fun pay(amount: Double) {
        println("Processing payment of \$$amount")
    }
}

class ShippingSystem {
    fun shipItem(itemId: String, address: String) {
        println("Shipping item: $itemId to address: $address")
    }
}

// Problem: Client müsste alles einzeln aufrufen z.B.
// val inventory = Inventory()
// val payment = Payment()
// val shipping = Shipping()
//
// if (inventory.checkStock("Laptop")) {
//    payment.pay(1200.0)
//    shipping.ship("Laptop")
//}

// Lösung: Facade Klasse
class OnlineShopFacade {
    private val inventory = InventorySystem()
    private val payment = PaymentSystem()
    private val shipping = ShippingSystem()

    fun buyProduct(itemId: String, amount: Double, address: String) {
        if (inventory.checkStock(itemId)) {
            payment.pay(amount)
            shipping.shipItem(itemId, address)
            println("Order placed successfully!")
        } else {
            println("Item $itemId is out of stock.")
        }
    }
}

// Nutzung des Facade-Musters
fun main() {
    val shop = OnlineShopFacade()
    shop.buyProduct("12345", 99.99, "123 Main St, Anytown, USA")
}