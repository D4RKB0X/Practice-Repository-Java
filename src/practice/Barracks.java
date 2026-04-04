package practice;

import java.util.ArrayList;
import java.util.List;

class Units {
    private final String name;
    private final int health;
    private final int armor;

    public Units(String n, int h, int a) {
        this.name = n;
        this.health = h;
        this.armor = a;
    }

    public String getUnitName() { return name; }
    public int getUnitHealth() { return health; }
    public int getUnitArmor() { return armor; }

    public void printUnitInfo() {
        System.out.printf("Name: %s | Health: %d | Armor: %d\n", name, health, armor);
    }
}

class Buildings {
    private final String name;
    private final List<Units> unitList;

    public Buildings(String n, int reserveSize) {
        this.name = n;
        this.unitList = new ArrayList<Units>(reserveSize);
    }

    public String getBuildingName() { return name; }

    public void addUnit(Units aU) {
        if(aU != null) {
            this.unitList.add(aU);
        }
        else {
            System.out.print("Cannot be null!\n");
        }
    }

    public void showAvailableUnits() {
        for(Units sAU : unitList) {
            sAU.printUnitInfo();
        }
    }
}

class Main {
    public static void main(String[] args) {

        Units ftmn = new Units("Footman", 150, 3);
        Units rfle = new Units("Rifleman", 125, 1);
        Units kngt = new Units("Knight", 250, 5);

        Buildings brcks = new Buildings("Barracks", 3);

        brcks.addUnit(ftmn);
        brcks.addUnit(rfle);
        brcks.addUnit(kngt);

        brcks.showAvailableUnits();
    }
}
