package de.phbe

// Lambdas sind anonyme Funktionen, ohne einen expliziten Namen zu definieren

// Funktionale Interfaces - Vorwissen zu Lambdas
// SAM = Single-Abstract-Method = ein Interface, dass genau eine abstract Methode hat wie z.B: Runnable oder Comparator
// Kotlin erlaubt dir, ein Lambda statt eines Objekts zu übergeben,
// das ein SAM-Interface implementiert.
// Kotlin konvertiert das Lambda automatisch zu einem Objekt,
// das dieses Interface implementiert.
// Kotlin braucht keine funktionale Interfaces wie java

// Sender		  []-> erwartet nichts, gibt aber was zurück
// Consumer	    ->[]   erwartet etwas, gibt nichts zurück
// Function     ->[]-> erwartet etwas, verarbeitet es und gibt es aus
// Predicate	->[]-> Gibt true oder false zurück

// +++++++++++++++++++++++++++++++++++++++++
// Lambda allg. Syntax:
// val lambdaName: (ParameterTyp) -> RückgabeTyp = { parameterName -> Rückgabewert }
// Der Ausdruck befindet sich immer in {} Klammern

fun main() {
    // SENDER Beispiel
    val sender: () -> String = { "Hallo von Sender!" }
    println(sender()) // Hallo von Sender
    // Hier: die Variable heisst sender
    // Der Typ ist () -> String (also ein Lambda Ausdruck)
    // {...} ist der Block in dem Code ausgeführt wird.
    // Der Body kann richtige Abfragen haben, z.B. if...

    // CONSUMER Beispiel: empfängt und verarbeitet einen Wert
    val consumer: (String) -> Unit = { message -> println("Consumer hat empfangen: $message") }

    // FUNCTION Beispiel:
    // it verwendet man, wenn es nur genau einen Parameter gibt
    val stringLength: (String) -> Int = { it.length }  // transformiert einen String in seine Länge
    // oder equivalent
    val stringLength2: (String) -> Int = { str -> str.length}

    // PREDICATE: prüft, ob ein String leer ist
    val isEmpty: (String) -> Boolean = { it.isEmpty() }

    // --------------------------------------------------
    // Bsp: Vorwissen
    val message = sender()              // Sender sendet
    consumer(message)                   // Consumer empfängt

    val length = stringLength(message)  // Function transformiert
    println("Länge der Nachricht: $length")

    val leer = isEmpty(message)         // Predicate prüft
    println("Ist die Nachricht leer? $leer")

    // --------------------------------------------------
    // Bsp: Function references
    var test: (Any) -> Unit
    test = ::fktRefTest
    test("Hallo") // Hallo
    test(1234) // 1234

    var test2 = ::fktRefTest
    test2("Test2")

    val myConsole = MyConsole()
    test = myConsole::printConsole
    test(8899) // >> 8899

    val test3 = MyConsole::directPrint
    test3("Direct Print")

    // Bsp. hinter Funktionsaufruf
    behind("foo") {s -> s.isNotEmpty()}
    // anstatt
    behind("foo", {s -> s.isNotEmpty()})

    // --------------------------------------------------
    // Bsp: Lambda with receivers
    val str = buildString {
        append("Hallo")
        append("Welt")
    }
    println(str)
    // ohne Lambda wäre der Code so:
    val builder = StringBuilder()
    builder.append("Hello")
    builder.append("world")
    val str2 = builder.toString()

    // --------------------------------------------------
    // Bsp: Inline functions
    logAndRun {
        println("Running...")
    }
}


// --------------------------
// Function references :: Notation
// allg: Kotext::referenzierte Funktion
fun fktRefTest(input: Any): Unit{
    println(input)
}
// anderer Kontext: Hier eine Klasse
class MyConsole{
    fun printConsole(input: Any): Unit = println(">> $input")

    companion object{
        fun directPrint(input: Any): Unit{
            println(input)
        }
    }

}

fun behind(str: String, pred: (String) -> Boolean) = pred(str)

// --------------------------
// Lambda with receivers - Nützlich für DSL (Domain specific Language)
fun buildString(actions: StringBuilder.() -> Unit):String{
    val builder = StringBuilder()
    builder.actions()
    return builder.toString()
}

// --------------------------
// Inline functions
// verbessern die Performance
inline fun logAndRun(action: () -> Unit){
    println("Start")
    action()
    println("End")
}