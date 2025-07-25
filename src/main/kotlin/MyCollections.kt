package de.phbe

fun main(){
    // ++++++++++++++++++++++++++++++++++++++++++++++
    // Arrays
    // werden durch Array<T> abgebildet und sind iterable
    // ++++++++++++++++++++++++++++++++++++++++++++++
    val anArr: Array<Int> = arrayOf(1,2,3,4,5)
    val anArr2: IntArray = intArrayOf(1,2,3,4,5)
    val anArr3: Array<Int> = (1..5).toList().toTypedArray()
    val anArr4: IntArray = (1..10).toList().toIntArray()

    println(anArr.contentToString()) // [1, 2, 3, 4, 5]

    // Zusatzfunktionen
    // plus
    val newAnArr = anArr.plus(arrayOf(7,8,9))
    println(newAnArr.contentToString()) // [1, 2, 3, 4, 5, 7, 8, 9]
    println(newAnArr.indexOf(2))

    // Mehrdimensional
    val threeDimArr: Array<Array<Array<Int>>> =
        arrayOf(
            arrayOf(
                arrayOf(1,2,3)
            )
        )
    println(threeDimArr[0][0][1]) // 2


    // ++++++++++++++++++++++++++++++++++++++++++++++
    // Collections
    // ++++++++++++++++++++++++++++++++++++++++++++++
    // read only Listen
    val list = listOf(1,2,3)
    val set = setOf("a","b","c")
    val map = mapOf("a" to 1, "b" to 2, "c" to 3)

    // read write Listen
    val list2 = mutableListOf<Boolean>()
    val set2 = mutableSetOf("a","b","c")
    val map2 = mutableMapOf<String, Int>()

}

