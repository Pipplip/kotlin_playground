// Coroutines sind leichte, kooperierende Threads die es ermöglichen,
// asynchronen und nebenläufigen Code einfach zu schreiben.
// Merke: Coroutines = structured Concurrency + suspend Functions + leichte Jobs

// Coroutines sind im Gegensatz zu normalen Threads
// - leichtgewichtiger (geringerer Speicherverbrauch)
// - kooperierend (pausieren an definierten Stellen)
// - nicht blockierend (Threads bleiben frei für andere Aufgaben)
// - strukturierter (einfacheres Management von Nebenläufigkeit)
// - einfacher zu verwenden (Kotlin-Sprachunterstützung)
// - sicherer (bessere Fehlerbehandlung)
// - skalierbarer (mehr Coroutines als Threads möglich)
// - besser lesbar (ähnlich wie synchroner Code)
// - besser wartbar (klarere Trennung von Aufgaben)
// - flexibler (einfaches Wechseln von Kontexten/Dispatcher)

// Coroutine ist eine Funktion, die an bestimmten Stellen
// ihre Ausführung pausieren und später wieder aufnehmen kann.

// Concurrency = gleichzeitige Ausführung von mehreren Aufgaben
// Asynchron = Aufgaben, die unabhängig voneinander ablaufen und nicht aufeinander warten müssen
// Syncjhron = Aufgaben, die nacheinander ablaufen und aufeinander warten müssen

// Coroutines sind nicht wie traditionelle Threads, da sie kooperativ sind.
// Das bedeutet, dass sie an bestimmten Stellen (sogenannten Suspension Points)
// ihre Ausführung pausieren können, um anderen Coroutines die Möglichkeit zu geben,
// ausgeführt zu werden. Dies ermöglicht eine effizientere Nutzung von Ressourcen
// und eine bessere Skalierbarkeit, da viele Coroutines auf wenigen Threads laufen können.
// D.h. weniger Threads für die gleiche Anzahl an Aufgaben.

// Wichtigste Bestandteile von Coroutines in Kotlin:
// 1. Builders: Funktionen wie runBlocking, launch, async, coroutineScope zum Starten von Coroutines.
//  1a. runBlocking: Startet eine Coroutine und blockiert den aktuellen Thread, bis sie abgeschlossen ist. Wird hauptsächlich in main-Funktionen und Tests verwendet.
//  1b. launch: Asynchron. Startet eine neue Coroutine, die keinen Rückgabewert hat, und blockiert den Thread nicht. Wird oft für nebenläufige Aufgaben verwendet.
//  1c. async: Startet eine neue Coroutine, die einen Rückgabewert hat (Deferred). Wird oft für parallele Aufgaben verwendet.
//  1d. coroutineScope: Erstellt einen neuen Coroutine-Kontext und wartet
// 2. CoroutineScope: Definiert den Lebenszyklus von Coroutines und verwaltet deren Kontext.
// 3. Dispatcher: Bestimmt, auf welchem Thread oder Thread-Pool die Coroutine ausgeführt wird (z.B. Main, IO, Default).
// 4. Suspend Functions: Funktionen, die pausiert und später wieder aufgenommen werden können.
// 5. Jobs: Repräsentieren eine laufende Coroutine und ermöglichen deren Verwaltung (Start, Stopp, Statusabfrage).

// Beispiel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

/* -----------------------------------------------------------
 * 1. DATA-KLASSEN & FAKE API
 * ----------------------------------------------------------- */

// Einfache Datenklassen (automatisch: equals, hashCode, toString, copy)
data class User(val id: Int, val name: String)
data class Post(val id: Int, val title: String)

// Suspend heißt: funktion kann "pausiert" werden, ohne Thread zu blockieren
suspend fun loadUser(): User {
    delay(300) // Nicht blockierend – Thread bleibt frei
    return User(1, "Alice")
}

suspend fun loadPosts(): List<Post> {
    delay(500)
    return listOf(
        Post(1, "Hallo Welt"),
        Post(2, "Coroutines sind cool")
    )
}

