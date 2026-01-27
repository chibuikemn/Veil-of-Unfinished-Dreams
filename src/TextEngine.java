import java.util.List;

public class TextEngine {
    private static boolean skipDelay = false;
    private static boolean soundEnabled = true;

    public static void setSkipDelay(boolean skip) {
        skipDelay = skip;
    }

    public static void setSoundEnabled(boolean enabled) {
        soundEnabled = enabled;
    }

    private static void playTypeSound() {
        if (!soundEnabled) return;
        try {
            // Simple system beep - more reliable
            java.awt.Toolkit.getDefaultToolkit().beep();
        } catch (Exception e) {
            // Silently ignore sound errors
        }
    }

    public static void pt(String text, int delay, boolean newline) {
        for (char c : text.toCharArray()) {
            System.out.print(c);
            if (c != ' ' && c != '\n' && Math.random() < 0.3) { // Only beep 30% of the time to avoid spam
                playTypeSound();
            }
            if (!skipDelay) {
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Interrupted: " + e.getMessage());
                }
            }
        }
        if (newline) {
            System.out.println();
        }
    }

    public static void pt(String text, int delay) {
        pt(text, delay, true);
    }

    public static void pt(String text, boolean newline) {
        pt(text, 25, newline);
    }

    public static void pt(String text) {
        pt(text, 25, true);
    }

    public static void pt(String[] text) {
        for (String line : text) {
            pt(line);
        }
    }

    public static void pt(List<String> text) {
        for (String line : text) {
            pt(line);
        }
    }
}