import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player {
    private List<String> inventory;
    private int health = 100;
    private int sanity = 100;
    private String currentLocation = "Dream Threshold";
    private String playerName;
    private String dream;
    private boolean dreamAbandoned = false;
    private String gender;

    public Player(String name, String gender) {
        inventory = new ArrayList<>();
        this.playerName = name;
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getDream() {
        return dream;
    }

    public void setDream(String dream) {
        this.dream = dream;
    }

    public boolean isDreamAbandoned() {
        return dreamAbandoned;
    }

    public void abandonDream() {
        dreamAbandoned = true;
        TextEngine.pt(Handler.applyStyle(playerName + "'s dream fades into shadow...", "i", "darkgrey"));
    }

    public void addItem(String item) {
        if (item == null) return;
        inventory.add(item);
        TextEngine.pt(Handler.applyStyle("You have acquired: ", "i", "darkgrey") + 
                     Handler.applyStyle(item, "b", "cyan"));
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = Math.max(0, Math.min(100, health));
    }

    public int getSanity() {
        return sanity;
    }

    public void setSanity(int sanity) {
        this.sanity = Math.max(0, Math.min(100, sanity));
    }

    public void takeDamage(int damage) {
        health = Math.max(0, health - damage);
        TextEngine.pt(Handler.applyStyle("You take " + damage + " damage. Health: " + health, "b", "red"));
    }

    public void loseSanity(int amount) {
        sanity = Math.max(0, sanity - amount);
        TextEngine.pt(Handler.applyStyle("Your mind wavers. Sanity: " + sanity, "i", "magenta"));
    }

    public boolean isDead() {
        return health <= 0;
    }

    public boolean isInsane() {
        return sanity <= 0;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String location) {
        this.currentLocation = location;
    }

    public void getHelp() {
        String[] text = {
            "use <item> - Use an item from your inventory",
            "inventory - View your current items",
            "status - Check your health and sanity",
            "look - Examine your surroundings",
            "sound - Toggle typewriter sound on/off",
            "help - Display this help message"
        };
        TextEngine.pt(Handler.applyStyle("\nAvailable Commands:\n", "b", "yellow"));
        for (String line : text) {
            TextEngine.pt(Handler.applyStyle(line, "i"));
        }
    }

    public boolean useItem(String item) {
        if (item == null || item.isEmpty()) {
            TextEngine.pt(Handler.applyStyle("You must specify an item to use.", "i", "darkgrey"));
            return false;
        }

        for (String invItem : inventory) {
            if (invItem.equalsIgnoreCase(item)) {
                if (parseItem(invItem)) {
                    inventory.remove(invItem);
                    return true;
                }
            }
        }
        
        TextEngine.pt(Handler.applyStyle("You don't have that item or it cannot be used.", "i", "darkgrey"));
        return false;
    }

    private boolean parseItem(String item) {
        if (item.toLowerCase().contains("dream fragment")) {
            sanity = Math.min(sanity + 20, 100);
            TextEngine.pt(Handler.applyStyle("The dream fragment restores clarity to your mind. Sanity: " + sanity, "i", "cyan"));
            return true;
        } else if (item.toLowerCase().contains("healing")) {
            health = Math.min(health + 30, 100);
            TextEngine.pt(Handler.applyStyle("You feel restored. Health: " + health, "i", "green"));
            return true;
        }
        return false;
    }

    public boolean hasItem(String item) {
        return inventory.stream().anyMatch(invItem -> invItem.equalsIgnoreCase(item));
    }

    public void showInventory() {
        if (inventory.isEmpty()) {
            TextEngine.pt(Handler.applyStyle(playerName + "'s mind holds no tangible memories.", "i", "darkgrey"));
            return;
        }

        Map<String, Integer> itemCounts = new HashMap<>();
        for (String item : inventory) {
            itemCounts.put(item, itemCounts.getOrDefault(item, 0) + 1);
        }

        TextEngine.pt(Handler.applyStyle("\n" + playerName + " (" + gender + ")'s consciousness:", "b", "cyan"));
        for (Map.Entry<String, Integer> entry : itemCounts.entrySet()) {
            String display = entry.getValue() > 1 ? 
                entry.getValue() + "x " + entry.getKey() : entry.getKey();
            TextEngine.pt(Handler.applyStyle(" - " + display, "i"));
        }
    }

    public void showStatus() {
        TextEngine.pt(Handler.applyStyle("\n=== " + playerName + " (" + gender + ") ===", "b", "magenta"));
        TextEngine.pt(Handler.applyStyle("Location: " + currentLocation, "i"));
        TextEngine.pt(Handler.applyStyle("Health: " + health + "/100", health > 50 ? "green" : "red"));
        TextEngine.pt(Handler.applyStyle("Sanity: " + sanity + "/100", sanity > 50 ? "cyan" : "magenta"));
        if (dream != null && !dreamAbandoned) {
            TextEngine.pt(Handler.applyStyle("Dream: " + dream, "i", "yellow"));
        } else if (dreamAbandoned) {
            TextEngine.pt(Handler.applyStyle("Dream: Abandoned", "i", "darkgrey"));
        }
    }
}