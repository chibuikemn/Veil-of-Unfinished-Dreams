import java.util.Scanner;

public class VeilOfUnfinishedDreams {
    public static Player player1;
    public static Player player2;
    public static Player currentPlayer;
    private static Scanner scanner = new Scanner(System.in);
    private static int maleCount = 0;
    private static int femaleCount = 0;
    private static boolean soundEnabled = true;
    private static int veilStrain = 0;
    private static String[] dreamTypes = {
        "The Crownless King", "The Silent Healer", "The Bound Oath", "The Forbidden Ascension"
    };

    public static void main(String[] args) {
        initializePlayers();
        startGame();
    }

    private static void initializePlayers() {
        // Player 1
        TextEngine.pt(Handler.applyStyle("What is your name?", "i"));
        String name1 = scanner.nextLine();
        TextEngine.pt(Handler.applyStyle("Are you male or female? (male/female)", "i"));
        String gender1 = scanner.nextLine().toLowerCase();
        while (!gender1.equals("male") && !gender1.equals("female")) {
            TextEngine.pt(Handler.applyStyle("Please choose 'male' or 'female'", "i", "red"));
            gender1 = scanner.nextLine().toLowerCase();
        }
        player1 = new Player(name1, gender1);
        displayCharacterIntro(name1, gender1);
        
        // Player 2
        TextEngine.pt(Handler.applyStyle("\nWhat is your name?", "i"));
        String name2 = scanner.nextLine();
        TextEngine.pt(Handler.applyStyle("Are you male or female? (male/female)", "i"));
        String gender2 = scanner.nextLine().toLowerCase();
        while (!gender2.equals("male") && !gender2.equals("female")) {
            TextEngine.pt(Handler.applyStyle("Please choose 'male' or 'female'", "i", "red"));
            gender2 = scanner.nextLine().toLowerCase();
        }
        player2 = new Player(name2, gender2);
        displayCharacterIntro(name2, gender2);
        
        // After both backstories, then approach the Veil
        TextEngine.pt(Handler.applyStyle("\n\nTwo souls approach the Veil...", "b", "magenta"));
        
        // Assign random dreams
        player1.setDream(dreamTypes[(int)(Math.random() * dreamTypes.length)]);
        do {
            player2.setDream(dreamTypes[(int)(Math.random() * dreamTypes.length)]);
        } while (player1.getDream().equals(player2.getDream()));
        
        currentPlayer = player1;
    }

    private static void displayCharacterIntro(String name, String gender) {
        int introNumber;
        if (gender.equals("male")) {
            maleCount++;
            introNumber = maleCount;
        } else {
            femaleCount++;
            introNumber = femaleCount;
        }
        
        String filename = gender.equals("male") ? 
            "player introduction\\Male_Player" + introNumber + "_Introduction.txt" :
            "player introduction\\Female_Player" + introNumber + "_Introduction.txt";
        
        try {
            java.nio.file.Path path = java.nio.file.Paths.get(filename);
            String intro = new String(java.nio.file.Files.readAllBytes(path));
            intro = intro.replace("<name>", name);
            
            TextEngine.pt(Handler.applyStyle("\n" + intro, "i", "cyan"));
        } catch (Exception e) {
            TextEngine.pt(Handler.applyStyle("\nThe Veil recognizes " + name + ", a " + gender + " soul carrying unfinished dreams.", "i", "cyan"));
        }
        
        TextEngine.pt(Handler.applyStyle("\nPress Enter to continue...", "i", "yellow"));
        scanner.nextLine();
    }

    public static void startGame() {
        String[] intro = {
            "Welcome to the Veil of Unfinished Dreams",
            "You find yourself standing at the threshold between waking and sleeping,",
            "where incomplete dreams drift like shadows in the mist.",
            "The air shimmers with fragments of forgotten aspirations.",
            "Do you dare to step forward into this realm of unfinished possibilities? (enter/flee)"
        };

        TextEngine.pt(Handler.applyStyle(intro[0], "b", "magenta"));
        for (int i = 1; i < intro.length; i++) {
            TextEngine.pt(Handler.applyStyle(intro[i], "i"));
        }

        gameLoop();
    }

