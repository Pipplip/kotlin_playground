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

    println(33.toBinary()) // 100001
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
// z.B. 32.toBinary()
fun Int.toBinary(): String{
    return this.toString(2)
}