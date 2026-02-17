package refactoring

// Mache main zu einem Composition Root, in dem alle Abhängigkeiten erstellt und zusammengeführt werden.
fun main(args: Array<String>){
    val context = ApplicationContext(args)
    runApplication(context)
}

// erstelle eine runApplication. Da diese in Integration-Test aufgerufen werden soll,
// nicht die main()
fun runApplication(context: ApplicationContext) {
    val service = context.service
    service.doSomething()
}

// ############################################
// Alle Abhängigkeiten werden in diesem Kontext erstellt und zusammengeführt.
class ApplicationContext(
    args: Array<String>,
    private val overrides: Overrides = Overrides()
) {
    val service = overrides.service ?: MyService() // wenn kein Override gesetzt ist, wird default MyService verwendet
}

// Diese Klasse ermöglicht es, Abhängigkeiten zu überschreiben, z.B. für Tests
data class Overrides {
    var service: MyService? = null
}

class MyService() {
    fun doSomething() {
        println("Doing something...")
    }
}