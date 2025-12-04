// Problem: You have an existing class with a specific interface, but you need it to work with another interface that your code expects.
// You want to create a bridge between the two interfaces without modifying the existing class.
// Lösung: Implementiere einen Adapter, der die Methoden des bestehenden Interfaces in das erwartete Interface übersetzt.
// Beispiel:

// Alt: Vorhandenes Interface
interface OldInterface {
    fun specificRequest(): String
}

// Alt: Vorhandene Klasse, die das alte Interface implementiert
class ExistingClass : OldInterface {
    override fun specificRequest(): String {
        return "Response from ExistingClass"
    }
}

// Neu: Neues Interface, welches vom client erwartet wird
interface NewInterface {
    fun request(): String
}

// Neu: Adapter Klasse die das neue Interface implementiert und eine Instanz der bestehenden Klasse verwendet
class Adapter(private val existingClass: OldInterface) : NewInterface {
    override fun request(): String {
        // Adapter übersetzt den Aufruf
        return existingClass.specificRequest()
    }
}

// Nutzung des Adapter Design Patterns
fun main() {
    val existing = ExistingClass()
    val adapter: NewInterface = Adapter(existing)
    println(adapter.request()) // Output: Response from ExistingClass
}