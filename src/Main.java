class Unit {
    private final String name;
    private final int health;
    private final int damage;

    public Unit(String name, int health, int damage) {
        this.name = name;
        this.health = health;
        this.damage = damage;
    }

    public String getName() { return name; }
    public int getHealth() { return health; }
    public int getDamage() { return damage; }

    public void printUnitInfo() {
        System.out.println("Name: " + name + " | Health: " + health + " | Damage: " + damage);
    }
}

public class Main {
    static void main(String[] args) {
        Unit ftmn = new Unit("Footman", 150, 10);
        ftmn.printUnitInfo();
    }
}