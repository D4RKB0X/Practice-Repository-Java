interface Damageable {
    void takeDamage(int damage);
    boolean isAlive();
    String getName();
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
    public boolean isAlive() { return health > 0; }
    
    @Override
    public void takeDamage(int damage) {
        if(damage < 0) {
            throw new IllegalArgumentException("Damage cannot be negative!");
        }
        
        if(!isAlive()) {
            System.err.printf("%s is already dead!\n", getName());
            return;
        }
        
        health = Math.max(0, health - damage);
        if(!isAlive()) {
            System.err.printf("%s has been decimated to ash!\n", getName());
        } 
        
        else {
            System.out.printf("%s has taken %d damage! Remaining Health: %d\n", getName(), damage, getHealth());
        }
        
    }
    
    public void printInfo() {
        System.out.printf("Name: %s | Health: %d\n", getName(), getHealth());
    }
}

class Main {
    public static void main(String[] args) {
        System.out.println("Random Test :)");
    }
}
