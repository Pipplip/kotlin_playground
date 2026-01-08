import kotlinx.coroutines.*

// Coroutines sind eine logische Struktur von Arbeit.
// Threads sind die Ausführungsressourcen.
// Dispatcher verbinden beides.
// „Viele Coroutines teilen sich wenige Threads“

// Begriffe:
// Scope = Umfang + Lebensdauer von Coroutines
// Dispatcher = (Verteiler) Verteilt Coroutines auf Threads
// Jobs = (Aufgaben) Repräsentieren Coroutines und deren Status und ermöglichen deren Steuerung (z.B. Abbrechen)

// Erklärung (Thread-name / Coroutine-name):
// main = runBlocking (main / @coroutine#1)
//  - job = scope.launch (DefaultDispatcher-worker-1 / @coroutine#2)
//      - couroutineScope (DefaultDispatcher-worker-1 / @coroutine#2)
//          - launch (DefaultDispatcher-worker-2 / @coroutine#3)
//          - async (DefaultDispatcher-worker-2 / @coroutine#4)
//      - withContext (DefaultDispatcher-worker-1 / @coroutine#2)

/**
 * CoroutineScope
 * - Definiert Lebensdauer
 * - Enthält Dispatcher + Job
 */
val scope = CoroutineScope(
    Dispatchers.Default + SupervisorJob()
)

/**
 * ENTRY POINT
 * runBlocking:
 * - Startet eine Coroutine
 * - Blockiert den main-Thread (nur für main / Tests!)
 */
fun main() = runBlocking {

    println("Start main   | Thread: ${Thread.currentThread().name}")

    /**
     * launch = Coroutine Builder
     * - Startet eine Coroutine
     * - Gibt kein Ergebnis zurück
     */
    val job:Job = scope.launch {

        println("scope.launch start | Thread: ${Thread.currentThread().name}")

        /**
         * coroutineScope:
         * - erstellt nur einen neuen Scope, ABER keine Coroutine
         * - Strukturierte Nebenläufigkeit
         * - Wartet auf alle Child-Coroutines
         */
        coroutineScope {
            println("coroutineScope start | Thread: ${Thread.currentThread().name}")

            // Starte neue Coroutine 1 (CPU-lastig)
            launch(Dispatchers.Default) {
                println("Berechnung   | Thread: ${Thread.currentThread().name}")
                delay(500) // blockiert NICHT den Thread
            }

            // Starte neue Coroutine 2 (I/O): I/O Arbeit mit dem Ergebnis
            val daten = async(Dispatchers.IO) {
                println("Lade Daten   | Thread: ${Thread.currentThread().name}")
                delay(1000)
                "Antwort vom Server"
            }

            // Ergebnis von async abholen
            println("Ergebnis: ${daten.await()}")
            println("coroutineScope ende | Thread: ${Thread.currentThread().name}")
        }

        /**
         * expliziter Thread-Wechsel mit withContext
         * - Dispatcher entscheidet ob neuer Thread genutzt wird
         * hängt davon ab, ob der aktuelle Thread zum Dispatcher passt
         */
        withContext(Dispatchers.IO) {
            println("Datei lesen  | Thread: ${Thread.currentThread().name}")
            delay(300)
        }

        println("scope.launch ende  | Thread: ${Thread.currentThread().name}")
    }

    println("main wartet... | Thread: ${Thread.currentThread().name}")

    // warten bis der Job fertig ist
    job.join()

    // Scope sauber beenden
    scope.cancel()

    println("Ende main    | Thread: ${Thread.currentThread().name}")
}

//Start main   | Thread: main @coroutine#1
//main wartet... | Thread: main @coroutine#1
//  scope.launch start | Thread: DefaultDispatcher-worker-2 @coroutine#2
//      coroutineScope start | Thread: DefaultDispatcher-worker-2 @coroutine#2
//          Berechnung   | Thread: DefaultDispatcher-worker-3 @coroutine#3
//          Lade Daten   | Thread: DefaultDispatcher-worker-2 @coroutine#4
//      Ergebnis: Antwort vom Server
//      coroutineScope ende | Thread: DefaultDispatcher-worker-3 @coroutine#2
//      Datei lesen  | Thread: DefaultDispatcher-worker-3 @coroutine#2
//  scope.launch ende  | Thread: DefaultDispatcher-worker-3 @coroutine#2
//Ende main    | Thread: main @coroutine#1