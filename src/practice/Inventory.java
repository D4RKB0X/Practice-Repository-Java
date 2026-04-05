package practice;

import java.util.ArrayList;
import java.util.List;

interface InventoryHolder {
    void addItem(String item) throws Exception;
    void displayInventory();
}

class Hero implements InventoryHolder {
    private final String name;
    private final List<String> inventory;
    private final int MAX_ITEMS = 6;

    public Hero(String n) {
        this.name = n;
        this.inventory = new ArrayList<>(MAX_ITEMS);
    }

    public String getHeroName() {
        return name;
    }

    public void addItem(String item) throws Exception {
        if(item == null) {
            throw new Exception("Cannot add null item to inventory.");
        }
        if(inventory.size() >= MAX_ITEMS) {
            throw new Exception("Inventory is full! Cannot add " + item);
        }
        inventory.add(item);
    }

    public void displayInventory() {
        System.out.println(name + "'s Inventory:");
        if(inventory.isEmpty()) {
            System.out.println("(empty)");
            return;
        }
        for(String item : inventory) {
            System.out.println("- " + item);
        }
    }
}

class Inventory {
    public static void main(String[] args) {
        Hero arth = new Hero("Arthas");

        String[] items = {
                "Crown of Kings",
                "Mask of Death",
                "Ring of Protection +5",
                "Orb of Frost",
                "Claws of Attack +15",
                "Staff of Teleportation"
        };

        for(String item : items) {
            try {
                arth.addItem(item);
            } catch(Exception error) {
                System.err.println("Error: " + error.getMessage());
            }
        }

        arth.displayInventory();
    }
}
