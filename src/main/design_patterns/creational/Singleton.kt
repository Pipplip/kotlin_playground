// Example of Singleton Design Pattern in Kotlin
// Option1 - Empfohlen: Benutzung von object Deklaration
object Database {
    private val connectionPool = mutableListOf<String>()

    fun connect(): String {
        val connection = "Connection-${connectionPool.size + 1}"
        connectionPool.add(connection)
        return connection
    }

    fun getConnections(): List<String> = connectionPool
}

// Option 2 - Object Declaration mit lazy Initialisierung
object MyDatabaseOptionTwo {
    // Singleton properties and methods go here
    fun doSomething() {
        println("Singleton is doing something")
    }
}

// Option 3 - Klassische Implementierung mit Companion Object
class MyDatabaseOptionThree private constructor() {

    companion object {
        private val instance: MyDatabaseOptionThree by lazy { MyDatabaseOptionThree() }

        fun getInstance(): MyDatabaseOptionThree {
            return instance
        }
    }
    // Singleton properties and methods go here
    fun doSomething() {
        println("Singleton is doing something")
    }
}

// Nutzung des Singleton
fun main() {
    // Option 1
    val db1 = Database.connect()
    val db2 = Database.connect()
    println("Established Connections: ${Database.getConnections()}") // Established Connections: [Connection-1, Connection-2]}

    // Option 2
    MyDatabaseOptionTwo.doSomething()

    // Option 3 - Klassische Implementierung mit Companion Object
    val singletonInstance = MyDatabaseOptionThree.getInstance()
    singletonInstance.doSomething()