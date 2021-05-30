package net.spectral.logger

import org.tinylog.Level
import org.tinylog.core.LogEntry
import org.tinylog.core.LogEntryValue
import org.tinylog.writers.AbstractFormatPatternWriter

class SpectralConsoleWriter(private val properties: Map<String, String>) : AbstractFormatPatternWriter(properties) {

    private val dateColor = ConsoleColor.valueOf(properties["color.date"] ?: "WHITE")
    private val traceLevelColor = ConsoleColor.valueOf(properties["color.level.trace"] ?: "WHITE")
    private val debugLevelColor = ConsoleColor.valueOf(properties["color.level.debug"] ?: "WHITE")
    private val infoLevelColor = ConsoleColor.valueOf(properties["color.level.info"] ?: "WHITE")
    private val warningLevelColor = ConsoleColor.valueOf(properties["color.level.warn"] ?: "WHITE")
    private val errorLevelColor = ConsoleColor.valueOf(properties["color.level.error"] ?: "WHITE")
    private val moduleColor = ConsoleColor.valueOf(properties["color.module"] ?: "WHITE")
    private val messageColor = ConsoleColor.valueOf(properties["color.message"] ?: "WHITE")
    private val module = properties["module"] ?: throw RuntimeException("The property 'writer.module' must be defined when using 'spectral console'.")

    override fun getRequiredLogEntryValues(): MutableCollection<LogEntryValue> {
        val logEntryValues = super.getRequiredLogEntryValues()
        logEntryValues.add(LogEntryValue.LEVEL)
        return logEntryValues
    }

    override fun write(logEntry: LogEntry) {
        val rendered = render(logEntry)
        val split = rendered.split(" ")
        val builder = StringBuilder()
        builder.append(split[0].colorize(dateColor) + " ")

        when(logEntry.level) {
            Level.TRACE -> builder.append(split[1].colorize(traceLevelColor) + " ")
            Level.DEBUG -> builder.append(split[1].colorize(debugLevelColor) + " ")
            Level.INFO -> builder.append(split[1].colorize(infoLevelColor) + " ")
            Level.WARN -> builder.append(split[1].colorize(warningLevelColor) + " ")
            Level.ERROR -> builder.append(split[1].colorize(errorLevelColor) + " ")
            else -> builder.append(split[1].colorize(ConsoleColor.RESET) + " ")
        }

        builder.append("($module)".colorize(moduleColor) + " ")

        if(logEntry.level == Level.ERROR) {
            val message = split.subList(3, split.size).joinToString(" ")
            if(logEntry.exception != null) {
                builder.append(message.substring(0, message.indexOf(": ")).colorize(ConsoleColor.DARK_RED) + NEW_LINE)
                builder.append(message.substring(message.indexOf(": ") + 1).colorize(ConsoleColor.RESET))
            } else {
                builder.append(message.colorize(ConsoleColor.DARK_RED))
            }
        } else {
            builder.append(split.subList(3, split.size).joinToString(" ").colorize(messageColor))
        }

        print(builder.toString())
    }

    override fun close() {}

    override fun flush() {}

    private fun String.colorize(color: ConsoleColor): String {
        return "\u001B[${color.foreground}m$this"
    }

    companion object {
        private val NEW_LINE = System.getProperty("line.separator")
    }
}