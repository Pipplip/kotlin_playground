import java.nio.charset.Charset

fun main(){
    println("Bytes")
    val bytes: ByteArray = byteArrayOf(0x01, 0x02, 0x03, 0x04)
    val b1: Byte = 127 // 127 in Dezimal
    val b2: Byte = 0xFF.toByte() // 255 in Dezimal, aber als Byte konvertiert
    val b3: Byte = 0x20 // Hex -> 32 in Dezimal
    val b4: Byte = 0b00010000 // Binär -> 16 in Dezimal
    val myBytes = MyBytes()

    myBytes.byteToDecimal(b1)          // Ausgabe: 127
    myBytes.byteToDecimal(b2)          // Ausgabe: -1
    myBytes.byteToDecimal(b3)          // Ausgabe: 32
    myBytes.byteToDecimal(b4)          // Ausgabe: 16

    println("-".repeat(10))

    myBytes.byteToHex(b1)         // Ausgabe: 7F
    myBytes.byteToHex(b2)         // Ausgabe: FF
    myBytes.byteToHex(b3)        // Ausgabe: 20
    myBytes.byteToHex(b4)        // Ausgabe: 10

    println("-".repeat(10))

    myBytes.byteToUnsignedDecimal(b1)         // Ausgabe: 127
    myBytes.byteToUnsignedDecimal(b2)         // Ausgabe: 255
    myBytes.byteToUnsignedDecimal(b3)        // Ausgabe: 32
    myBytes.byteToUnsignedDecimal(b4)        // Ausgabe: 16

    println("-".repeat(10))

    myBytes.bytesToInt(b1)         // Ausgabe: 127
    myBytes.bytesToInt(b2)         // Ausgabe: 255
    myBytes.bytesToInt(b3)        // Ausgabe: 32
    myBytes.bytesToInt(b4)        // Ausgabe: 16

    println("-".repeat(10))

    // HEX bytes werden als decimal ausgegeben
    myBytes.printBytes(bytes)

}

class MyBytes{
    // Ein Byte besteht aus 8 Bits.
    // Wertebereich:
    // signed Byte: -128 … 127 (in Koltin immer signed)
    // unsigned Byte: 0 … 255

    // Wichtig: Zahlensysteme
    // 1. Dezimal (Basis 10): 0-9 in Koltin Standard, also z.B. Int = 10
    // 2. Binär (Basis 2): 0-1, in Koltin mit 0b vorangestellt
    // 3. Hexadezimal (Basis 16): 0-9, A-F, in Koltin mit 0x vorangestellt

    // Hexadezimale Darstellung in Kotlin
    // 1 Hex-Zeichen = 4 Bit
    // 2 Hex-Zeichen = 1 Byte
    // z.B: FF = 15×16 + 15 = 255
    // Werte >127 brauchen .toByte()
    // 0xFF = 255 ist z.B. nicht im signed Byte Bereich, deshalb muss man es explzit konvertieren


    fun byteToDecimal(b: Byte){
        println(b.toInt())
    }

    fun byteToHex(b: Byte){
        val hexString = "%02X".format(b)
        println(hexString)
    }

    fun byteToUnsignedDecimal(b: Byte){
        val unsigned = b.toInt() and 0xFF
        println(unsigned)
    }

    fun bytesToInt(b: Byte){
        val unsigned = b.toInt() and 0xFF
        println(unsigned)
    }

    fun byteArrayToString(bytesArray: ByteArray, charset: Charset = Charsets.UTF_8){
        val str = bytesArray.toString(charset)
        println(str)
    }

    fun stringToByteArray(str: String, charset: Charset = Charsets.UTF_8): ByteArray{
        return str.toByteArray(charset)
    }

    fun hexToBytes(bytesArray: ByteArray){
        println(bytesArray.toHex())
    }

    fun printBytes(bytesArray: ByteArray) {
        for (b in bytesArray) {
            println(b)
        }
    }

    fun compareBytesArrays(arr1: ByteArray, arr2: ByteArray): Boolean {
        return arr1.contentEquals(arr2)
    }

    fun ByteArray.toHex(): String =
        joinToString(" ") { "%02X".format(it) }

    // ByteArray -> to readable ASCII-Presentation
    fun ByteArray.toPrintableAscii(): String = joinToString("") {
        val c = it.toInt() and 0xFF
        if (c in 32..126) c.toChar().toString() else "."
    }

}