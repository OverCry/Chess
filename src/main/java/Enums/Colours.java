package Enums;

public enum Colours {
    // Regular Colors
    BLACK("\033[0;30m"),   // BLACK
    RED("\033[0;31m"),     // RED
    WHITE("\033[0;37m"),   // WHITE

    BLACK_BOLD("\033[1;30m"),  // BLACK
    RED_BOLD("\033[1;31m"),    // RED
    WHITE_BOLD("\033[1;37m"),  // WHITE

    WHITE_BRIGHT("\033[0;97m"),  // WHITE

    BLACK_BACKGROUND("\033[40m"),  // BLACK
    RED_BACKGROUND("\033[41m"),    // RED
    GREEN_BACKGROUND("\033[42m"),  // GREEN
    YELLOW_BACKGROUND("\033[43m"), // YELLOW
    BLUE_BACKGROUND("\033[44m"),   // BLUE
    PURPLE_BACKGROUND("\033[45m"), // PURPLE
    CYAN_BACKGROUND("\033[46m"),   // CYAN
    WHITE_BACKGROUND("\033[47m"),  // WHITE

    RESET("\033[0m");

    private String _representation;

    Colours(String representation) {
        _representation = representation;
    }

    public String getRepresentation() {
        return _representation;
    }
}
