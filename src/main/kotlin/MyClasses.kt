package de.phbe

// ++++++++++++++++++++++++++++++++++++++++++++++
// Klassen bestehen aus:
// Konstruktoren,
// init Blöcke
// Properties
// Funktionen
// Innere Klassen
// Object Deklarationen
//
// Klassen sind final by default
// Alle Klassen erben von der Superklasse Any()
// D.h. alle Klassen können equals(), hashCode() und toString() überschreiben
// ++++++++++++++++++++++++++++++++++++++++++++++

// Primäre Konstruktoren sind Teil vom Klassenkopf
// Primärer Konstruktor ist optional: kann auch einfach "class Fish" heißen
// Parameter sind val (read only) by default
// Parameter können auch mit default Werten gesetzt werden
class Fish(name: String, age: Int)

// Bsp: Properties
class Fish2(val name: String){
    var age: Int = 0 // Felder heißen Properties in Kotlin

    // Klassenmethoden
    fun sayHello() {
        println("Hello, $name")
    }
}

// Sekundäre Konstruktoren
class Fish3(val name: String){
    var age: Int = 0

    // Sek. Konstruktor dürfen keine neuen Properties deklarieren, nur bestehende verändern, wenn diese
    // var sind, also read write
    // UND müssen den primären Konstr. aufrufen
    constructor(name: String, age: Int) : this(name) {
        this.age = age
        println("using secondary constructor")
    }

    fun introduce(){
        println("HI, AM $name, $age old")
    }
}

// Mit init
// init ist Teil des primören Konstruktors
// Können auf Parameter des prim . K. zugreifen
// Können read only Variablen befüllen und zugreifen
class Fish4(name: String){
    val upperName: String

    init {
        upperName = name.uppercase()
    }

    fun introduce(){
        println("Hi $upperName")
    }

}

// ++++++++++++++++++++++++++++++++++++++++++++++
// Vererbung
// Da eine Klasse final ist (by default) muss man sie für Vererbung öffnen (open)
// ++++++++++++++++++++++++++++++++++++++++++++++
open class Base: Any()
class DerivedClass : Base() // Vererbung mit Doppelpunkt

// Vererbung mit Parameter
// Wenn die Superklasse Parameter hat, müssen diese
// in der Subklasse deklariert werden
open class Base1(val id: String)
class DerivedClass1(id:String): Base1(id)

// was aber auch geht, also wenn man den Parameter nicht braucht, dann setzt man default values
class DerivedClass2 : Base1("")




fun main(){

    // Bsp. Fish2
    Fish2("Nemo").sayHello()

    val dory = Fish2("Dory")
    dory.age = 30
    dory.sayHello()

    // Bsp. Fish3
    val sam = Fish3("Sam", 30)
    sam.introduce()

    // Bsp. Fish4 mit init
    val tom = Fish4("Tom")
    tom.introduce()

    // Bsp. Vererbung
    val derived = DerivedClass1("2")


}


