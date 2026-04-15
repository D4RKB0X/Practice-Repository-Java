abstract class Entity {
    protected final String name;
    private int health;
    
    public Entity(String name, int health) {
        if(name == null || name.isBlank()) {
            throw new IllegalArgumentException("Entity's name cannot be empty or null!");
        }
        
        if(health < 0) {
            throw new IllegalArgumentException("Entity's health cannot be negative!");
        }
        
        this.name = name;
        this.health = health;
    }
    
    public String getEntityName() { return name; }
    protected int getEntityHealth() { return health; }
    
    public void takeDamage(int amount) {
        if(amount < 0) {
            throw new IllegalArgumentException("Damage cannot be negative!");
        }
        health = Math.max(0, health - amount);
    }
}

class Unit extends Entity {
    private final int damage;
    
    public Unit(String name, int health, int damage) {
        super(name, health);
        
        if(damage < 0) {
            throw new IllegalArgumentException("Damage cannot be negative!");
        }
        
        this.damage = damage;
    }
    
    public int getUnitDamage() { return damage; }
    public boolean isAlive() { return getEntityHealth() > 0; }
    
    public void dealDamage(Entity target) {
        if(target == null) {
            throw new IllegalArgumentException("Target cannot be null!");
        }
        
        if(!isAlive()) {
            System.err.printf("%s is already dead!\n", getEntityName());
            return;
        }
        
        System.out.printf("%s dealt %d damage!\n", getEntityName(), damage);
        target.takeDamage(damage);
    }
    
    public void printUnitInfo() {
        System.out.printf("Name: %s | Health: %d | Damage: %d\n", getEntityName(), getEntityHealth(), damage);
    }
}

class Destructable extends Entity {
    public Destructable(String name, int health) {
        super(name, health);
    }
    
    public boolean isDestroyed() { return getEntityHealth() <= 0; }
    
    @Override
    public void takeDamage(int amount) {
        if (isDestroyed()) {
            System.err.printf("%s is already destroyed!\n", getEntityName());
            return;
        }
        
        super.takeDamage(amount);
        
        if (isDestroyed()) {
            System.out.printf("%s has been destroyed!\n", getEntityName());
        }
        else {
            System.out.printf("%s took %d damage! Remaining Health: %d\n", getEntityName(), amount, getEntityHealth());
        }
    }
    
    public void takeDamage(Unit attacker) {
        if (attacker == null) {
            throw new IllegalArgumentException("Attacker cannot be null!");
        }
        takeDamage(attacker.getUnitDamage());
    }
}

class Main {
    public static void main(String[] args) {
                Unit knight = new Unit("Knight", 100, 25);
        Unit zombie = new Unit("Zombie", 50, 10);
        Destructable tree = new Destructable("Ancient Tree", 75);

        // Print info
        knight.printUnitInfo();
        zombie.printUnitInfo();
        System.out.println();

        // Unit vs Unit
        knight.dealDamage(zombie);
        zombie.dealDamage(knight);
        System.out.println();

        // Unit vs Destructable
        knight.dealDamage(tree);
        knight.dealDamage(tree);
        knight.dealDamage(tree);
        System.out.println();

        // Attack destroyed tree
        knight.dealDamage(tree);
        System.out.println();

        // Dead unit attacks
        zombie.dealDamage(zombie);
        zombie.dealDamage(zombie);
        zombie.dealDamage(zombie);
        zombie.dealDamage(knight);
        System.out.println();

        // Defensive checks
        try {
            knight.takeDamage(-10);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        try {
            knight.dealDamage(null);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        try {
            tree.takeDamage(-10);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught: " + e.getMessage());
        }
    }
}
