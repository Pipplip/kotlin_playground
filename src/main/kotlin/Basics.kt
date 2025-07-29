package de.phbe

import java.io.File
import java.io.FileOutputStream


// Konstanten
const val KONSTANTE = 1.23

// aliases
// kein neuer Typ, sondern nur ein neuer Name für einen Typ
typealias Centimeter = Int

fun main(args: Array<String>) {

    // ++++++++++++++++++++++++++++++++++++++++++++++
    // Variablen
    // ++++++++++++++++++++++++++++++++++++++++++++++
    val a: Int = 42 // val ist read only (in Java final)
    val b = 42      // Type muss nicht explizit angegeben werden

    var changeMe: Int = 42 // var: read write
    changeMe = 43

    // companion object entspricht einer static Klassenvariable
    println(StaticProperty.KONSTANTE_2)

    // ++++++++++++++++++++++++++++++++++++++++++++++
    // Datentypen
    // ++++++++++++++++++++++++++++++++++++++++++++++
    var bo: Boolean = true
    val c: Char = 'a' // '\u0040'
    val n8: Byte = 127
    val n16: Short = 32_767
    val n32: Int = 2_147_483_647
    val n64: Long = 9_223_372_036_854_775_807
    val u8: UByte = 255u
    val u16: UShort = 65_535u
    val u32: UInt = n32.toUInt() * 2u
    val u64: ULong = n64.toULong() * 2u
    val f32: Float = 3.141592f
    val f64: Double = 0.0
    val str: String = "abc"
    val something:Any = "foo" // Any kann alles sein, da wir aber String zuweisen wird daraus ein String

    // ++++++++++++++++++++++++++++++++++++++++++++++
    // Strings
    // ++++++++++++++++++++++++++++++++++++++++++++++
    println("Beispiel template expression $str ${str.length}")

    // raw string
    val rawString = """
        Ich bin ein raw string und bin sehr nützlich 
        für z.B. json, weil ich nicht explizit escapen muss
        {
            "id":123
        }
    """.trimIndent()
    println(rawString)

    // ++++++++++++++++++++++++++++++++++++++++++++++
    // Kontrollstrukturen (Verzweigungen)
    // ++++++++++++++++++++++++++++++++++++++++++++++
    val ks1 = 3
    val ks2 = 1

    if( ks1 > ks2 ){
        println("$ks1 ist größer als $ks2")
    }else{
        println("$ks1 ist kleiner als $ks2")
    }

    // Zuweisung geht auch:
    val ksResult = if(ks1 > ks2){
        ks1
    }else{
        ks2
    }

    // ternärer Operator
    val ksResult2 = if(ks1 > ks2) ks1 else ks2

    // when ist ähnlich wie switch case, was es in Kotlin nicht gibt
    val when1 = "when"
    when(when1) {
        "when" -> { println("When eingegeben") }
        else -> { println("When nicht eingegeben") }
    }

    // when ohne Audruck im Kopf
    // beliebige Prüfung
    when {
        "when".length > 3 ->  println("Eingabewert hat mehr als drei Stellen")
        else -> println("keine Bedingung hat gepasst")
    }

    // Zuweisung geht auch
    val zuweis: Any = "foo"
    val whenRes = when(zuweis) {
        is String -> zuweis.startsWith("prefix")
        else -> false
    }
    println(whenRes)

    // ++++++++++++++++++++++++++++++++++++++++++++++
    // Schleifen
    // ++++++++++++++++++++++++++++++++++++++++++++++
    // Über eine range (1..5), welches ein Range Object ist
    for (i in 1..5) {
        println("i = $i")
    }

    for (i in 1 until 4){
        println("i = $i")
    }

    for (i in 6 downTo 0 step 2) {
        println("i = $i")
    }

    for (n in (1..3).reversed()){
        println("n = $n")
    }

    // for each
    for(arg in args) {
        println("arg = $arg")
    }

    for (i in args.indices){
        println("i = ${args[i]}")
    }

    // while
    var count = 5
    while (count > 1){
        println(count)
        count--

        if(count == 3) break
        if(count == 4) continue
    }

    // while with breaker
    // break um aus beiden loops zu kommen
    var outer = 5
    var inner = 5
    outer@while (outer > 1){
        outer--
        while (inner > 1){
            inner--
            if(outer == 3) break@outer
        }
        inner = 5
    }

    // do while
    count = 5
    do{
        println(count)
        count--
    }while (count > 1)

    // ++++++++++++++++++++++++++++++++++++++++++++++
    // Exceptions
    // ++++++++++++++++++++++++++++++++++++++++++++++
    try {
        // some code
    }catch (e: Exception){
        println(e.message)
    }finally {
        // do something
    }

    // try with resources (Schluesselwort use)
    val file = File("out.txt")
    FileOutputStream(file).use{
        it.write(64)
        Thread.sleep(10_000)
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++
    // aliases
    // ++++++++++++++++++++++++++++++++++++++++++++++
    val threeCenti : Centimeter = 3

    // ++++++++++++++++++++++++++++++++++++++++++++++
    // Nullable Types
    // ++++++++++++++++++++++++++++++++++++++++++++++
    // In Kotlin ist nichts null.
    // Wenn etwas null sein darf, muss man es explizit sagen
    // mit einem Fragezeichen hinter dem Typ z.B. String?
    // Der Elvis Operator (?:) prüft auf null und gibt Defaultwerte bei null zurück
    val name: String? = null
    val length = name?.length // wenn name null ist, wird length auch null (anstatt Exception zu werfen)
    println(length)
    val length2 = name?.length ?: 0
    println(length2)

    // ++++++++++++++++++++++++++++++++++++++++++++++
    // Generics
    // ++++++++++++++++++++++++++++++++++++++++++++++
    // Ein Generic ist ein generischer Typ-Parameter, der für einen beliebigen Datentyp steht
    // Er wird verwendet, um Funktionen oder Klassen generisch und damit flexibel und wiederverwendbar zu machen
    // Stell dir eine Schachtel (Box) vor, die du mit allem Möglichen befüllen kannst –
    // Äpfel, Bücher oder Spielzeug. Ein Generic ist wie diese Schachtel:
    // Sie ist nicht auf einen bestimmten Inhalt festgelegt, sondern funktioniert mit allem, solange du beim Ein- und Auspacken den Typ beachtest.
    printItem("Gen1")
    printItem(123)
}

fun <T> printItem(item: T) {
    println(item)
}

class StaticProperty(){
    // companion object entspricht einer static Klassenvariable
    companion object {
        const val KONSTANTE_2 = 3.22
    }
}