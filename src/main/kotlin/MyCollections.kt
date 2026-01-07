fun main(){

    // ++++++++++++++++++++++++++++++++++++++++++++++
    // Collections:
    // In Kotlin gibst du nur den Typ an, Kotlin entscheidet die konkrete Implementierung.
    // Bsp: Ich gebe nur den Typ an z.B: val a: List<String>
    // Kotlin erzeugt aus listOf("A", "B") intern eine Liste.
    // Das kann eine ArrayList, LinkedList oder Vector sein.
    // Wir wissen es nicht und sollen es nicht wissen.
    // Wenn man was spezielles braucht, kann man das schon machen, sollte man aber in den
    // meisten Fällen nicht: val list = ArrayList<String>()
    // ++++++++++++++++++++++++++++++++++++++++++++++

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
    // Kotlin will von uns nur den Typ.
    // Das wie, also welche Form (ArrayList, LinkedList etc) der Collection, entscheidet Kotlin selbst
    // ++++++++++++++++++++++++++++++++++++++++++++++
    // read only Listen
    val list: List<Int> = listOf(1,2,3) // kürzer: val list = listOf(1,2,3)
    val set: Set<String> = setOf("a","b","c")
    val map: Map<String, Int> = mapOf("a" to 1, "b" to 2, "c" to 3)

    // read write Listen
    val list2: MutableList<Boolean> = mutableListOf<Boolean>() // kürzer: val list2 = mutableListOf<Boolean>()
    val set2: MutableSet<String> = mutableSetOf("a","b","c")
    val map2: MutableMap<String, Int> = mutableMapOf<String, Int>()

    // Tipps:
    // Nutze Standardfunktionen anstatt zu iterieren!
    // filter, map, flatMap, groupBy, partition
    // associateBy, zip, chunked, windowed
    // any, all, none, count
    //
    // Nutze Sequence bei großen Datenmengen oder komplexen Ketten

    // Bsp: Standardfunktionen:
    showStandardFunctions()

    // ++++++++++++++++++++++++++++++++++++++++++++++
    // ranges
    // ++++++++++++++++++++++++++++++++++++++++++++++
    // Sequenz von Werten innerhalb fester Grenzen
    // ranges erben von Iterable<T>
    val r: IntRange = 1..3
    val r2 = 1 until 5

    // ++++++++++++++++++++++++++++++++++++++++++++++
    // sequences
    // ++++++++++++++++++++++++++++++++++++++++++++++
    // sind "lazy" und erzeugen keine neue Instanzen. Berechnungen werden nur ausgeführt, wenn sie benötigt werden.
    // Im Gegensatz zu Listen:
    // Wenn Listen bearbeitet werden, werden die Berechnungen sofort ausgeführt (eager) und es entsteht eine neue Liste.
    // Verwende sequences bei große Datenmengen
    val mySequence = sequenceOf("One", 2, true, "abc", 33)

    val first = mySequence
        .onEach { println("processing $it") }
        .map(Any::toString) // transformation
        .first{ it.startsWith("a")}
    println(first)
}

