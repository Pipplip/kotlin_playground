// Idiomatischer Einsatz des Builder-Designmusters in Kotlin (empfohlen)
// Geeignet für komplexe Objekterstellung mit vielen optionalen Parametern
// z.B. Konfigurationen, UI-Komponenten, Datenmodelle, Spring-Data-Entities
class User{
    var name: String = ""
    var age: Int = 0
    var email: String? = null

    override fun toString(): String {
        return "User(name='$name', age=$age, email='$email')"
    }
}

fun createUser(block: User.() -> Unit): User {
    val user = User()
    user.block()
    return user
    // oder kürzer:
    // return User().apply(block)
}

// Nutzung
fun main() {
    val user = createUser {
        name = "Alice"
        age = 30
        email = ""
    }
    println(user)
}
// Ausgabe: User(name='Alice', age=30, email='')