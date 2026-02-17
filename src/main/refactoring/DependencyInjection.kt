package refactoring

// Dependeny Injection ist die Technik (Aufruf des Konstruktors)
// Dependeny Inversion ist das Prinzip (Abstraktion als Konstruktor)

// Dependency Injection:
// Single Responsibility Principle (SRP):
// Eine Klasse sollte nur eine einzige Verantwortung haben und diese vollständig kapseln.
// - erlaubt es, Abhängigkeiten von außen bereitzustellen,
// - anstatt sie innerhalb der Klasse zu erstellen.

// als dependencies gehen auch Factory Interfaces

// schlecht:
class OrderService {
    private val repository = OrderRepository()
    private val reportService = ReportService()

    fun placeOrder(order: Order) {
        repository.save(order)
        reportService.writeToReport(order)
    }
}

// ##################
// refactor:
interface OrderRepository {
    fun save(order: Order)
}

interface ReportService {
    fun writeToReport(order: Order)
}

class PdfReportService(
    private val info: String
) : ReportService {
    override fun writeToReport(order: Order) {
        println("Erzeuge PDF Report für Order ${order.id} mit Info: $info")
        // PDF-Logik hier
    }
}

// Wenn man einen Service übergeben möchte, der erst noch durch eine Factory erstellt werden muss
interface ReportServiceFactory {
    fun createService(info: String, format: ReportFormat): ReportService
}

class DefaultReportServiceFactory : ReportServiceFactory {
    override fun createService(info: String, format: ReportFormat): ReportService {
        return when(format) {
            ReportFormat.PDF -> PdfReportService(info)
            ReportFormat.CSV -> CsvReportService(info)
        }
    }
}

class OrderService(
    private val repository: OrderRepository, // wird direkt verwendet
    private val reportServiceFactory: ReportServiceFactory // wird verwendet um ein spezielles Objekt zu erstellen, das dann verwendet wird
){
    fun placeOrder(order: Order) {
        repository.save(order)
        val reportService = reportServiceFactory.createService("Info", ReportFormat.PDF)
        reportService.writeToReport(order)
    }
}

// Im test:
 class OrderServiceTest {
    @Test
    fun `place order should save order and send confirmation`() {
        val repository = mockk<OrderRepository>()
        val service = OrderService(repository)
        val order = Order(...)
    }
}