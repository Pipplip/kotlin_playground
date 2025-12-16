// DSL - Domain specific language
// hier mit lambdas

// Die Aufgabe
data class Task(val description: String, val priority: Int)

// Der DSL-Builder für eine Liste von Aufgaben
class TaskList {
    private val tasks = mutableListOf<Task>()

    // Methode für eine Aufgabe
    fun task(description: String, priority: Int) {
        tasks.add(Task(description, priority))
    }

    // Gibt alle Aufgaben zurück
    fun getTasks(): List<Task> = tasks
}

// Der DSL-Einstiegspunkt
fun taskList(init: TaskList.() -> Unit): TaskList {
    val taskList = TaskList()
    taskList.init()  // oder taskList.apply(init)
    return taskList
}

fun main() {
    // DSL-Verwendung mit lambdas
    val myTasks = taskList {
        task("Einkaufen gehen", 2)
        task("Kotlin lernen", 1)
        task("Abendessen kochen", 3)
    }

    // Ausgabe der Aufgaben
    myTasks.getTasks().forEach {
        println("Aufgabe: ${it.description}, Priorität: ${it.priority}")
    }
}