    private static void gameLoop() {
        while (true) {
            if (player1.isDead() || player2.isDead()) {
                TextEngine.pt(Handler.applyStyle("\nConsciousness fades into the void...", "b", "red"));
                TextEngine.pt(Handler.applyStyle("THE VEIL CLAIMS ALL", "b", "red"));
                break;
            }

            if (player1.isInsane() || player2.isInsane()) {
                TextEngine.pt(Handler.applyStyle("\nMadness consumes the dream realm...", "b", "magenta"));
                TextEngine.pt(Handler.applyStyle("SANITY SHATTERED", "b", "magenta"));
                break;
            }

            TextEngine.pt(Handler.applyStyle("\n[" + currentPlayer.getPlayerName() + "'s turn]", "b", "yellow"));
            String command = scanner.nextLine().toLowerCase().trim();
            String[] parts = Handler.parseCommand(command);
            
            switch (parts[0]) {
                case "enter":
                    if (currentPlayer.getCurrentLocation().equals("Dream Threshold")) {
                        enterDreamRealm();
                    } else {
                        TextEngine.pt(Handler.applyStyle("You cannot enter from here.", "i", "darkgrey"));
                    }
                    break;
                case "use":
                    currentPlayer.useItem(parts[1]);
                    switchPlayer();
                    break;
                case "inventory":
                    currentPlayer.showInventory();
                    break;
                case "status":
                    currentPlayer.showStatus();
                    break;
                case "both":
                    player1.showStatus();
                    player2.showStatus();
                    break;
                case "help":
                    currentPlayer.getHelp();
                    break;
                case "sound":
                    TextEngine.setSoundEnabled(!soundEnabled);
                    TextEngine.pt(Handler.applyStyle("Sound " + (soundEnabled ? "enabled" : "disabled"), "i", "yellow"));
                    break;
                case "look":
                    describeSurroundings();
                    break;
                case "agree":
                    handleVeilDecision(true);
                    break;
                case "refuse":
                    handleVeilDecision(false);
                    break;
                case "north":
                case "south":
                case "east":
                case "west":
                    move(parts[0]);
                    switchPlayer();
                    break;
                case "flee":
                case "exit":
                    exitGame();
                    return;
                default:
                    TextEngine.pt(Handler.applyStyle("The Veil does not understand. Try 'help' for guidance.", "i", "darkgrey"));
            }
        }
    }

