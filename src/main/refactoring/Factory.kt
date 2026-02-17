package refactoring

// Eine Factory kapselt die Erzeugung von Objekten.
// wann sinnvoll:
// 1. Wenn die Erzeugung komplex ist (z.B. viele Parameter, Abhängigkeiten, etc.)

// schlecht:
 val connection = when(env) {
     "DEV" -> Connection("http://dev.local", 1000)
     "PROD" -> Connection("https://api.prod", 5000)
     else -> throw IllegalArgumentException("Unknown environment")
 }

// ##################
// refactor:
// kapseln in einer Factory-Klasse
class Connection(val url: String, val timeout: Int)

class ConnectionFactory {
    fun createConnection(env: String): Connection {
        return when(env) {
            "DEV" -> Connection("http://dev.local", 1000)
            "PROD" -> Connection("https://api.prod", 5000)
            else -> throw IllegalArgumentException("Unknown environment")
        }
    }
}

// Aufruf:
val connectionFactory = ConnectionFactory()
val connection = connectionFactory.createConnection("DEV")


// ################################################################################
// 2. wenn man unterschiedliche Implementierungen eines Interfaces erzeugen möchte
// schlecht:
 val gateway: PaymentGateway = if (useStripe) VisaGateway() else PaypalGateway()

// ##################
// refactor:
interface PaymentGateway {
    fun pay(amount: Double)
}

class VisaGateway : PaymentGateway { ... }
class PaypalGateway : PaymentGateway { ... }

class PaymentGatewayFactory {
    fun create(type: String): PaymentGateway = when(type) {
        "visa" -> VisaGateway()
        "paypal" -> PaypalGateway()
        else -> throw IllegalArgumentException("Unknown gateway")
    }
}

// ################################################################################
// 3. wenn man Objekte dynamisch zur Laufzeit erzeugen möchte (z.B. basierend auf Benutzereingaben, Konfigurationen, etc.)
// schlecht:
val reportGenerator: ReportGenerator = when(reportType) {
    "PDF" -> PdfReportGenerator()
    "HTML" -> HtmlReportGenerator()
    else -> throw IllegalArgumentException()
}

// ##################
// refactor:
class ReportFactory {
    fun createReport(type: String): ReportGenerator = when(type) {
        "PDF" -> PdfReportGenerator()
        "HTML" -> HtmlReportGenerator()
        else -> throw IllegalArgumentException()
    }
}

// Aufruf:
val reportFactory = ReportFactory()
val reportGenerator = reportFactory.createReport("PDF")

