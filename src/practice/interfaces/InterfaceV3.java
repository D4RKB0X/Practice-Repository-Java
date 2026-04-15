import java.util.ArrayList;
import java.util.List;

interface Damageable {
    void takeDamage(int damage);
    boolean isAlive();
    String getName();
}

// interface Healable {
//     void healTarget(Unit target, int healAmount);
// }

interface Tickable {
    void onTick();
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
    
    protected void setHealth(int health) {
        if(health < 0) {
            throw new IllegalArgumentException("Health value cannot be negative!");
        }
        
        this.health = health;
    }
    
    @Override
    public boolean isAlive() { return health > 0; }
    
    @Override
    public void takeDamage(int damage) {
        if(damage < 0) {
            throw new IllegalArgumentException("Damage value cannot be negative!");
        }
        
        if(!isAlive()) {
            System.err.printf("%s is already dead!\n", getName());
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
        
        public abstract void printInfo();
}

abstract class Unit extends Entity implements Tickable {
    private final int damage;
    private int mana;
    private int regenPerTick;
    
    private List<Spell> availableSpells;
    private static final int MAX_SPELL_CAPACITY = 4;
    
    public Unit(String name, int health, int damage, int mana, int regenPerTick) {
        super(name, health);
        if(damage < 0) {
            throw new IllegalArgumentException("Damage value cannot be negative!");
        }
        
        if(mana < 0) {
            throw new IllegalArgumentException("Mana value cannot be negative!");
        }
        
        if(regenPerTick < 0) {
            throw new IllegalArgumentException("Regenerate value cannot be negative!");
        }
        
        this.damage = damage;
        this.mana = mana;
        this.regenPerTick = regenPerTick;
        this.availableSpells = new ArrayList<Spell>(MAX_SPELL_CAPACITY);
    }
    
    public int getUnitDamage() { return damage; }
    public int getUnitMana() { return mana; }
    
    public List<Spell> getAvailableSpells() { return List.copyOf(availableSpells); }
    
    public void addSpell(Spell inputSpell) {
        if(inputSpell == null) {
            throw new IllegalArgumentException("Spell cannot be null!");
        }
        
        if(availableSpells.contains(inputSpell)) {
            System.err.printf("%s already has %s in its roster!\n", inputSpell.getSpellName(), getName());
            return;
        }
        
        if(availableSpells.size() >= MAX_SPELL_CAPACITY) {
            System.out.printf("Spell capacity full! Cannot add %s!\n", inputSpell.getSpellName());
            return;
        }
        
        availableSpells.add(inputSpell);
        System.out.printf("%s has been added to %s!\n", inputSpell.getSpellName(), getName());
    }

    public void removeSpell(Spell inputSpell) {
        if(inputSpell == null) {
            throw new IllegalArgumentException("Spell cannot be null!");
        }
        
        if(!availableSpells.contains(inputSpell)) {
            System.err.printf("%s doesn't have %s!\n", getName(), inputSpell.getSpellName());
            return;
        }
        
        availableSpells.remove(inputSpell);
        System.out.printf("%s has been removed from %s!\n", inputSpell.getSpellName(), getName());
    }
    
    public void applyRegen() {
        if(!isAlive()) {
            System.err.printf("%s cannot be healed! They're dead!\n", getName());
            return;
        }
        
        if(regenPerTick <= 0) {
            System.err.printf("%s cannot be healed! Have no HP regen!\n", getName());
            return;
        }
        
        if(getHealth() >= getMaxHealth()) {
            System.err.printf("%s has finished recovering! Health: %d/%d\n", getName(), getHealth(), getMaxHealth());
            return;
        }
        
        setHealth(Math.min(getMaxHealth(), getHealth() + regenPerTick));
        System.out.printf("%s regenerated %d HP! Remaining Health: %d/%d\n", getName(), regenPerTick, getHealth(), getMaxHealth());
    }
    
    @Override
    public void onTick() {
        applyRegen();
    }
    
    public void dealDamage(Damageable target) {
        if(target == null) {
            throw new IllegalArgumentException("Target cannot be null!");
        }
        
        if(!isAlive()) {
            System.err.printf("%s cannot attack because they are dead!\n", getName());
            return;
        }
        
        if(!target.isAlive()) {
            System.err.printf("Target %s is already dead!\n", target.getName());
            return;
        }
        
        System.out.printf("%s dealt %d damage to the %s!\n", getName(), getUnitDamage(), target.getName());
        target.takeDamage(damage);
    }
    
    // @Override
    // public void healTarget(Unit target, int healAmount) {
    //     if(target == null) {
    //         throw new IllegalArgumentException("Target cannot be null!");
    //     }
        
    //     if (healAmount < 0) {
    //         throw new IllegalArgumentException("Heal amount cannot be negative!");
    //     }
        
    //     if(!isAlive()) {
    //         System.err.printf("%s cannot heal because they are dead!\n", getName());
    //         return;
    //     }
        
    //     if(!target.isAlive()) {
    //         System.err.printf("Target %s is dead! Cannot be healed\n", target.getName());
    //         return;
    //     }
        
    //     else {
    //         target.setHealth(Math.min(target.getMaxHealth(), target.getHealth() + healAmount));
    //         System.out.printf("%s healed %s for %d! Remaining Health: %d/%d\n", getName(), target.getName(), healAmount, target.getHealth(), target.getMaxHealth());
    //     }
    // }
    
    @Override
    public void printInfo() {
        System.out.printf("Name: %s | Health: %d/%d | Damage: %d | Mana: %d\n", getName(), getHealth(), getMaxHealth(), getUnitDamage(), getUnitMana());
    }
}

abstract class Building extends Entity {
    private List<Unit> availableUnits;
    private static final int MAX_UNIT_CAPACITY = 4;
    
    public Building(String name, int health) {
        super(name, health);
        this.availableUnits = new ArrayList<Unit>(MAX_UNIT_CAPACITY);
    }
    
    public List<Unit> getAvailableUnits() { return List.copyOf(availableUnits); }
    
    public void addUnit(Unit inputUnit) {
        if(inputUnit == null) {
            throw new IllegalArgumentException("Unit cannot be null!");
        }
        
        if(availableUnits.contains(inputUnit)) {
            System.err.printf("%s already has %s in its roster!\n", getName(), inputUnit.getName());
            return;
        }
        
        if(availableUnits.size() >= MAX_UNIT_CAPACITY) {
            System.err.printf("Capacity reached! Cannot add %s to the %s's roster!\n", inputUnit.getName(), getName());
            return;
        }
        
        availableUnits.add(inputUnit);
        System.out.printf("Successfully added %s to the %s's roster!\n", inputUnit.getName(), getName());
    }
    
    public void removeUnit(Unit inputUnit) {
        if(inputUnit == null) {
            throw new IllegalArgumentException("Unit cannot be null!");
        }
        
        if(!availableUnits.contains(inputUnit)) {
            System.err.printf("%s doesn't have %s!\n", getName(), inputUnit.getName());
            return;
        }
        
        availableUnits.remove(inputUnit);
        System.out.printf("Successfully removed %s from the %s's roster!\n", inputUnit.getName(), getName());
    }
    
    public void printRoster() {
        if(availableUnits.isEmpty()) {
            System.err.printf("%s roster is empty!\n", getName());
            return;
        }
        
        for(Unit units : availableUnits) {
            units.printInfo();
        }
    }
}

abstract class Spell {
    private final String name;
    private final int manaCost;
    
    public Spell(String name, int manaCost) {
        if(name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank nor null!");
        }
        
        if(manaCost < 0) {
            throw new IllegalArgumentException("Mana cost cannot be negative!");
        }
        
        this.name = name;
        this.manaCost = manaCost;
    }
    
    public String getSpellName() { return name; }
    public int getSpellManaCost() { return manaCost; }
    
    public abstract void castSpell(Unit caster, Unit target);
    public abstract void printSpellInfo();
}

class Main {
    public static void main(String[] args) {
        System.out.println("Hello Lain Iwakura!");
    }
}
