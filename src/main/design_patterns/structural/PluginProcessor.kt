package design_patterns.structural

import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit

interface Processor {
    suspend fun processPlugins(pluginIdentifiers: List<PluginIdentifier>)
    suspend fun processPlugin(pluginIdentifier: PluginIdentifier): Boolean
}

data class PluginIdentifier(val name: String)

class ThreadedProcessor(
    private val loader: FilePluginLoader,
    var parallelism: Int = 0
) : Processor {

    init {
        if (parallelism == 0) {
            parallelism = detectOptimalParallelism()
        }
    }

    override suspend fun processPlugin(pluginIdentifier: PluginIdentifier): Boolean {
        println("Loading plugin files from ${pluginIdentifier.name}")
        return true
    }

    override suspend fun processPlugins(pluginIdentifiers: List<PluginIdentifier>) = coroutineScope {
        pluginIdentifiers.forEach { println("Loading plugin file from ${loader.load().path}") }

        val semaphore = Semaphore(parallelism)

        val results =
            pluginIdentifiers.map { pluginIdentifier ->
                async(Dispatchers.Default) {
                    semaphore.withPermit {
                        processPlugin(pluginIdentifier)
                    }
                }
            }
        // Wait for all to complete
        results.awaitAll()
    }
}

// +++++++++++++++

class FilePluginLoader(
    private val directoryPath: String
) : FileLoader {
    override fun load(): File = File(directoryPath)
}

interface FileLoader {
    fun load(): File
}

// +++++++++++++++

fun main()= runBlocking {
    val fileLoader = FilePluginLoader("dir")
    val processor: Processor = ThreadedProcessor(fileLoader)
    processor.processPlugins(listOf(
            PluginIdentifier("PluginA"),
            PluginIdentifier("PluginB"),
        )
    )
}
