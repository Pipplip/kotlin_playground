// Idiomatischer Einsatz des Builder-Designmusters in Kotlin (empfohlen)
// Geeignet für komplexe Objekterstellung mit vielen optionalen Parametern
// z.B. Konfigurationen, UI-Komponenten, Datenmodelle, Spring-Data-Entities
data class Computer(
    var cpu: String = "",
    var ram: String = "",
    var storage: String = "",
    var graphicsCard: String = ""
)

class ComputerBuilder {
    var cpu: String = ""
    var ram: String = ""
    var storage: String = ""
    var graphicsCard: String = ""

    fun build() = Computer(cpu, ram, storage, graphicsCard)
}

fun computer(block: ComputerBuilder.() -> Unit): Computer =
    ComputerBuilder().apply(block).build()

// Nutzung
fun main() {
    val gamingComputer = computer {
        cpu = "Intel Core i9"
        ram = "32GB DDR4"
        storage = "1TB SSD"
        graphicsCard = "NVIDIA RTX 3080"
    }
    println(gamingComputer) // Ausgabe: Computer(cpu='Intel Core i9', ram='32GB DDR4', storage='1TB SSD', graphicsCard='NVIDIA RTX 3080')
}

//
// Traditioneller Builder-Ansatz (weniger idiomatisch in Kotlin)
// Geeignet für Sprachen ohne DSL-Unterstützung oder wenn ein klassischer Builder-Stil bevorzugt wird
// Product class
data class Computer(
    val cpu: String,
    val ram: String,
    val storage: String,
    val graphicsCard: String
)

// Concrete builder class
class ComputerBuilder {
    private var cpu: String = ""
    private var ram: String = ""
    private var storage: String = ""
    private var graphicsCard: String = ""

    fun cpu(cpu: String): ComputerBuilder {
        this.cpu = cpu
        return this
    }

    fun ram(ram: String): ComputerBuilder {
        this.ram = ram
        return this
    }

    fun storage(storage: String): ComputerBuilder {
        this.storage = storage
        return this
    }

    fun graphicsCard(graphicsCard: String): ComputerBuilder {
        this.graphicsCard = graphicsCard
        return this
    }

    fun build(): Computer {
        return Computer(cpu, ram, storage, graphicsCard)
    }
}

fun main() {
    // Build the computer with a specific configuration
    val builder = ComputerBuilder()
    val gamingComputer = builder
        .cpu("Intel Core i9")
        .ram("32GB DDR4")
        .storage("1TB SSD")
        .graphicsCard("NVIDIA RTX 3080")
        .build()
}