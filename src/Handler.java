import java.util.ArrayList;
import java.util.List;

public class Handler {
    // ANSI escape sequences
    private static final String r = "\033[0m";   // Reset
    private static final String b = "\033[1m";   // Bold
    private static final String d = "\033[2m";   // Dim
    private static final String i = "\033[3m";   // Italic
    private static final String u = "\033[4m";   // Underline
    private static final String bl = "\033[5m";  // Blink
    private static final String rev = "\033[7m"; // Reverse
    private static final String h = "\033[8m";   // Hidden

    // Text colors
    private static final String blk = "\033[30m";
    private static final String red = "\033[31m";
    private static final String green = "\033[32m";
    private static final String yellow = "\033[33m";
    private static final String blue = "\033[34m";
    private static final String magenta = "\033[35m";
    private static final String cyan = "\033[36m";
    private static final String white = "\033[37m";
    private static final String darkGrey = "\033[90m";

    // Background colors
    private static final String blk_bg = "\033[40m";
    private static final String red_bg = "\033[41m";
    private static final String green_bg = "\033[42m";
    private static final String yellow_bg = "\033[43m";
    private static final String blue_bg = "\033[44m";
    private static final String magenta_bg = "\033[45m";
    private static final String cyan_bg = "\033[46m";
    private static final String white_bg = "\033[47m";

    public static String[] parseCommand(String string) {
        String[] parts = string.split(" ", 2);
        String action = parts[0];
        String argument = parts.length > 1 ? parts[1] : "";
        return new String[] { action, argument };
    }

    public static String applyStyle(String string, String... styles) {
        StringBuilder styledString = new StringBuilder();

        for (String style : styles) {
            switch (style.toLowerCase()) {
                case "r": styledString.append(r); break;
                case "b": styledString.append(b); break;
                case "d": styledString.append(d); break;
                case "i": styledString.append(i); break;
                case "u": styledString.append(u); break;
                case "bl": styledString.append(bl); break;
                case "rev": styledString.append(rev); break;
                case "h": styledString.append(h); break;
                case "blk": styledString.append(blk); break;
                case "red": styledString.append(red); break;
                case "green": styledString.append(green); break;
                case "yellow": styledString.append(yellow); break;
                case "blue": styledString.append(blue); break;
                case "magenta": styledString.append(magenta); break;
                case "cyan": styledString.append(cyan); break;
                case "white": styledString.append(white); break;
                case "darkgrey": styledString.append(darkGrey); break;
                case "blk_bg": styledString.append(blk_bg); break;
                case "red_bg": styledString.append(red_bg); break;
                case "green_bg": styledString.append(green_bg); break;
                case "yellow_bg": styledString.append(yellow_bg); break;
                case "blue_bg": styledString.append(blue_bg); break;
                case "magenta_bg": styledString.append(magenta_bg); break;
                case "cyan_bg": styledString.append(cyan_bg); break;
                case "white_bg": styledString.append(white_bg); break;
            }
        }

        styledString.append(string).append(r);
        return styledString.toString();
    }

    public static List<String> applyStyle(String[] strings, String... styles) {
        List<String> styledStrings = new ArrayList<>();
        for (String str : strings) {
            styledStrings.add(Handler.applyStyle(str, styles));
        }
        return styledStrings;
    }
}