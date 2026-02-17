package refactoring

// verwende keine Singletons, da die Klassen nun Konstruktoren haben, die Parameter benötigen.

// schlecht:
object Database {                 // globaler Singleton
    fun save(data: String) {
        println("Saved: $data")
    }
}

class UserService {
    fun registerUser(name: String) {
        Database.save(name)       // harte Abhängigkeit
    }
}

// ##################
// refactor:
interface Database {
    fun save(data: String)
}

// Konkrete Implementierng:
class PostgresDatabase : Database {
    override fun save(data: String) {
        println("Saved to Postgres: $data")
    }
}

// Service mit Constructor Injection:
class UserService(
    private val database: Database   // Dependency Injection
) {
    fun registerUser(name: String) {
        database.save(name)
    }
}

// bessere Testbarkeit:
class FakeDatabase : Database {
    val savedData = mutableListOf<String>()

    override fun save(data: String) {
        savedData.add(data)
    }
}

fun test() {
    val fakeDb = FakeDatabase()
    val service = UserService(fakeDb)

    service.registerUser("Anna")

    assert(fakeDb.savedData.contains("Anna"))
}