fun showStandardFunctions(){

    // Übersicht:
    // 1. filter(): Filtert Elemente nach einer Bedingung.
    // 2. map(): Transformiert jedes Element in etwas anderes z.B. Strings in int count
    // 3. flatMap(): mappt jedes Element zu einer Collection und „flacht“ das Ergebnis.
    // 4. groupBy(): Gruppiert Elemente nach einem Schlüssel.
    // 5. partition(): Teilt eine Collection in zwei: eine mit Elementen, die die Bedingung erfüllen, eine mit denen, die es nicht tun.
    // 6. associateBy(): Erstellt eine Map, wobei der Schlüssel aus jedem Element berechnet wird.
    // 7. any, all, none: Prüfen Bedingungen auf Elementen.
    // 8. reduce und fold: Fassen eine Collection zu einem einzigen Wert zusammen.
    // 9. zip(): Kombiniert zwei Listen Element für Element zu Paaren.
    // 10. chunked(): Teilt eine Liste in kleinere Listen einer bestimmten Größe.
    // 11. forEach(): Führt eine Aktion für jedes Element der Sammlung aus.
    // 12. first() / last(): Gibt das erste bzw. das letzte Element einer Sammlung zurück.
    // 13. sorted(): Gibt eine neue Sammlung zurück, die die Elemente in aufsteigender Reihenfolge enthält.
    // 14. take(): Gibt die ersten n Elemente einer Sammlung zurück.
    // 15. drop(): Entfernt die ersten n Elemente einer Sammlung und gibt die restlichen zurück.
    // 16. distinct(): Gibt eine Sammlung zurück, die nur die einzigartigen Elemente enthält, ohne Duplikate.
    // 17. count(): Zählt die Anzahl der Elemente, die einer Bedingung entsprechen.
    // 18. find(): Gibt das erste Element zurück, das eine gegebene Bedingung erfüllt, oder null, wenn kein solches Element gefunden wird.

    // 1. Filter
    // Filtert Elemente nach einer Bedingung.
    val numbers = listOf(1, 2, 3, 4, 5, 6)
    val evenNumbers = numbers.filter { it % 2 == 0 }
    println(evenNumbers) // [2, 4, 6]

    // 2. map
    // Transformiert jedes Element in etwas anderes.
    val names = listOf("Anna", "Bob", "Carmen")
    val nameLengths = names.map { it.length }
    println(nameLengths) // [4, 3, 6]

    // 3. flatMap
    // Mappt jedes Element zu einer Collection und „flacht“ das Ergebnis.
    val list = listOf(1, 2, 3)
    val flatMapped = list.flatMap { listOf(it, it * 10) }
    println(flatMapped) // [1, 10, 2, 20, 3, 30]

    // 4. groupBy
    // Gruppiert Elemente nach einem Schlüssel.
    val words = listOf("apple", "banana", "apricot", "blueberry")
    val grouped = words.groupBy { it.first() }
    println(grouped) // {a=[apple, apricot], b=[banana, blueberry]}

    // 5. Partition
    // Teilt eine Collection in zwei: eine mit Elementen, die die Bedingung erfüllen, eine mit denen, die es nicht tun.
    val numbersPart = listOf(1, 2, 3, 4, 5)
    val (even, odd) = numbersPart.partition { it % 2 == 0 }
    println(even) // [2, 4]
    println(odd)  // [1, 3, 5]

    // 6. associateBy
    // Erstellt eine Map, wobei der Schlüssel aus jedem Element berechnet wird.
    val people = listOf(PersonPart(1, "Anna"), PersonPart(2, "Bob"))
    val map = people.associateBy { it.id }
    println(map) // {1=Person(id=1, name=Anna), 2=Person(id=2, name=Bob)}

    // 7. any, all, none
    //Prüfen Bedingungen auf Elementen.
    val numbers3 = listOf(1, 3, 5)
    println(numbers3.any { it % 2 == 0 })  // false (gibt’s gerade Zahlen?)
    println(numbers3.all { it > 0 })       // true  (sind alle > 0?)
    println(numbers3.none { it < 0 })      // true  (gibt’s keine negativen?)

    // 8. reduce und fold
    // Fassen eine Collection zu einem einzigen Wert zusammen.
    val numbers4 = listOf(1, 2, 3, 4)
    // reduce: benutzt erstes Element als Startwert
    val sumReduce = numbers4.reduce { acc, num -> acc + num }
    println(sumReduce) // 10

    // fold: Startwert selbst definieren (z.B. 0)
    val sumFold = numbers4.fold(0) { acc, num -> acc + num }
    println(sumFold) // 10

    // 9. zip
    //Kombiniert zwei Listen Element für Element zu Paaren.
    val names4 = listOf("Anna", "Bob")
    val ages = listOf(25, 30)
    val zipped = names4.zip(ages)
    println(zipped) // [(Anna, 25), (Bob, 30)]

    // 10. chunked
    // Teilt eine Liste in kleinere Listen einer bestimmten Größe.
    val numbers5 = (1..10).toList()
    val chunks = numbers5.chunked(3)
    println(chunks) // [[1, 2, 3], [4, 5, 6], [7, 8, 9], [10]]

    // 11. forEach
    // Führt eine Aktion für jedes Element der Sammlung aus.
    val numbers6 = (1..10).toList()
    numbers6.forEach { println(it) }

    // 12. First / Last
    // Gibt das erste bzw. das letzte Element einer Sammlung zurück.
    val first = numbers6.first()  // Ausgabe: 1
    val last = numbers6.last()    // Ausgabe: 10

    // 13. sorted
    // Gibt eine neue Sammlung zurück, die die Elemente in aufsteigender Reihenfolge enthält.
    val numbers7 = listOf(3, 1, 2, 4, 5)
    val sorted = numbers7.sorted()
    val sortedDes = numbers7.sortedDescending()
    println(sorted) // Ausgabe: 1 2 3 4 5
    println(sortedDes) // Ausgabe 5 4 3 2 1

    // 14. take()
    // Gibt die ersten n Elemente einer Sammlung zurück.
    val numbers8 = listOf(1, 2, 3, 4, 5)
    val firstTwo = numbers8.take(2)
    println(firstTwo)  // Ausgabe: [1, 2]

    // 15. drop()
    // Entfernt die ersten n Elemente einer Sammlung und gibt die restlichen zurück.
    val numbers9 = listOf(1, 2, 3, 4, 5)
    val dropFirstTwo = numbers9.drop(2)
    println(dropFirstTwo)  // Ausgabe: [3, 4, 5]

    // 16. distinct()
    // Gibt eine Sammlung zurück, die nur die einzigartigen Elemente enthält, ohne Duplikate.
    val numbers10 = listOf(1, 2, 2, 3, 3)
    val distinctNumbers = numbers10.distinct()
    println(distinctNumbers)  // Ausgabe: [1, 2, 3]

    // 17. count()
    // Zählt die Anzahl der Elemente, die einer Bedingung entsprechen.
    val numbers11 = listOf(1, 2, 3, 4, 5)
    val countEven = numbers11.count { it % 2 == 0 }
    println(countEven)  // Ausgabe: 2

    // 18. find()
    // Gibt das erste Element zurück, das eine gegebene Bedingung erfüllt, oder null, wenn kein solches Element gefunden wird.
    val numbers12 = listOf(1, 2, 3)
    val firstEven = numbers12.find { it % 2 == 0 }
    println(firstEven)  // Ausgabe: 2
}

data class PersonPart(val id: Int, val name: String)