    private static void switchPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }

    private static void handleVeilDecision(boolean agreed) {
        TextEngine.pt(Handler.applyStyle(currentPlayer.getPlayerName() + (agreed ? " agrees" : " refuses"), "i", "cyan"));
        TextEngine.pt(Handler.applyStyle("The Veil awaits the other's choice...", "i", "darkgrey"));
    }

    private static void enterDreamRealm() {
        player1.setCurrentLocation("Misty Crossroads");
        player2.setCurrentLocation("Misty Crossroads");
        String[] text = {
            "\nBoth souls step through the Veil together.",
            "Reality shifts. Colors bleed into whispered regrets.",
            "You stand at crossroads where four paths diverge into swirling mists.",
            "The dreams you carry pulse with dangerous life.",
            "\n" + player1.getPlayerName() + " bears the burden of: " + player1.getDream(),
            player2.getPlayerName() + " carries the weight of: " + player2.getDream(),
            "\nWhich direction calls to your souls? (north/south/east/west)"
        };
        TextEngine.pt(Handler.applyStyle(text, "i"));
        
        player1.addItem("Dream Fragment");
        player2.addItem("Dream Fragment");
    }

    private static void move(String direction) {
        String currentLocation = currentPlayer.getCurrentLocation();
        
        if (currentLocation.equals("Misty Crossroads")) {
            switch (direction) {
                case "north":
                    goldenPath();
                    break;
                case "south":
                    shadowCorridor();
                    break;
                case "east":
                    crystallineBridge();
                    break;
                case "west":
                    silverForest();
                    break;
            }
        } else {
            TextEngine.pt(Handler.applyStyle("You cannot go that way from here.", "i", "darkgrey"));
        }
    }

    private static void goldenPath() {
        currentPlayer.setCurrentLocation("Golden Path");
        String[] text = {
            "\n" + currentPlayer.getPlayerName() + " walks the path of golden light.",
            "Warmth embraces them as forgotten hopes whisper encouragement.",
            "A glowing orb materializes, containing memories of past ambitions.",
            "The path continues deeper, but shadows gather ahead."
        };
        TextEngine.pt(Handler.applyStyle(text, "i", "yellow"));
        currentPlayer.addItem("Hope Orb");
        currentPlayer.setSanity(currentPlayer.getSanity() + 10);
    }

    private static void shadowCorridor() {
        currentPlayer.setCurrentLocation("Shadow Corridor");
        String[] text = {
            "\n" + currentPlayer.getPlayerName() + " enters the shadowy corridor.",
            "Whispers of abandoned dreams echo from the walls.",
            "The darkness presses against their mind, but they find resilience.",
            "Something stirs in the deeper shadows..."
        };
        TextEngine.pt(Handler.applyStyle(text, "i", "darkgrey"));
        currentPlayer.addItem("Resilience Shard");
        currentPlayer.loseSanity(5);
        veilStrain++;
    }

    private static void crystallineBridge() {
        currentPlayer.setCurrentLocation("Crystalline Bridge");
        String[] text = {
            "\n" + currentPlayer.getPlayerName() + " steps onto the crystalline bridge.",
            "Below, an abyss of swirling possibilities stretches infinitely.",
            "The crystal resonates with their footsteps, creating haunting melodies.",
            "A fragment of pure clarity materializes before them."
        };
        TextEngine.pt(Handler.applyStyle(text, "i", "cyan"));
        currentPlayer.addItem("Clarity Fragment");
    }

    private static void silverForest() {
        currentPlayer.setCurrentLocation("Silver Forest");
        String[] text = {
            "\n" + currentPlayer.getPlayerName() + " enters the forest of silver trees.",
            "Each leaf reflects different versions of their life's possibilities.",
            "The trees whisper secrets of paths not taken.",
            "Among the roots, they discover liquid moonlight."
        };
        TextEngine.pt(Handler.applyStyle(text, "i", "white"));
        currentPlayer.addItem("Moonlight Vial");
        currentPlayer.setSanity(currentPlayer.getSanity() + 5);
    }

    private static void describeSurroundings() {
        String location = currentPlayer.getCurrentLocation();
        switch (location) {
            case "Dream Threshold":
                TextEngine.pt(Handler.applyStyle("Two souls stand at the boundary between dreams and reality.", "i"));
                break;
            case "Misty Crossroads":
                TextEngine.pt(Handler.applyStyle("Four paths stretch before you, each leading to different aspects of the dream realm.", "i"));
                TextEngine.pt(Handler.applyStyle("The Veil strain grows stronger: " + veilStrain, "i", "red"));
                break;
            default:
                TextEngine.pt(Handler.applyStyle(currentPlayer.getPlayerName() + " stands in " + location + ". The dream realm shifts around them.", "i"));
        }
    }

    private static void exitGame() {
        String[] farewell = {
            "\nBoth souls step back through the Veil, leaving unfinished dreams behind.",
            "The memories linger like morning mist, fading but not forgotten.",
            "Perhaps you will return when ready to face what remains incomplete.",
            "\nThe Veil remembers. The dreams wait."
        };
        TextEngine.pt(Handler.applyStyle(farewell, "i", "magenta"));
        System.exit(0);
    }
}