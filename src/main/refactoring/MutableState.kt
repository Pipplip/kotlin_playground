package refactoring

// Mutable state: Zustand kann überall verändert werden, was zu unerwarteten Seiteneffekten führt.
// schlecht:
class Cart {
    var items: MutableList<Item> = mutableListOf()

    fun add(item: Item) {
        items.add(item)
    }
}

// ##################
// refactor:
// items lassen sich jetzt nur noch über die add-Methode hinzufügen, aber nicht mehr von außen verändern
// (z.B. durch Zuweisung oder direktes Manipulieren der Liste).
data class Cart(
    val items: List<Item>
) {
    fun add(item: Item): Cart =
        copy(items = items + item)
}