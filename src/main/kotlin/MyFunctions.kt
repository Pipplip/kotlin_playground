package de.phbe

fun main(){

    // ++++++++++++++++++++++++++++++++++++++++++++++
    // Funktionen
    // ++++++++++++++++++++++++++++++++++++++++++++++
    val a = 1
    val b = 2

    println(sum(a, b))
    printSum(a, b)
    println(sum2(a, b))

    read(2)
    read(3, "Drei")

    // Extension Function
    println(33.toBinary()) // 100001
    val middleValue = listOf(1,2,3,4).middle()
    println(middleValue)

    // Extension Properties
    extPropTest()

    // Infix functions
    println(3 times "Hi ")
    val alice = Person("Alice")
    val bob = Person("Bob")
    // Infix-Aufruf statt alice.loves(bob)
    println(alice loves bob)  // Ausgabe: Alice loves Bob

    // scope functions
    val prd = ProductBuilder()
        .name("Auto")
        .priceInCent(199)
        .build()
}

// Funktionsparameter sind immer val (read only)
// Hier Rückgabewert Int
fun sum(a: Int, b: Int): Int {
    return a + b
}

// Hier ohne Rückgabewert. Unit ist wie void. Muss man nicht angeben.
fun printSum(a: Int, b: Int): Unit {
    println("sum of $a and $b is ${a + b}")
}

// Hat die Funktion nur eine Expression,
// kann man die Funktion kürzer schreiben
fun sum2(a: Int, b: Int) = a + b

// Default values der Parameter
fun read(number: Int, input: String = "Default", len: Int = input.length){
    println("$number, $input, $len")
}

// Extension Functions
// erweitern primitive Typen, als auch Klassen
// Hier return String und kann auf Int angewendet werden
// Sie verändern nicht die Zielklasse
// Der Receiver kann null sein
// z.B. 32.toBinary()
fun Int.toBinary(): String{
    return this.toString(2)
}

// Extension Functions können auf beliebige Typen definiert werden
fun <T> List<T>.middle(): T? {
    return if (this.size == 0){
        null
    }else
        this[this.size/2]
}

// Extension properties
// Analog einer extension function
// Verhalten nur über get() und set() implementierbar
val List<Any>.lastIndex: Int
    get() = size -1

fun extPropTest(){
    val n = listOf(1,2,3).lastIndex
    println(n)
}

// Infix Functions
// sind spezielle Funktionen, die du mit einer klaren,
// lesbaren Syntax ohne Punkt und Klammern aufrufen kannst —
// also wie einen Operator oder ein Schlüsselwort.
// Dienen nur um die Lesbarkeit zu erhöhen
infix fun Int.times(str: String) = str.repeat(this)
// in main: 3 times "Hi "

// in einer Klasse
class Person(val name: String){
    infix fun loves(other: Person): String{
        return "$name loves ${other.name}"
    }
}

// Preconditions
val isRunning = false
fun fktWithPreconditions(v: Int, name: String?){
    require(v % 2 == 0) // muss eine gerade Zahl sein
    requireNotNull(name) // name darf nicht null sein
    check(isRunning){ "Sorry, Still running..." }

    error("Execution overtime...")
}

// Scope functions
// z.B. Builder pattern
class Product
class ProductBuilder{
    private var name : String = ""
    private var price : Double = 0.0

    fun name(s: String) = this.apply { name = s }
    fun priceInCent(n:Int) = this.apply { price = n/100.0 }

    fun build() {
        println("Build $name with price in cents $price")
        Product()
    }
}

// apply = Ich möchte an mir arbeiten, und dabei ich bleiben
// also = An bzw. mit etwas soll gearbeitet werden ohne es zu transformieren
// run = Ich möchte mit mir arbeiten und mich verändern
// let = An etwas soll gearbeitet und dabei transformiert werden