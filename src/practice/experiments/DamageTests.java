class Unit {
    private final String name;
    private int health;
    private final int damage;
    
    public Unit(String name, int health, int damage) {
        if(name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank!");
        }
        if(health < 0) {
            throw new IllegalArgumentException("Health cannot be negative!");
        }
        if(damage < 0) {
            throw new IllegalArgumentException("Damage cannot be negative!");
        }
        
        this.name = name;
        this.health = health;
        this.damage = damage;
    }
    
    public String getUnitName() { return name; }
    public int getUnitHealth() { return health; }
    public int getUnitDamage() { return damage; }
    public boolean isAlive() { return health > 0; }
    
    public void takeDamage(int amount) {
        if(amount < 0) {
            throw new IllegalArgumentException("Damage cannot be negative!");
        }
        if(!isAlive()) {
            System.err.printf("%s is already dead!\n", name);
            return;
        }
        
        health = Math.max(0, health - amount);
        if(!isAlive()) {
            System.err.printf("%s has been decimated to ash!\n", name);
        }
        else {
            System.out.printf("%s has taken damage! Remaining Health: %d\n", name, health);
        }
    }
    
    public void dealDamage(Unit target) {
        if(target == null) {
            throw new IllegalArgumentException("Target cannot be null!");
        }
        if(!isAlive()) {
            System.err.printf("%s is already dead!\n", name);
            return;
        }
        
        System.out.printf("%s dealt %d damage!\n", name, damage);
        target.takeDamage(damage);
    }
    
    public void printUnitInfo() {
        System.out.printf("Name: %s | Health: %d | Damage: %d\n", name, health, damage);
    }
}

class Main {
    public static void main(String[] args) {
        Unit knight = new Unit("Knight", 100, 25);
        Unit zombie = new Unit("Zombie", 100, 5);

        knight.printUnitInfo();
        zombie.printUnitInfo();
        System.out.println("--------------------");

        // Normal combat
        knight.dealDamage(zombie);
        zombie.dealDamage(knight);
        System.out.println("--------------------");

        // Kill the zombie
        knight.dealDamage(zombie);
        knight.dealDamage(zombie);
        knight.dealDamage(zombie);
        System.out.println("--------------------");

        // Try attacking a dead zombie
        knight.dealDamage(zombie);
        System.out.println("--------------------");

        // Try passing negative damage
        try {
            knight.takeDamage(-10);
        }
        catch (IllegalArgumentException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        // Try passing null target
        try {
            knight.dealDamage(null);
        }
        catch (IllegalArgumentException e) {
            System.out.println("Caught: " + e.getMessage());
        }
    }
}
