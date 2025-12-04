// Observer Design Pattern in Kotlin
// Das Observer-Pattern definiert eine Eins-zu-viele-Abhängigkeit zwischen Objekten,
// sodass wenn ein Objekt seinen Zustand ändert, alle abhängigen Objekte automatisch benachrichtigt werden und aktualisiert werden.

interface WeatherObserver {
    fun update(temperature: Double)
}

// Subjekt, das die Beobachter verwaltet und benachrichtigt
class WeatherStation {
    private val observers = mutableListOf<WeatherObserver>()
    private var temperature: Double = 0.0

    fun addObserver(observer: WeatherObserver) {
        observers.add(observer)
    }

    fun removeObserver(observer: WeatherObserver) {
        observers.remove(observer)
    }

    fun setTemperature(temp: Double) {
        temperature = temp
        notifyObservers()
    }

    private fun notifyObservers() {
        observers.forEach { it.update(temperature) }
    }
}

// Konkrete Beobachter
class PhoneDisplay : WeatherObserver {
    override fun update(temperature: Double) {
        println("Phone display shows temperature: $temperature °C")
    }
}

class WindowDisplay : WeatherObserver {
    override fun update(temperature: Double) {
        println("Window display shows temperature: $temperature °C")
    }
}

// Nutzung des Observer-Patterns
fun main() {
    val station = WeatherStation()
    val phone = PhoneDisplay()
    val window = WindowDisplay()

    station.addObserver(phone)
    station.addObserver(window)

    station.setTemperature(25.0)
    station.setTemperature(30.0)

    station.removeObserver(window)
    station.setTemperature(28.0)
}