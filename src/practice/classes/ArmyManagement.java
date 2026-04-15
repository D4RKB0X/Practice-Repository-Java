import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

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
                System.err.print("Error!\n");
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

    public void unitRemoveSpell(Spell inputSpell) {
        if(!unitSpell.contains(inputSpell)) {
            System.err.printf("%s doesn't have %s!\n", name, inputSpell.getSpellName());
            return;
        }
        else {
            unitSpell.remove(inputSpell);
            System.out.printf("%s has been removed from %s!\n", inputSpell.getSpellName(), name);
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
    private int heroLevel = 1;
    private static final int MAX_HERO_LEVEL = 10;

    private double strength;
    private double agility;
    private double intelligence;

    private static final double ADD_STRENGTH = 2.0;
    private static final double ADD_AGILITY = 1.0;
    private static final double ADD_INTELLIGENCE = 1.5;

    public Hero(String name, int health, int mana, int attackRange, int moveSpeed, AttackType initAttackType, MoveType initMoveType, double strength, double agility, double intelligence) {
        super(name, health, mana, attackRange, moveSpeed, initAttackType, initMoveType);
        this.strength = strength;
        this.agility = agility;
        this.intelligence = intelligence;
    }

    public double getHeroStrength() { return strength; }
    public double getHeroAgility() { return agility; }
    public double getHeroIntelligence() { return intelligence; }

    public void heroLevelUp(int level) {
        if(level <= 0) {
            System.err.print("Invalid Level Input!\n");
            return;
        }

        int levelGained = Math.min(level, MAX_HERO_LEVEL - heroLevel);
        if(levelGained == 0) {
            System.err.printf("%s is already at max level!\n", getUnitName());
            return;
        }
        else {
            heroLevel += levelGained;
            strength += ADD_STRENGTH * levelGained;
            agility += ADD_AGILITY * levelGained;
            intelligence += ADD_INTELLIGENCE * levelGained;
            System.out.printf("%s leveled up!\nNew Attributes:\n- Strength: %.1f\n- Agility: %.1f\n- Intelligence: %.1f\n", getUnitName(), strength, agility, intelligence);
        }
    }

    @Override
    public void printUnitInfo() {
        System.out.printf("Heroname: %s | Level: %d | Health: %d | Mana: %d | Attack Range: %d | Movement Speed: %d\nAttributes:\n- Strength: %.1f\n- Agility: %.1f\n- Intelligence: %.1f\n", getUnitName(), heroLevel, getUnitHealth(), getUnitMana(), getUnitRange(), getUnitSpeed(), strength, agility, intelligence);
    }
}

class Army {
    private final String name;
    private final List<Unit> armyUnits;
    private static final int MAX_UNITS_IN_ARMY = 12;

    public Army(String name) {
        this.name = name;
        this.armyUnits = new ArrayList<Unit>(MAX_UNITS_IN_ARMY);
    }

    public String getArmyName() { return name; }
    public int getArmySize() { return armyUnits.size(); }
    public boolean isEmpty() { return armyUnits.isEmpty(); }

    public void addUnit(Unit inputUnit) {
        if(inputUnit == null) {
            System.err.print("Cannot Add Null!\n");
            return;
        }
        else if (armyUnits.size() >= MAX_UNITS_IN_ARMY) {
            System.err.print("Army Full Capacity!\n");
            return;
        }
        else {
            armyUnits.add(inputUnit);
            System.out.printf("%s has been added to the battlefield!\n", inputUnit.getUnitName());
        }
    }

    public void removeUnit(Unit inputUnit) {
        if(!armyUnits.contains(inputUnit)) {
            System.err.printf("%s is not in %s!\n", inputUnit.getUnitName(), name);
            return;
        }
        else {
            armyUnits.remove(inputUnit);
            System.out.printf("%s has been removed from %s!\n", inputUnit.getUnitName(), name);
        }
    }

    public void printArmyInfo() {
        System.out.printf("Army: %s | Units: %d/%d\n", name, armyUnits.size(), MAX_UNITS_IN_ARMY);
        for(Unit u : armyUnits) {
            u.printUnitInfo();
        }
    }
}

class OOP {
    public static void main(String[] args) {
        System.out.println("=== Starting WC3 Sandbox ===");

        Hero dreadlord   = new Hero("Mal'Ganis", 1200, 400, 150, 290, AttackType.Melee, MoveType.Foot, 22, 15, 18);
        Unit ghoul       = new Unit("Ghoul", 340, 0, 100, 250, AttackType.Melee, MoveType.Foot);
        Unit abomination = new Unit("Abomination", 1100, 0, 110, 190, AttackType.Melee, MoveType.Foot);
        Unit gargoyle    = new Unit("Gargoyle", 500, 0, 300, 400, AttackType.Ranged, MoveType.Fly);

        Unit footman  = new Unit("Footman", 420, 0, 100, 270, AttackType.Melee, MoveType.Foot);
        Unit peasant  = new Unit("Peasant", 250, 0, 10, 220, AttackType.None, MoveType.Foot);
        Unit archer   = new Unit("Archer", 280, 0, 450, 300, AttackType.Ranged, MoveType.Foot);
        Unit knight   = new Unit("Knight", 825, 0, 100, 270, AttackType.Melee, MoveType.Horse);

        Spell deathCoil    = new Spell("Death Coil", 100, 200);
        Spell carrionSwarm = new Spell("Carrion Swarm", 125, 300);
        Spell sleep        = new Spell("Sleep", 150, 0);
        Spell inferno      = new Spell("Inferno", 175, 100);
        Spell dummy        = new Spell("Dummy Spell", 50, 10);

        Army scourge  = new Army("Scourge");
        Army alliance = new Army("Alliance");

        System.out.println("\n=== Building Armies ===");
        scourge.addUnit(dreadlord);
        scourge.addUnit(ghoul);
        scourge.addUnit(abomination);
        scourge.addUnit(gargoyle);

        alliance.addUnit(footman);
        alliance.addUnit(peasant);
        alliance.addUnit(archer);
        alliance.addUnit(knight);

        System.out.println("\n=== Army Rosters ===");
        scourge.printArmyInfo();
        System.out.println();
        alliance.printArmyInfo();

        System.out.println("\n=== Animations ===");
        dreadlord.attackAnimation();
        dreadlord.movementAnimation();
        gargoyle.attackAnimation();
        gargoyle.movementAnimation();
        knight.movementAnimation();
        peasant.attackAnimation();

        System.out.println("\n=== Mal'Ganis Levels Up ===");
        dreadlord.heroLevelUp(3);
        dreadlord.heroLevelUp(9);
        dreadlord.heroLevelUp(0);

        System.out.println("\n=== Learning Spells ===");
        dreadlord.unitAddSpell(deathCoil);
        dreadlord.unitAddSpell(carrionSwarm);
        dreadlord.unitAddSpell(sleep);
        dreadlord.unitAddSpell(inferno);
        dreadlord.unitAddSpell(dummy);

        System.out.println("\n=== Spell Info ===");
        deathCoil.printSpellInfo();
        carrionSwarm.printSpellInfo();
        sleep.printSpellInfo();
        inferno.printSpellInfo();

        System.out.println("\n=== The Battle Begins ===");
        dreadlord.unitUseSpell(deathCoil, footman);
        dreadlord.unitUseSpell(carrionSwarm, footman);
        dreadlord.unitUseSpell(sleep, knight);
        dreadlord.unitUseSpell(inferno, peasant);

        System.out.println("\n=== Unowned Spell Attempt ===");
        footman.unitUseSpell(deathCoil, peasant);

        System.out.println("\n=== Spell Removal ===");
        dreadlord.unitRemoveSpell(sleep);
        dreadlord.unitRemoveSpell(dummy);

        System.out.println("\n=== Army Edge Cases ===");
        scourge.addUnit(null);
        alliance.removeUnit(dreadlord);

        System.out.println("\n=== Updating Alliance Roster ===");
        alliance.removeUnit(footman);
        alliance.printArmyInfo();

        System.out.println("\n=== Final Status ===");
        scourge.printArmyInfo();
        System.out.println();
        alliance.printArmyInfo();
        System.out.println();
        System.out.printf("Footman alive: %b\n", footman.isAlive());
        System.out.printf("Knight alive: %b\n", knight.isAlive());
        System.out.printf("Peasant alive: %b\n", peasant.isAlive());
        System.out.printf("Mal'Ganis alive: %b\n", dreadlord.isAlive());

        System.out.println("\n=== The Scourge Prevails. Sandbox Complete. ===");
    }
}
