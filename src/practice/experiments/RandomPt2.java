package practice.experiments;

import java.util.ArrayList;

class initItem {
    private final String name;
    private final int price;
    private final int level;

    public initItem(String n, int p, int l) {
        this.name = n;
        this.price = p;
        this.level = l;
    }

    public String getItemName() { return name; }
    public int getItemPrice() { return price; }
    public int getItemLevel() { return level; }

    public void printItemInfo() {
        System.out.printf("Item: %s | Price: %d | Level: %d\n", name, price, level);
    }
}

class initShop {
    private final String name;
    private final ArrayList<initItem> items;

    public initShop(String n) {
        this.name = n;
        this.items = new ArrayList<initItem>(12);
    }

    public String getShopName() { return name; }

    public void addItems(initItem aI) {
        if(aI != null) {
            this.items.add(aI);
        }
        else {
            System.err.print("Cannot be null!\n");
        }
    }

    public void showItemShop() {
        for(initItem itemList : items) {
            itemList.printItemInfo();
        }
    }
}

class RandomPt2 {
    public static void main(String[] args) {
        initShop mrktplc = new initShop("Marketplace");

        initItem cok = new initItem("Crown of Kings", 1500, 8);
        initItem mod = new initItem("Mask of Death", 2000, 8);
        initItem ofs = new initItem("Orb of Slow", 1000, 8);

        mrktplc.addItems(cok);
        mrktplc.addItems(mod);
        mrktplc.addItems(ofs);

        mrktplc.showItemShop();
    }
}
