import java.util.Scanner;

public class Game {
    private Scanner scanner;
    private boolean running;

    public Game() {
        scanner = new Scanner(System.in);
        running = true;
    }

    public void start() {
        System.out.println("Welcome to Veil of Unfinished Dreams");
        gameLoop();
    }

    private void gameLoop() {
        while (running) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            handleInput(input);
        }
    }

    private void handleInput(String input) {
        if (input.isEmpty()) {
            return;
        }

        switch (input.toLowerCase()) {
            case "quit":
            case "exit":
                running = false;
                System.out.println("Goodbye.");
                break;
            case "help":
                System.out.println("Commands: help, quit");
                break;
            default:
                System.out.println("You said: " + input);
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}
