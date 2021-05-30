package net.spectral.logger

enum class ConsoleColor(val foreground: Int, val background: Int) {
    RESET(0, 0),
    BLACK(30, 40),
    DARK_RED(31, 41),
    DARK_GREEN(32, 42),
    GOLD(33, 43),
    DARK_BLUE(34, 44),
    PURPLE(35, 45),
    TEAL(36, 46),
    LIGHT_GRAY(37, 47),
    DARK_GRAY(90, 100),
    RED(91, 101),
    GREEN(92, 102),
    YELLOW(93, 103),
    BLUE(94, 104),
    MAGENTA(95, 105),
    CYAN(96, 106),
    WHITE(97, 107);
}