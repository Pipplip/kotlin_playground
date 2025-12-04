// Factory Design Pattern Example in Kotlin
// Geeignet, wenn die genaue Klasse des zu erstellenden Objekts zur Laufzeit bestimmt wird
// z.B. bei der Arbeit mit verschiedenen Datenbanktypen, UI-Komponenten oder Dateiformaten

interface Vehicle {
    fun drive(): String
}

class Car : Vehicle {
    override fun drive() = "Driving a car"
}

class Bike : Vehicle {
    override fun drive() = "Riding a bike"
}

object VehicleFactory {
    fun createVehicle(type: String): Vehicle {
        return when (type.lowercase()) {
            "car" -> Car()
            "bike" -> Bike()
            else -> throw IllegalArgumentException("Unknown vehicle type")
        }
    }
}

// Nutzung des Factory Design Patterns
fun main() {
    val car: Vehicle = VehicleFactory.createVehicle("car")
    println(car.drive()) // Driving a car

    val bike: Vehicle = VehicleFactory.createVehicle("bike")
    println(bike.drive()) // Riding a bike
}