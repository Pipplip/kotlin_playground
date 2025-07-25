package de.phbe

import sun.security.util.Length
import java.util.UUID

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
// Parameter können auch mit default Werten gesetzt werden
// Parameter sind nicht automatisch Properties
class Fish(name: String, age: Int)

// Bsp: Properties
class Fish2(val name: String){
    var age: Int = 0 // Felder heißen Properties in Kotlin

    // Klassenmethoden
    fun sayHello() {
        println("Hello, $name")
    }
}

// Bsp2: Properties + Konstruktor Parameter
class Shark(name: String, val color: String, var eats: String){
    // name könnte man in der main weder lesen noch schreiben
    // da name weder var noch val ist, wird es nicht als Property angelegt
    // also es gibt kein Feld "name" in der Klasse
    // val s = Shark()
    // s.name // FEHLER

    // color könnte man in der main nur lesen, weil dann eine Property angelegt wird:
    // val color entspricht in Java: private final String name; mit Getter getName()

    // eats kann man in der main lesen und schreiben
    // var age entspricht in Java: private int age; + getAge() + setAge(int)

    // siehe Fish4 für ein weiters Bsp.
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
// init ist Teil des primären Konstruktors
// Können auf Parameter des primären K. zugreifen
// Können read only Variablen befüllen und zugreifen
class Fish4(name: String){
    val upperName: String

    init {
        // Macht hier Sinn, da der Konstruktorparameter weder val noch var ist
        upperName = name.uppercase()
    }

    fun introduce(){
        println("Hi $upperName")
    }
}

// weiteres Beispiel für Spring
class HelloService(){
    fun greet(): String = "Hello from service!"
}
class RestControllerBeispiel(service: HelloService) { // <-- kein val oder var hier!
    // service wird nur intern verwendet, also:
    private val helloService = service

