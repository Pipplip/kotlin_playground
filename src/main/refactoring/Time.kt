package refactoring

// schlecht:
fun isExpired(expiration: LocalDateTime): Boolean {
    return expiration.isBefore(LocalDateTime.now())
}

// ##################
// refactor:
class ExpirationService(
    private val clock: Clock
) {
    fun isExpired(expiration: LocalDateTime): Boolean {
        return expiration.isBefore(LocalDateTime.now(clock))
    }
}

// im test:
val fixedClock = Clock.fixed(
    Instant.parse("2025-01-01T00:00:00Z"),
    ZoneOffset.UTC
)