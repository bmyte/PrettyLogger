package net.spectral.logger

import org.junit.jupiter.api.Test

class LoggerTest {

    @Test
    fun `demo text`() {
        Thread.currentThread().name = "Spectral-Test"

        Logger.trace("Trace logger message")
        Logger.debug("Debug logger message")
        Logger.info("Info logger message")
        Logger.warn("Warn logger message")
        Logger.error("Error logger message")

        val exception = RuntimeException("This is a test exception")
        Logger.error(exception) { "Exception logger message" }
    }
}