package practice.classes;

import java.util.ArrayList;
import java.util.List;

enum AttackType { Melee, Ranged, None }
enum MoveType { Foot, Fly, Hover, Horse, Amphibious, None }

interface AttackBehavior { void attackAnimation(); }
interface MovementBehavior { void movementAnimation(); }

class Unit implements AttackBehavior, MovementBehavior {
    private final String name;
    private int health;
    private int mana;

    private final int attackRange;
    private final int moveSpeed;

    private final AttackType initAttackType;
    private final MoveType initMoveType;

    private final List<Spell> unitSpell;

    private static final int MAX_SPELL_CAPACITY = 4;
    private static final int MAX_RANGE_FOR_MELEE = 200;
    private static final int MAX_RANGE_FOR_RANGE = 1000;
    private static final int FAST_SPEED = 300;
    private static final int SLOW_SPEED = 200;

    public Unit(String name, int health, int mana, int attackRange, int moveSpeed, AttackType initAttackType, MoveType initMoveType) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        else if (health < 0 || mana < 0) {
            throw new IllegalArgumentException("Stats cannot be negative");
        }
        else {
            this.name = name;
            this.health = health;
            this.mana = mana;

            this.attackRange = attackRange;
            this.moveSpeed = moveSpeed;

            this.initAttackType = initAttackType;
            this.initMoveType = initMoveType;

            this.unitSpell = new ArrayList<Spell>(MAX_SPELL_CAPACITY);
        }
    }

    public String getUnitName() { return name; }
    public int getUnitHealth() { return health; }
    public int getUnitMana() { return mana; }
    public int getUnitRange() { return attackRange; }
    public int getUnitSpeed() { return moveSpeed; }
    public List<Spell> getUnitSpells() { return List.copyOf(unitSpell); }
    public boolean isAlive() { return health > 0; }

    public void attackAnimation() {
        switch (initAttackType) {
            case Melee: {
                System.out.printf("%s attacks in melee!\n", name);
                break;
            }
            case Ranged: {
                System.out.printf("%s attacks from range!\n", name);
                break;
            }
            case None: {
                System.out.printf("%s cannot attack!\n", name);
                break;
            }
            default: {
                System.err.printf("Error!\n");
                break;
            }
        }

        System.out.printf("%s Attack Range: %d\n", name, attackRange);
    }

    public void movementAnimation() {
        switch(initMoveType) {
            case Foot: {
                System.out.printf("%s moves on foot!\n", name);
                break;
            }
            case Fly: {
                System.out.printf("%s flies!\n", name);
                break;
            }
            case Hover: {
                System.out.printf("%s hovers!\n", name);
                break;
            }
            case Horse: {
                System.out.printf("%s gallops!\n", name);
                break;
            }
            case Amphibious: {
                System.out.printf("%s swims!\n", name);
                break;
            }
            case None: {
                System.out.printf("%s cannot move!\n", name);
                break;
            }
            default: {
                System.err.print("Error!\n");
                break;
            }
        }

        System.out.printf("%s Movement Speed: %d\n", name, moveSpeed);
    }

    public void takeDamage(int amount) {
        if(amount >= health) {
            health = 0;
            System.out.printf("%s has been decimated!\n", getUnitName());
        }
        else {
            health = Math.max(0, health - amount);
            System.out.printf("%s took %d damage! Health remaining: %d\n", getUnitName(), amount, health);
        }
    }

    public void useMana(int amount) {
        if(mana < amount) {
            System.err.print("Not Enough Mana!\n");
            return;
        }
        else {
            mana -= amount;
            System.out.printf("Remaining Mana: %d\n", mana);
        }
    }

    public void unitAddSpell(Spell inputSpell) {
        if(inputSpell == null) {
            System.err.print("Cannot Add Null!\n");
            return;
        }

        else if (unitSpell.size() >= MAX_SPELL_CAPACITY) {
            System.err.printf("Cannot add %s to %s! Max Capacity Reached!\n", inputSpell.getSpellName(), getUnitName());
            return;
        }

        else {
            unitSpell.add(inputSpell);
            System.out.printf("%s has been added to %s's abilities!\n", inputSpell.getSpellName(), getUnitName());
        }
    }

    public void unitUseSpell(Spell inputSpell, Unit uTarget) {
        if(!unitSpell.contains(inputSpell)) {
            System.err.printf("%s doesn't have %s ability on them!\n", getUnitName(), inputSpell.getSpellName());
            return;
        }
        else if(mana < inputSpell.getSpellManaCost()) {
            System.err.printf("Cannot cast %s! Not enough Mana!\n", inputSpell.getSpellName());
            return;
        }
        else {
            useMana(inputSpell.getSpellManaCost());
            inputSpell.castSpell(uTarget);
            System.out.printf("%s casted %s to the %s!\n", getUnitName(), inputSpell.getSpellName(), uTarget.getUnitName());
        }
    }

    public void printUnitInfo() {
        System.out.printf("Unitname: %s | Health: %d | Mana: %d | Attack Range: %d | Movement Speed: %d\n", name, health, mana, attackRange, moveSpeed);
    }
}

