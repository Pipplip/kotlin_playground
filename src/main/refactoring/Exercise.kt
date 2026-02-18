package refactoring

// ########################
// Aufgabe: Bitte refactorn
// ########################
//class UserService {
//    private val repo = UserRepositoryImpl()
//    private val logger = Logger()
//
//    fun createUser(name: String, email: String, age: Int, password: String): Boolean {
//        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
//            logger.log("Invalid input")
//            return false
//        }
//
//        val user = User(name, email, age, password)
//        repo.save(user)
//
//        logger.log("User created: ${user.email}")
//        return true
//    }
//
//    fun getUserEmail(id: Int): String {
//        val user = repo.findById(id)
//        return user!!.email
//    }
//}
//
//class UserRepositoryImpl {
//    private val users = mutableListOf<User>()
//
//    fun save(user: User) {
//        users.add(user)
//    }
//
//    fun findById(id: Int): User? {
//        return users.getOrNull(id)
//    }
//}
//
//class Logger {
//    fun log(msg: String) {
//        println("LOG: $msg")
//    }
//}
//
//data class User(val name: String, val email: String, val age: Int, val password: String)

// ########################
// Lösung
// ########################
interface UserRepository{
    fun save(user: User)
    fun findById(id: Int): User?
}

class UserRepositoryImpl() : UserRepository{
    val users: MutableList<User> = mutableListOf()

    override fun save(user: User) {
        users.add(user)
    }
    override fun findById(id: Int): User? {
        return users.find{it.id == id}
    }
}

interface UserFactory{
    fun create(user: User): User
}

class DefaultUserFactory : UserFactory{
    override fun create(user: User): User {
        if (user.name.isEmpty() || user.email.isEmpty() || user.password.isEmpty()) {
            throw IllegalArgumentException("Username or email can't be empty")
        }
        return User(user.id, user.name, user.email, user.age, user.password)
    }
}

class UserService(
    private val repo: UserRepository, // private machen, da sonst von außen auf repo zugegriffen werden kann
    private val logger: Logger,
    private val userFactory: UserFactory
){
    fun createUser(user: User): Boolean {
        val newUser = userFactory.create(user) // eine UserFactory übernimmt alle checks
        repo.save(newUser)

        logger.log("User created: ${newUser.email}")
        return true
    }

    fun getUserEmail(id: Int): String {
        val user = repo.findById(id) ?: throw Exception("User not found")
        return user.email
    }
}

interface Logger{
    fun log(message: String)
}

class ConsoleLogger : Logger {
    override fun log(msg: String) {
        println("LOG: $msg")
    }
}

data class User(val id: Int, val name: String, val email: String, val age: Int, val password: String)

fun main(){
    val repo = UserRepositoryImpl()
    val log = ConsoleLogger()
    val userFactory = DefaultUserFactory()
    val userService = UserService(repo, log, userFactory)

    val user = User(1, "Test", "test@test.de", 18, "password")
    userService.createUser(user)
    val email = userService.getUserEmail(user.id)
    println(email)
}