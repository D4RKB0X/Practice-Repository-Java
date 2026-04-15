import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

class Unit {
    private final String name;
    private int health;
    private final int damage;

    public Unit(String name, int health, int damage) {
        if(name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null nor empty!");
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

class Spell {
    private final String name;
    private final int manaCost;
    private final int damage;
    
    public Spell(String name, int manaCost, int damage) {
        if(name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty nor null!");
        }
        
        if(manaCost < 0) {
            throw new IllegalArgumentException("Mana cost cannot be negative!");
        }
        
        if(damage < 0) {
            throw new IllegalArgumentException("Damage cannot be negative!");
        }
        
        this.name = name;
        this.manaCost = manaCost;
        this.damage = damage;
    }
    
    public String getSpellName() { return name; }
    public int getSpellManaCost() { return manaCost; }
    public int getSpellDamage() { return damage; }
    
    public void castSpell(Unit uTarget) {
        if(uTarget == null) {
            throw new IllegalArgumentException("Target cannot be empty nor null!");
        }
        
        if(!uTarget.isAlive()) {
            System.err.printf("%s is already dead!\n", uTarget.getUnitName());
            return;
        }
        
        System.out.printf("%s is casted!\n", name);
        uTarget.takeDamage(damage);
    }
    
    public void printSpellInfo() {
        System.out.printf("Spell: %s | Mana Cost: %d | Damage: %d\n", name, manaCost, damage);
    }
}

class Wizard extends Unit {
    private int mana;
    private List<Spell> spellList;
    private static final int MAX_SPELL_CAPACITY = 4;
    
    public Wizard(String name, int health, int damage, int mana) {
        super(name, health, damage);
        if (mana < 0) {
            throw new IllegalArgumentException("Mana cannot be negative!");
        }

        this.mana = mana;
        this.spellList = new ArrayList<Spell>(MAX_SPELL_CAPACITY);
    }
    
    public int getWizardMana() { return mana; }
    public List<Spell> getUnitSpells() { return List.copyOf(spellList); }
    
    public void useMana(int amount) {
        if(amount < 0) {
            throw new IllegalArgumentException("Mana usage cannot be negative!");
        }
        
        if(mana < amount) {
            System.err.print("Not Enough Mana!\n");
            return;
        }
        
        mana -= amount;
        System.out.printf("Remaining Mana: %d\n", mana);
    }
    
    public void addSpell(Spell inputSpell) {
        if(inputSpell == null) {
            throw new IllegalArgumentException("Spell cannot be empty nor null!");
        }
        
        if(spellList.contains(inputSpell)) {
            System.out.printf("%s already has %s!\n", getUnitName(), inputSpell.getSpellName());
            return;
        }
        
        if(spellList.size() >= MAX_SPELL_CAPACITY) {
            System.err.printf("Max spell capacity reached! It cannot add %s to the spellbook!\n", inputSpell.getSpellName());
            return;
        }
        
        spellList.add(inputSpell);
        System.out.printf("%s has been added to %s's abilities!\n", inputSpell.getSpellName(), getUnitName());
    }
    
    public void removeSpell(Spell inputSpell) {
        if(inputSpell == null) {
            throw new IllegalArgumentException("Spell cannot be empty nor null!");
        }
        
        if(!spellList.contains(inputSpell)) {
            System.err.printf("%s doesn't have %s!\n", getUnitName(), inputSpell.getSpellName());
            return;
        }
        
        spellList.remove(inputSpell);
        System.out.printf("%s has been removed from %s!\n", inputSpell.getSpellName(), getUnitName());
    }
    
    public void unitUseSpell(Spell inputSpell, Unit uTarget) {
        if(inputSpell == null || uTarget == null) {
            throw new IllegalArgumentException("Cannot be empty nor null!");
        }
        
        if(!spellList.contains(inputSpell)) {
            System.err.printf("%s doesn't have %s ability on them!\n", getUnitName(), inputSpell.getSpellName());
            return;
        }
        
        if(mana < inputSpell.getSpellManaCost()) {
            System.err.printf("Cannot cast %s! Not enough Mana!\n", inputSpell.getSpellName());
            return;
        }

        useMana(inputSpell.getSpellManaCost());
        inputSpell.castSpell(uTarget);
    }
}

class Main {
    public static void main(String[] args) {
        // Setup spells
        Spell fireball = new Spell("Fireball", 30, 50);
        Spell frostbolt = new Spell("Frostbolt", 20, 35);
        Spell lightning = new Spell("Lightning", 40, 65);
        Spell arcaneBlast = new Spell("Arcane Blast", 25, 45);
        Spell overloadSpell = new Spell("Overload", 10, 20);

        // Setup units
        Wizard merlin = new Wizard("Merlin", 100, 15, 120);
        Unit grunt = new Unit("Grunt", 80, 20);

        // Print info
        merlin.printUnitInfo();
        grunt.printUnitInfo();
        System.out.println();

        // Add spells
        merlin.addSpell(fireball);
        merlin.addSpell(frostbolt);
        merlin.addSpell(lightning);
        merlin.addSpell(arcaneBlast);
        System.out.println();

        // Try exceeding capacity
        merlin.addSpell(overloadSpell);
        System.out.println();

        // Print spells
        for(Spell s : merlin.getUnitSpells()) {
            s.printSpellInfo();
        }
        System.out.println();

        // Cast spells
        merlin.unitUseSpell(fireball, grunt);
        merlin.unitUseSpell(frostbolt, grunt);
        merlin.unitUseSpell(lightning, grunt);
        System.out.println();

        // Try casting spell not in spellbook
        merlin.unitUseSpell(overloadSpell, grunt);
        System.out.println();

        // Remove a spell
        merlin.removeSpell(fireball);
        merlin.unitUseSpell(fireball, grunt);
        System.out.println();

        // Drain mana completely
        merlin.unitUseSpell(arcaneBlast, grunt);
        merlin.unitUseSpell(arcaneBlast, grunt);
        merlin.unitUseSpell(arcaneBlast, grunt);
        System.out.println();

        // Defensive checks
        try {
            merlin.addSpell(null);
        } catch(IllegalArgumentException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        try {
            merlin.unitUseSpell(null, grunt);
        } catch(IllegalArgumentException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        try {
            merlin.unitUseSpell(frostbolt, null);
        } catch(IllegalArgumentException e) {
            System.out.println("Caught: " + e.getMessage());
        }
    }
}
