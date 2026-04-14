interface Damageable {
    void takeDamage(int damage);
    boolean isAlive();
    String getName();
}

interface Healable {
    void healTarget(Unit target, int healAmount);
}

abstract class Entity implements Damageable {
    private final String name;
    private final int maxHealth;
    private int health;
    
    public Entity(String name, int health) {
        if(name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank nor null!");
        }
        
        if(health < 0) {
            throw new IllegalArgumentException("Health value cannot be negative!");
        }
        
        this.name = name;
        this.maxHealth = health;
        this.health = health;
    }
    
    public String getName() { return name; }
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
    
    protected void setHealth(int health) { this.health = health; }
    
    @Override
    public boolean isAlive() { return health > 0; }
    
    @Override
    public void takeDamage(int damage) {
        if(damage < 0) {
            throw new IllegalArgumentException("Damage value cannot be negative!");
        }
        
        if(!isAlive()) {
            System.out.printf("%s is already dead!\n", getName());
            return;
        }
        
        health = Math.max(0, health - damage);
        if(!isAlive()) {
            System.err.printf("%s has been decimated to ash!\n", getName());
            return;
        } 
        
        else {
            System.out.printf("%s has taken %d damage! Remaining Health: %d/%d\n", getName(), damage, getHealth(), getMaxHealth());
        }
    }
    
    public void printInfo() {
        System.out.printf("Name: %s | Health: %d\n", getName(), getHealth());
    }
}

abstract class Unit extends Entity implements Healable {
    private final int damage;
    private int mana;
    
    public Unit(String name, int health, int damage, int mana) {
        super(name, health);
        if(damage < 0) {
            throw new IllegalArgumentException("Damage value cannot be negative!");
        }
        
        if(mana < 0) {
            throw new IllegalArgumentException("Mana value cannot be negative!");
        }
        
        this.damage = damage;
        this.mana = mana;
    }
    
    public int getUnitDamage() { return damage; }
    public int getUnitMana() { return mana; }
    
    public void dealDamage(Damageable target) {
        if(target == null) {
            throw new IllegalArgumentException("Target cannot be null!");
        }
        
        if(!isAlive()) {
            System.out.printf("%s cannot attack because they are dead!\n", getName());
            return;
        }
        
        if(!target.isAlive()) {
            System.out.printf("Target %s is already dead!\n", target.getName());
            return;
        }
        
        System.out.printf("%s dealt %d damage to the %s!\n", getName(), getUnitDamage(), target.getName());
        target.takeDamage(damage);
    }
    
    @Override
    public void healTarget(Unit target, int healAmount) {
        if(target == null) {
            throw new IllegalArgumentException("Target cannot be null!");
        }
        
        if(!isAlive()) {
            System.out.printf("%s cannot heal because they are dead!\n", getName());
            return;
        }
        
        if(!target.isAlive()) {
            System.err.printf("Target %s is dead! Cannot be healed\n", target.getName());
            return;
        }
        
        else {
            target.setHealth(Math.min(target.getMaxHealth(), target.getHealth() + healAmount));
            System.out.printf("%s healed %s for %d! Remaining Health: %d/%d\n", getName(), target.getName(), healAmount, target.getHealth(), target.getMaxHealth());
        }
    }
    
    @Override
    public void printInfo() {
        System.out.printf("Name: %s | Health: %d/%d | Damage: %d | Mana: %d\n", getName(), getHealth(), getMaxHealth(), getUnitDamage(), getUnitMana());
    }
}

class Footman extends Unit {
    public Footman(String name) {
        super(name, 420, 12, 0);
    }
}

class Priest extends Unit {
    public Priest(String name) {
        super(name, 270, 8, 200);
    }
}

class Main {
    public static void main(String[] args) {
        Footman footman = new Footman("Footman");
        Priest priest = new Priest("Priest");
        
        System.out.println("=== Unit Info ===");
        footman.printInfo();
        priest.printInfo();
        
        System.out.println("\n=== Combat ===");
        priest.dealDamage(footman);
        priest.dealDamage(footman);
        priest.dealDamage(footman);
        
        System.out.println("\n=== Healing ===");
        priest.healTarget(footman, 25);
        
        System.out.println("\n=== Updated Info ===");
        footman.printInfo();
        priest.printInfo();
    }
}
