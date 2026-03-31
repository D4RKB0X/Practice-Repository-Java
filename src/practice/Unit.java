package practice;

public class Unit {
    private final String name;
    private final int health;
    private final int damage;
    private final int armor;

    public Unit(String n, int h, int d, int a) {
        this.name = n;
        this.health = h;
        this.damage = d;
        this.armor = a;
    }

    public String getName() { return name; }
    public int getHealth() { return health; }
    public int getDamage() { return damage; }
    public int getArmor() { return armor; }

    public void printUnitInfo() {
        System.out.printf("Name: %s | Health: %d | Damage: %d | Armor: %d\n", name, health, damage, armor);
    }
}

class Footman extends Unit {
    public Footman(String n, int h, int d, int a) {
        super(n, h, d, a);
    }
}

class Archer extends Unit {
    public Archer(String n, int h, int d, int a) {
        super(n, h, d, a);
    }
}

class UnitTest {
    public static void main(String[] args) {
        Footman ftmn = new Footman("Footman", 150, 10, 5);
        ftmn.printUnitInfo();

        Archer arch = new Archer("Archer", 100, 15, 1);
        arch.printUnitInfo();
    }
}