import java.util.ArrayList;
import java.util.List;

interface Attackable {
    void attack(Unit target);
}

interface Healable {
    void heal(Unit target);
}

abstract class Unit {
    protected String name;
    protected int health;

    public Unit(String name, int health) {
        this.name = name;
        this.health = health;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health < 0) health = 0;

        System.out.println(name + " took " + damage + " damage. HP: " + health);
    }
}

class Soldier extends Unit implements Attackable {

    private int damage;

    public Soldier(String name, int health, int damage) {
        super(name, health);
        this.damage = damage;
    }

    @Override
    public void attack(Unit target) {
        System.out.println(name + " attacks " + target.getName());
        target.takeDamage(damage);
    }
}

class Priest extends Unit implements Healable {

    private int healAmount;

    public Priest(String name, int health, int healAmount) {
        super(name, health);
        this.healAmount = healAmount;
    }

    @Override
    public void heal(Unit target) {
        target.health += healAmount;
        System.out.println(name + " heals " + target.getName()
                + " for " + healAmount
                + ". HP: " + target.getHealth());
    }
}

class Army {
    private List<Unit> units = new ArrayList<>();

    public void addUnit(Unit unit) {
        units.add(unit);
        System.out.println(unit.getName() + " joined the army.");
    }

    public void showArmy() {
        System.out.println("\n--- Army Units ---");
        for (Unit u : units) {
            System.out.println(u.getName() + " HP: " + u.getHealth());
        }
    }

    public List<Unit> getUnits() {
        return units;
    }
}

public class Main {
    public static void main(String[] args) {

        Soldier s1 = new Soldier("Footman", 100, 20);
        Soldier s2 = new Soldier("Knight", 150, 30);
        Priest p1 = new Priest("Priest", 80, 25);

        Army army = new Army();

        /* --- COLLECTION TEST --- */
        army.addUnit(s1);
        army.addUnit(s2);
        army.addUnit(p1);

        army.showArmy();

        /* --- INTERFACE TEST --- */
        s1.attack(s2);
        s2.attack(s1);

        p1.heal(s1);

        /* --- AFTER ACTION --- */
        army.showArmy();
    }
}
