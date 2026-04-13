import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

interface Damageable {
    void takeDamage(int damage);
    boolean isAlive();
    String getName();
}

interface Attacking {
    void actionAttack();
}

interface Movement {
    void actionMove();
}

abstract class Entity implements Damageable {
    private final String name;
    private int health;
    
    public Entity(String name, int health) {
        if(name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null nor empty!");
        }
        
        if(health < 0) {
            throw new IllegalArgumentException("Health cannot be negative!");
        }
        
        this.name = name;
        this.health = health;
    }
    
    public String getName() { return name; }
    public int getHealth() { return health; }
    
    @Override
    public boolean isAlive() { 
        return health > 0;
    }
    
    @Override
    public void takeDamage(int damage) {
        if(damage < 0) {
            throw new IllegalArgumentException("Damage cannot be negative!");
        }
        
        if(!isAlive()) {
            System.err.printf("%s is already dead!\n", name);
            return;
        }
        
        health = Math.max(0, health - damage);
        if(!isAlive()) {
            System.err.printf("%s has been decimated to ash!\n", name);
        } 
        
        else {
            System.out.printf("%s has taken %d damage! Remaining Health: %d\n", name, damage, health);
        }
    }
    
    public void printInfo() {
        System.out.printf("Name: %s | Health: %d\n", name, health);
    }
}

class Unit extends Entity implements Attacking, Movement {
    private final int damage;
    
    public Unit(String name, int health, int damage) {
        super(name, health);
        if(damage < 0) {
            throw new IllegalArgumentException("Damage cannot be negative!");
        }
        
        this.damage = damage;
    }
    
    public int getDamage() { return damage; }
    
    public void dealDamage(Damageable target) {
        if(target == null) {
            throw new IllegalArgumentException("Target cannot be null!");
        }
        
        if(!isAlive()) {
            System.out.printf("%s cannot attack because they are dead!\n", getName());
            return;
        }
        
        if(!target.isAlive()) {
            System.err.printf("%s is already dead!\n", target.getName());
            return;
        }
        
        System.out.printf("%s dealt %d damage to the %s!\n", getName(), damage, target.getName());
        target.takeDamage(damage);
    }
    
    @Override
    public void actionAttack() {
        System.out.printf("%s is attacking!\n", getName());
    }
    
    @Override
    public void actionMove() {
        System.out.printf("%s is walking!\n", getName());
    }
    
    @Override
    public void printInfo() {
        System.out.printf("Name: %s | Health: %d | Damage: %d\n", getName(), getHealth(), damage);
    }
}

class Building extends Entity {
    private final List<Unit> availUnits;
    private static final int MAX_UNIT_CAPACITY = 4;
    
    public Building(String name, int health) {
        super(name, health);
        this.availUnits = new ArrayList<Unit>(MAX_UNIT_CAPACITY);
    }
    
    public List<Unit> getAvailableUnits() { return List.copyOf(availUnits); }
    
    public void addUnit(Unit inputUnit) {
        if(inputUnit == null) {
            throw new IllegalArgumentException("Unit cannot be null!");
        }
        
        if(availUnits.contains(inputUnit)) {
            System.err.printf("%s already has %s in its roster!\n", getName(), inputUnit.getName());
            return;
        }
        
        if(availUnits.size() >= MAX_UNIT_CAPACITY) {
            System.err.printf("Capacity reached! Cannot add %s to the %s's roster!\n", inputUnit.getName(), getName());
            return;
        }
        
        availUnits.add(inputUnit);
        System.out.printf("Successfully added %s to the %s's roster!\n", inputUnit.getName(), getName());
    }
    
    public void removeUnit(Unit inputUnit) {
        if(inputUnit == null) {
            throw new IllegalArgumentException("Unit cannot be null!");
        }
        
        if(!availUnits.contains(inputUnit)) {
            System.err.printf("%s doesn't have %s!\n", getName(), inputUnit.getName());
            return;
        }
        
        availUnits.remove(inputUnit);
        System.out.printf("Successfully removed %s from the %s's roster!\n", inputUnit.getName(), getName());
    }
    
    public void printRoster() {
        for(Unit units : availUnits) {
            units.printInfo();
        }
    }
}

class Main {
    public static void main(String[] args) {
        // Setup units
        Unit footman = new Unit("Footman", 100, 25);
        Unit grunt   = new Unit("Grunt", 80, 20);
        Unit archer  = new Unit("Archer", 70, 30);
        Unit knight  = new Unit("Knight", 120, 35);
        Unit peasant = new Unit("Peasant", 60, 10);

        // Setup building
        Building barracks = new Building("Barracks", 500);

        // Print info
        footman.printInfo();
        grunt.printInfo();
        System.out.println();

        // Animations
        footman.actionMove();
        footman.actionAttack();
        System.out.println();

        // Combat
        footman.dealDamage(grunt);
        grunt.dealDamage(footman);
        System.out.println();

        // Kill footman
        grunt.dealDamage(footman);
        grunt.dealDamage(footman);
        grunt.dealDamage(footman);
        grunt.dealDamage(footman);
        System.out.println();

        // Dead unit attacks
        footman.dealDamage(grunt);
        System.out.println();

        // Building roster
        barracks.addUnit(archer);
        barracks.addUnit(knight);
        barracks.addUnit(peasant);
        barracks.addUnit(grunt);
        System.out.println();

        // Exceed capacity
        barracks.addUnit(footman);
        System.out.println();

        // Duplicate check
        barracks.addUnit(archer);
        System.out.println();

        // Print roster
        barracks.printRoster();
        System.out.println();

        // Remove unit
        barracks.removeUnit(archer);
        barracks.removeUnit(archer); // already removed
        System.out.println();

        // Defensive checks
        try {
            footman.dealDamage(null);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        try {
            barracks.addUnit(null);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        try {
            footman.takeDamage(-10);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught: " + e.getMessage());
        }
    }
}
