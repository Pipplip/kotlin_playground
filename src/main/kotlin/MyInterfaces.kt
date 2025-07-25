package de.phbe

// ++++++++++++++++++++++++++++++++++++++++++++++
// Interfaces
// können companion objects, default Methoden und Properties haben
// ++++++++++++++++++++++++++++++++++++++++++++++
interface ColorChanger{
    val currentColor: String
    fun changeColor(newColor: String)
}

class ColorfulClass(var color: String) : ColorChanger {

    override val currentColor
        get() = color

    override fun changeColor(newColor: String) {
        this.color = newColor
    }
}

fun main(){
    val cClas =ColorfulClass("blue")
    println(cClas.currentColor)
    cClas.changeColor("yellow")
    println(cClas.currentColor)
}