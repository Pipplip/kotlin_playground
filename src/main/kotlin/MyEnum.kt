package de.phbe

// Standard ENUM
enum class Prio{
    HIGH, MEDIUM, LOW
}

// ENUM mit Eigenschaften und Konstruktor
enum class Color(val rgb: Int){
    RED(0xFF0000),
    GREEN(0x00FF00),
    BLUE(0x0000FF),
}
enum class HttpStatus(val code: Int, val description: String) {
    OK(200, "Success"),
    NOT_FOUND(404, "Not Found"),
    INTERNAL_ERROR(500, "Server Error")
}


// ENUM mit Methoden
enum class Operation {
    ADD {
        override fun apply(a: Int, b: Int) = a + b
    },
    SUBTRACT {
        override fun apply(a: Int, b: Int) = a - b
    };

    abstract fun apply(a: Int, b: Int): Int
}

// ENUM mit Interfaces
interface Printable {
    fun printInfo()
}
enum class Priority : Printable {
    HIGH {
        override fun printInfo() = println("High priority")
    },
    LOW {
        override fun printInfo() = println("Low priority")
    }
}

fun main(){
    val prio = Prio.valueOf("MEDIUM")
    println(prio) // MEDIUM

    // Bsp. mit Eigenschaften
    val hexCodeOfRed = Color.RED.rgb.toString(16)
    println("Color: ${Color.RED} has value $hexCodeOfRed")

    val httpOk = HttpStatus.OK
    println("HTTP Code: ${httpOk.code}, message: ${httpOk.description}")

    // Bsp. mit Methoden
    val op1 = Operation.ADD
    println("5 + 3 = ${op1.apply(5, 3)}")     // 8

    // Bsp. mit Interfaces
    val prio1 = Priority.HIGH
    prio1.printInfo()
}