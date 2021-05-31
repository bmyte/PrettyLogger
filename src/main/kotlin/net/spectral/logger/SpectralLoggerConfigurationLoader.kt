package net.spectral.logger

import org.tinylog.configuration.ConfigurationLoader
import org.tinylog.runtime.RuntimeProvider
import java.util.*

class SpectralLoggerConfigurationLoader : ConfigurationLoader {

    override fun load(): Properties {
        val properties = Properties()

        properties["writer"] = "spectral console"
        properties["writer.format"] = "[{date:HH:mm:ss}] (module) [{thread}/{level}] {message}"
        properties["writer.module"] = "Logger"
        properties["writer.level"] = "TRACE"
        properties["writer.color.date"] = "DARK_BLUE"
        properties["writer.color.level.trace"] = "DARK_GRAY"
        properties["writer.color.level.debug"] = "CYAN"
        properties["writer.color.level.info"] = "DARK_GREEN"
        properties["writer.color.level.warn"] = "GOLD"
        properties["writer.color.level.error"] = "DARK_RED"
        properties["writer.color.module"] = "TEAL"
        properties["writer.color.message"] = "RESET"

        val classloader = RuntimeProvider.getClassLoader()
        val stream = classloader.getResourceAsStream("logger.properties") ?: return properties
        properties.load(stream)

        return properties
    }
}