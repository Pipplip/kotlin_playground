// Das Command-Pattern kapselt eine Aktion in einem Objekt, sodass man:
// 1. Aktionen als Objekte behandeln kann
// 2. Aktionen parametrieren, speichern und rückgängig machen kann
// 3. lose Kopplung zwischen Sender und Empfänger der Aktion erreicht

interface Command {
    fun execute()
    fun undo() = Unit // optional für Undo-Funktion
}

// Receiver: Konkrete Command-Implementierung für Licht Aktionen
class Light {
    fun on() = println("Light is ON")
    fun off() = println("Light is OFF")
}

// Konkrete Command-Klassen
class LightOnCommand(private val light: Light) : Command {
    override fun execute() = light.on()

    override fun undo() = light.off()
}

class LightOffCommand(private val light: Light) : Command {
    override fun execute() = light.off()
    override fun undo() = light.on()
}

// Invoker: Verwaltet und führt Commands aus
class RemoteControl {
    private var command: Command? = null

    fun setCommand(command: Command) {
        this.command = command
    }

    fun pressButton() {
        command?.execute()
    }

    fun pressUndo() {
        command?.undo()
    }
}

// Nutzung des Command-Patterns
fun main() {
    val light = Light()
    val lightOn = LightOnCommand(light)
    val lightOff = LightOffCommand(light)

    val remote = RemoteControl()

    remote.setCommand(lightOn)
    remote.pressButton() // Light is ON
    remote.pressUndo()   // Light is OFF

    remote.setCommand(lightOff)
    remote.pressButton() // Light is OFF
    remote.pressUndo()   // Light is ON
}