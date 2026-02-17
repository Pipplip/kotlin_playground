package refactoring

// schlecht
fun parseAge(input: String): Int {
    return input.toInt() // wirft Exception
}

// ##################
// refactor:
fun parseAge(input: String): Result<Int> =
    runCatching { input.toInt() }