    //@GetMapping("/hello")
    fun sayHello(): String {
        return helloService.greet()
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
// Oder man macht einen sekun. Konstruktor und ruft super auf
class DerivedClass3 : Base1{
    constructor(id:String) : super(id)
}

// Methoden überschreiben
open class Base2{
    open fun sayHello(){
        println("Base Funktion")
    }

    fun functionCallInSubClass() {
        println("FunctionCallInSubClass")
    }
}

class Dervived4 : Base2(){
    override fun sayHello() {
        println("subclass Funktion")
    }
}

// ++++++++++++++++++++++++++++++++++++++++++++++
// Companion object
// Eine Art Singleton
// Also eine Abbildung statischer Variablen und Methoden
// ++++++++++++++++++++++++++++++++++++++++++++++
class CompTest(){
    companion object{
        const val PI = 3.14
        fun calc(diameter: Double): Double = diameter * PI
    }
}

// ++++++++++++++++++++++++++++++++++++++++++++++
// Property accessors
// Properties haben automatisch set und get accessors, die angepasst werden können
// field ist vorgegeben und entspricht der Variablen
// ++++++++++++++++++++++++++++++++++++++++++++++
class PropAccessTest(){
    var name: String = ""
        get() {
            return field
        }
//        set(value) {
//            field = value
//        }
        set(value){
            field = validateName(value)
        }

    // Anwendungsfall: validiere den Namen beim Setzen
    fun validateName(value: String) = if(value.isBlank()) "Default" else value
}

// ++++++++++++++++++++++++++++++++++++++++++++++
// Abstract classes
// Können nicht initialisiert werden und sind automatisch "open"
// ++++++++++++++++++++++++++++++++++++++++++++++
abstract class Base3Abs(protected val length: Int){
    abstract val name: String
    abstract fun generateId(): String
}

class IdGenerator(override val name: String, length: Int) :  Base3Abs(length){
    override fun generateId(): String = UUID.randomUUID().toString().substring(0, length)
}


// ++++++++++++++++++++++++++++++++++++++++++++++
// Inner classes
// haben Zugriff auf die Klasse, die sie erstellt hat
// ++++++++++++++++++++++++++++++++++++++++++++++
class OuterClass{
    private val id = "123"

    inner class InnerClass{
        fun printId(){
            println("ID ist: $id")
        }
    }
}

// ++++++++++++++++++++++++++++++++++++++++++++++
// Anonyme Classes
// Eine anonyme Klasse ist eine einmalige Implementierung einer Schnittstelle
// oder abstrakten Klasse, ohne dass du einen neuen Klassennamen erstellst.
// In Kotlin als Deklaration eines "object"
// ++++++++++++++++++++++++++++++++++++++++++++++
interface ClickListener{
    fun onClick()
}
// weiter in der main Funktion: val listener


// ++++++++++++++++++++++++++++++++++++++++++++++
// Data classes
// schnelle Deklaration zur reinen Datenhaltung
// ähnlich einem DTO in Java
// Gro0er Vorteil: Datenklassen kann man destructurieren, d.h.
// einzelne Daten werden in Komponenten zerlegt
// ++++++++++++++++++++++++++++++++++++++++++++++
data class MyDataClass(val isbn: String, val name: String)

// Operator Overloading:
// um benutzerdefinierte Implementierungen für Standardoperatoren
// wie +, -, *, [], ==, etc. zu definieren.
data class Vector(val x:Int, val y:Int) {
    // Operator '+' überladen für Vektoraddition
    operator fun plus(other: Vector): Vector {
        return Vector(x + other.x, y + other.y)
    }

    // Operator '-' überladen für Vektorsubtraktion
    operator fun minus(other: Vector): Vector {
        return Vector(x - other.x, y - other.y)
    }
}

// ++++++++++++++++++++++++++++++++++++++++++++++
// Sealed classes
// Können nur im gleichen Package vererbt werden
// d.h. man hat eine feste Menge an Subtypen
// Vorteil: Man kann ein when ohne else machen
// ++++++++++++++++++++++++++++++++++++++++++++++
sealed interface MySealedInterface
sealed class BaseError
class X : BaseError(), MySealedInterface
class Y : BaseError(), MySealedInterface


// ++++++++++++++++++++++++++++++++++++++++++++++
// Delegation
// Delegation in Kotlin bedeutet, dass eine Klasse die Implementierung
// von Funktionen oder Eigenschaften an ein anderes Objekt übergibt,
// statt sie selbst zu implementieren – z.B. über das Schlüsselwort by
// ++++++++++++++++++++++++++++++++++++++++++++++
interface Printer{
    fun printMessage()
    fun printFooter()
}
class RealPrinter : Printer{
    override fun printMessage() {
        println("Hello, AM the real printer")
    }

    override fun printFooter() {
        println("Footer, AM the real printer")
    }
}
class DelegatingPrinter(printer: Printer) : Printer by printer{
    override fun printFooter() {
        println("Den Footer zeige ich selbst. Gez. DelegatingPrinter")
    }
}

fun main(){

    // Bsp. Fish2
    Fish2("Nemo").sayHello()

    val dory = Fish2("Dory")
    dory.age = 30
    dory.sayHello()

    // ++++++++++++++++++++++++++++++++++++++++++++++
    // Bsp. Fish3
    val sam = Fish3("Sam", 30)
    sam.introduce()

    // ++++++++++++++++++++++++++++++++++++++++++++++
    // Bsp. Fish4 mit init
    val tom = Fish4("Tom")
    tom.introduce()

    // ++++++++++++++++++++++++++++++++++++++++++++++
    // Bsp. Vererbung
    val derived = DerivedClass1("2")

    Dervived4().functionCallInSubClass() // FunctionCallInSubClass
    Base2().sayHello() // Base Funktion
    Dervived4().sayHello() // subclass Funktion

    // ++++++++++++++++++++++++++++++++++++++++++++++
    // Bsp. Comanion Object
    val comp = CompTest()
    println(CompTest.calc(100.toDouble())) // 314.0

    // ++++++++++++++++++++++++++++++++++++++++++++++
    // Bsp. Abstract
    // val abs = Base3Abs(3) // compile error!
    val abs = IdGenerator("ABC", 4)
    println(abs.generateId())

    // ++++++++++++++++++++++++++++++++++++++++++++++
    // Bsp. Inner class
    val outer = OuterClass()
    val inner = outer.InnerClass()
    inner.printId()

    // ++++++++++++++++++++++++++++++++++++++++++++++
    // Bsp. anonyme class
    val listener = object : ClickListener{
        override fun onClick() {
            println("clicked")
        }
    }
    listener.onClick()

    // ++++++++++++++++++++++++++++++++++++++++++++++
    // Bsp. data class
    val book1 = MyDataClass("isbn1", "Lord of the Rings")
    println(book1)
    val book2 = book1.copy()
    println(book1 == book2) // true
    println(book1 === book2) // false

    // Destructuring
    val book3 = MyDataClass("isbn3", "The Hobbit")
    val (i,n) = book3
    println(i) // isbn3

    // ++++++++++++++++++++++++++++++++++++++++++++++
    // Bsp: Operator overloading
    val a = Vector(3, 4)
    val b = Vector(1, 2)
    val sum = a + b//  nutzt operator plus
    println(sum)

    // ++++++++++++++++++++++++++++++++++++++++++++++
    // Bsp: Sealed classes
    val seal: MySealedInterface = X()
    when(seal){
        is X -> { println("X")}
        is Y -> { println("Y")}
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++
    // Bsp: Delegation
    val realPrinter1 = RealPrinter()
    val delegatePrinter = DelegatingPrinter(realPrinter1)
    delegatePrinter.printMessage() // Ruft intern realPrinter.printMessage() auf
    delegatePrinter.printFooter()
}


