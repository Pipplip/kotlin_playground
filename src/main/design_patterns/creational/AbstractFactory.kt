// Angenommen, wir bauen ein UI-Toolkit, das sowohl Windows- als auch MacOS-UI-Komponenten erzeugen kann.
// Wir wollen Buttons und Checkboxes herstellen, ohne den Client-Code zu ändern, egal welches Betriebssystem genutzt wird.

interface Button {
    fun paint()
}

interface Checkbox {
    fun paint()
}

// Konkrete Produkte für Windows und MacOS
class WindowsButton : Button {
    override fun paint() = println("Rendering a Windows-style Button")
}

class MacOSButton : Button {
    override fun paint() = println("Rendering a MacOS-style Button")
}

class WindowsCheckbox : Checkbox {
    override fun paint() = println("Rendering a Windows-style Checkbox")
}

class MacOSCheckbox : Checkbox {
    override fun paint() = println("Rendering a MacOS-style Checkbox")
}

// Abstrakte Fabrik Interface für die GUI-Komponenten
interface GUIFactory {
    fun createButton(): Button
    fun createCheckbox(): Checkbox
}

// Konkrete Fabriken für Windows und MacOS
class WindowsFactory : GUIFactory {
    override fun createButton(): Button = WindowsButton()
    override fun createCheckbox(): Checkbox = WindowsCheckbox()
}

class MacOSFactory : GUIFactory {
    override fun createButton(): Button = MacOSButton()
    override fun createCheckbox(): Checkbox = MacOSCheckbox()
}

// Client-Code, der die abstrakte Fabrik verwendet
class Application(private val factory: GUIFactory) {
    private val button: Button = factory.createButton()
    private val checkbox: Checkbox = factory.createCheckbox()

    fun render() {
        button.paint()
        checkbox.paint()
    }
}
// Nutzung des Abstract Factory Design Patterns
fun main() {
    // Windows App
    val windowsApp = Application(WindowsFactory())
    windowsApp.render()
    println("-----")

    // MacOS App
    val macApp = Application(MacOSFactory())
    macApp.render()
}