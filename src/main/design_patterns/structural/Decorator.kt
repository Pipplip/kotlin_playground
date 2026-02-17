package design_patterns.structural

// erlaubt es dir, ein Lambda zu übergeben, das keinen Wert erwartet, aber einen Wert zurückgibt.
// Also Objekte zur Laufzeit mit neuen Fähigkeiten ausstatten
// ++++++++++++++++++++++++++++++++++++++++++++++
// Decorator Pattern
// ++++++++++++++++++++++++++++++++++++++++++++++

// Basis-Komponente
interface Text {
    fun render(): String
}

// Konkrete Komponente
class PlainText(private val content: String) : Text {
    override fun render(): String {
        return content
    }
}

class BoldText(private val wrapped: Text) : Text {
    override fun render(): String = "<b>${wrapped.render()}</b>"
}

class ItalicText(private val wrapped: Text) : Text {
    override fun render(): String = "<i>${wrapped.render()}</i>"
}

// Nutzung des Decorator Patterns
fun main() {
    val simpleText: Text = PlainText("Hello")
    val boldText: Text = BoldText(simpleText)
    val fancyText: Text = ItalicText(boldText)

    println(simpleText.render())  // Hello
    println(boldText.render())    // <b>Hello</b>
    println(fancyText.render())   // <i><b>Hello</b></i>
}