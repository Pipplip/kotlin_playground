// Example of Singleton Design Pattern in Kotlin
// Empfohlen: Benutzung von object Deklaration
object Database {
    private val connectionPool = mutableListOf<String>()

    fun connect(): String {
        val connection = "Connection-${connectionPool.size + 1}"
        connectionPool.add(connection)
        return connection
    }

    fun getConnections(): List<String> = connectionPool
}

// Nutzung des Singleton
fun main() {
    val db1 = Database.connect()
    val db2 = Database.connect()

    println("Established Connections: ${Database.getConnections()}") // Established Connections: [Connection-1, Connection-2]
}