/* -----------------------------------------------------------
 * 2. VIEWMODEL-ÄHNLICHES OBJEKT MIT EIGENEM COROUTINE-SCOPE
 * ----------------------------------------------------------- */

class MyViewModel(
    // SupervisorJob: Fehler eines Child-Jobs beendet NICHT alle anderen
    private val scope: CoroutineScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Default) // Default = CPU-optimiert
) {

    /* ------------------- UI-State ------------------- */

    // MutableStateFlow: interner State
    private val _state = MutableStateFlow("Idle")

    // Öffentlich: nur lesen erlaubt
    val state: StateFlow<String> get() = _state


    /* ------------------- Events (einmalige Benachrichtigungen) ------------------- */

    // Channel speichert Events, die als Stream weitergegeben werden
    private val _events = Channel<String>(Channel.BUFFERED)

    // Umwandeln zu Flow, damit mehrere Konsumenten "abonnieren" können
    val events = _events.receiveAsFlow()


    /* -----------------------------------------------------------
     * 3. HAUPTFUNKTION: Daten laden (parallel, sicher, strukturiert)
     * ----------------------------------------------------------- */

    fun loadData() {
        // launch: startet eine Coroutine ohne Rückgabewert
        scope.launch {
            try {
                // Aktualisiere UI-State
                _state.value = "Loading…"

                // Nutzt structured concurrency: alle Child-Coroutines werden gemanagt
                val (user, posts) = coroutineScope {

                    // async: starte parallele Tasks (hier IO-Operationen)
                    val userDeferred = async(Dispatchers.IO) { loadUser() }
                    val postsDeferred = async(Dispatchers.IO) { loadPosts() }

                    // await() wartet auf beide Ergebnisse
                    userDeferred.await() to postsDeferred.await()
                }

                // CPU-Arbeit in Default-Dispatcher auslagern
                val summary = withContext(Dispatchers.Default) {
                    """User: ${user.name}
                       Posts: ${posts.size}
                    """.trimIndent()
                }

                // Ergebnis in den UI-State setzen
                _state.value = "Success:\n$summary"

                // Ein einmaliges Event auslösen
                _events.send("Data loaded successfully!")

            } catch (e: CancellationException) {
                // WICHTIG: Dieser Fehler signalisiert Job-Abbruch → erneut werfen
                _state.value = "Cancelled"
                throw e

            } catch (e: Exception) {
                // Alle anderen Fehler sauber behandeln
                _state.value = "Error: ${e.message}"
                _events.send("Error occurred!")
            }
        }
    }


    /* -----------------------------------------------------------
     * 4. PARALLELER FLOW: Einfacher Counter-Stream
     * ----------------------------------------------------------- */

    fun startCounter() {
        // Startet eine Coroutine, die den Counter-Flow sammelt
        scope.launch {
            countFlow().collect {
                _events.send("Counter: $it")
            }
        }
    }

    // Ein "kalter" Flow – startet erst bei collect()
    private fun countFlow(): Flow<Int> = flow {
        for (i in 1..5) {
            emit(i)       // Sende Wert
            delay(200)    // nicht blockierend
        }
    }


    /* -----------------------------------------------------------
     * 5. ALLE COROUTINES ABBRECHEN
     * ----------------------------------------------------------- */

    fun clear() {
        scope.cancel()   // Beendet alle laufenden Coroutines sicher
    }
}


/* -----------------------------------------------------------
 * 6. MAIN-FUNKTION: DEMO-PROGRAMM
 * ----------------------------------------------------------- */

fun main() = runBlocking {
    // runBlocking für Demo/Tests
    val vm = MyViewModel(this)

    // Starte Counter und Datennachladen
    vm.startCounter()
    vm.loadData()

    // Beobachte UI-State
    val jobState = launch {
        vm.state.collect { println("STATE → $it") }
    }

    // Beobachte Events (einmalige Nachrichten)
    val jobEvents = launch {
        vm.events.collect { println("EVENT → $it") }
    }

    delay(2000) // Laufen lassen

    println(">>> cancel ViewModel!")
    vm.clear()

    // Beende die Beobachter
    jobState.cancel()
    jobEvents.cancel()
}
