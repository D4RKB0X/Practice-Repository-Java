package practice.interfaces;

import java.util.ArrayList;
import java.util.List;

interface ItemActions {
    void buyItem(Hero hero, Item item);
    void sellItem(Hero hero, Item item);
}

class Unit {
    private final String name;
    private int currentGold;
    private int inventoryCount;

    public Unit(String n, int cG, int iC) {
        this.name = n;
        this.currentGold = cG;
        this.inventoryCount = iC;
    }

    public String getUnitName() { return name; }
    public int getUnitCurrentGold() { return currentGold; }
    public int getUnitInventoryCount() { return inventoryCount; }

    public void addGold(int amount) {
        currentGold += amount;
    }

    public void useGold(int amount) {
        currentGold -= amount;
    }

    public void printUnitInfo() {
        System.out.printf("Unit: %s | Current Gold: %d\n", name, currentGold);
    }
}

class Hero extends Unit {
    private final List<Item> itemList;
    public Hero(String n, int cG, int iC) {
        super(n, cG, iC);
        this.itemList = new ArrayList<Item>();
    }

    public boolean removeItem(Item rI) {
        return itemList.remove(rI);
    }

    public void addItem(Item aTTU) {
        if(aTTU == null) {
            System.err.println("Error! Cannot be Null!");
            return;
        }
        else if (itemList.size() >= getUnitInventoryCount()) {
            System.err.println("Inventory is Full!");
            return;
        }
        else {
            itemList.add(aTTU);
        }
    }

    public void printHeroInventory() {
        for(var iList : itemList) {
            iList.printItemInfo();
        }
    }
}

class Item {
    private final String name;
    private final int goldCost;

    public Item(String n, int gC) {
        this.name = n;
        this.goldCost = gC;
    }

    public String getItemName() { return name; }
    public int getItemGoldCost() { return goldCost; }

    public void printItemInfo() {
        System.out.printf("Item: %s | Gold Cost: %d\n", name, goldCost);
    }
}

class Shop implements ItemActions {
    private final String name;
    public Shop(String n) { this.name = n; }
    public String getShopName() { return name; }

    @Override
    public void buyItem(Hero iHero, Item bI) {
        if (iHero.getUnitCurrentGold() < bI.getItemGoldCost()) {
            System.err.println("Not enough Gold!");
            return;
        }
        else {
            iHero.useGold(bI.getItemGoldCost());
            iHero.addItem(bI);
            System.out.printf("%s bought %s!\n", iHero.getUnitName(), bI.getItemName());
        }
    }

    @Override
    public void sellItem(Hero iHero, Item sI) {
        if (iHero.removeItem(sI)) {
            iHero.addGold(sI.getItemGoldCost());
            System.out.printf("%s sold %s!\n", iHero.getUnitName(), sI.getItemName());
        }

        else {
            System.err.println("Item not in inventory!");
        }
    }
}

class InventoryV2 {
    public static void main(String[] args) {
        Hero arth = new Hero("Arthas", 1000, 6);
        Item mod = new Item("Mask of Death", 1000);
        Shop mrktplc = new Shop("Marketplace");

        arth.printUnitInfo();
        mod.printItemInfo();

        mrktplc.buyItem(arth, mod);
        arth.printUnitInfo();
        mrktplc.sellItem(arth, mod);
        arth.printUnitInfo();
    }
}