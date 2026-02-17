package refactoring

// God Funtions und God Classes sind Funktionen oder Klassen, die zu viele Verantwortlichkeiten haben und dadurch schwer
// zu verstehen und zu warten sind. Sie verstoßen gegen das Single Responsibility Principle,
// da sie mehrere Aufgaben übernehmen, anstatt sich auf eine einzige Aufgabe zu konzentrieren.

// Beispiel für eine God Function:
// Eine Funktion, die zu viele Verantwortlichkeiten hat und schwer zu verstehen ist
fun processOrder(order: Order) {
    if (order.items.isEmpty()) {
        throw IllegalArgumentException("No items")
    }

    var total = 0.0
    for (item in order.items) {
        total += item.price * item.quantity
    }

    if (total > 100) {
        total *= 0.9
    }

    println("Final price: $total")
}

// ##################
// refactor:
fun processOrder(order: Order) {
    validate(order)
    val total = calculateTotal(order)
    val discounted = applyDiscount(total)
    printResult(discounted)
}

private fun validate(order: Order) {
    require(order.items.isNotEmpty()) { "No items" }
}

private fun calculateTotal(order: Order): Double =
    order.items.sumOf { it.price * it.quantity }

private fun applyDiscount(total: Double): Double =
    if (total > 100) total * 0.9 else total

private fun printResult(amount: Double) {
    println("Final price: $amount")
}

// #####################################
// Beispiel für eine God Class:
class ReportService {
    fun fetch() { /* DB */ }
    fun generatePdf() { /* PDF */ }
    fun sendEmail() { /* SMTP */ }
}

// ##################
// refactor:
interface ReportRepository {
    fun fetch(): List<Report>
}

interface PdfGenerator {
    fun generate(reports: List<Report>): ByteArray
}

interface MailSender {
    fun send(pdf: ByteArray, to: String)
}

class ReportService(
    private val repository: ReportRepository,
    private val pdfGenerator: PdfGenerator,
    private val mailSender: MailSender
) {
    fun sendReport(to: String) {
        val data = repository.fetch()
        val pdf = pdfGenerator.generate(data)
        mailSender.send(pdf, to)
    }
}