class Spell {
    private final String name;
    private final int manaCost;
    private final int damage;

    public Spell(String name, int manaCost, int damage) {
        this.name = name;
        this.manaCost = manaCost;
        this.damage = damage;
    }

    public String getSpellName() { return name; }
    public int getSpellManaCost() { return manaCost; }
    public int getSpellDamage() { return damage; }

    public void castSpell(Unit uTarget) {
        System.out.printf("%s has been casted on %s!\n", name, uTarget.getUnitName()); uTarget.takeDamage(damage);
    }

    public void printSpellInfo() {
        System.out.printf("Spellname: %s | Mana Cost: %d\n", name, manaCost);
    }
}

class Hero extends Unit {
    private int strength;
    private int agility;
    private int intelligence;

    public Hero(String name, int health, int mana, int attackRange, int moveSpeed, AttackType initAttackType, MoveType initMoveType, int strength, int agility, int intelligence) {
        super(name, health, mana, attackRange, moveSpeed, initAttackType, initMoveType);
        this.strength = strength;
        this.agility = agility;
        this.intelligence = intelligence;
    }

    public int getHeroStrength() { return strength; }
    public int getHeroAgility() { return agility; }
    public int getHeroIntelligence() { return intelligence; }

    @Override
    public void printUnitInfo() {
        System.out.printf("Heroname: %s | Health: %d | Mana: %d | Attack Range: %d | Movement Speed: %d\nAttributes:\n- Strength: %d\n- Agility: %d\n- Intelligence: %d\n", getUnitName(), getUnitHealth(), getUnitMana(), getUnitRange(), getUnitSpeed(), strength, agility, intelligence);
    }
}

class OOP {
    public static void main(String[] args) {
        System.out.println("--- Starting WC3 Sandbox ---");

        Hero dreadlord = new Hero("Mal'Ganis", 1200, 400, 150, 290, AttackType.Melee, MoveType.Foot, 22, 15, 18 );
        Unit footman = new Unit("Footman", 420, 0, 100, 270, AttackType.Melee, MoveType.Foot);
        Spell deathCoil = new Spell("Death Coil", 100, 200);

        System.out.println("\n---Action: Unit Info---");
        dreadlord.printUnitInfo();

        System.out.println("\n---Action: Testing Animations---");
        dreadlord.attackAnimation();
        dreadlord.movementAnimation();

        System.out.println("\n---Action: Learning & Casting---");
        dreadlord.unitAddSpell(deathCoil);
        dreadlord.unitUseSpell(deathCoil, footman);

        System.out.println("\n---Action: Final Status---");
        footman.printUnitInfo();

        System.out.println("\n--- Sandbox Simulation Complete. ---");
    }
}
