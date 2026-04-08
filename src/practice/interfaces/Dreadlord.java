package practice.interfaces;

import java.util.ArrayList;
import java.util.List;

interface Animation {
    void attackAnimation();
    void movementAnimation();
}

class Hero {
    private final String name;
    private int mana;
    private List<Spell> heroSpells;
    private static final int MAX_SPELLS = 4;

    public Hero(String name, int mana) {
        this.name = name;
        this.mana = mana;
        this.heroSpells = new ArrayList<Spell>(MAX_SPELLS);
    }

    public String getHeroName() { return name; }
    public int getHeroMana() { return mana; }

    public void useMana(int amount) {
        if(mana < amount) {
            System.out.print("Not enough mana!\n");
        }
        else {
            mana -= amount;
        }
    }

    public void addSpell(Spell inputSpell) {
        if(inputSpell == null) {
            System.err.print("Cannot Be Null!\n");
            return;
        }
        else if(heroSpells.size() >= MAX_SPELLS) {
            System.err.print("Hero Spell Capacity Reached!\n");
            return;
        }
        else {
            heroSpells.add(inputSpell);
            System.out.printf("%s Has Been Added To %s's Spells!\n", inputSpell.getSpellName(), name);
        }
    }

    public void useSpell(Spell inputSpell) {
        if(!heroSpells.contains(inputSpell)) {
            System.err.printf("%s Doesn't Have %s!\n", name, inputSpell.getSpellName());
            return;
        }
        else if(mana < inputSpell.getSpellManaCost()) {
            System.err.printf("%s Doesn't Have Enough Mana to Cast %s!\n", name ,inputSpell.getSpellName());
            return;
        }
        else {
            inputSpell.castSpell();
            mana -= inputSpell.getSpellManaCost();
            System.out.printf("%s casted %s! | Remaining Mana: %d\n", name, inputSpell.getSpellName(), mana);
        }
    }

    public void summonUnit(List<SummonUnit> army, SummonUnit inputUnit) {
        if(army == null || inputUnit == null) {
            System.err.print("Cannot Be Null!\n");
            return;
        }
        else {
            System.out.printf("%s summoned %s into the battlefield!\n", getHeroName(), inputUnit.getUnitName());
            army.add(inputUnit);
        }
    }

    public void printHeroInfo() {
        System.out.printf("Heroname: %s | Mana: %d\n", name, mana);
    }
}

class Spell {
    private final String name;
    private final int manaCost;

    public Spell(String name, int manaCost) {
        this.name = name;
        this.manaCost = manaCost;
    }

    public String getSpellName() { return name; }
    public int getSpellManaCost() { return manaCost; }

    public void castSpell() {
        System.out.printf("%s is casted!\n", name);
    }

    public void printSpellInfo() {
        System.out.printf("Spellname: %s | Mana Cost: %d\n", name, manaCost);
    }
}

class Unit {
    private final String name;
    private final int health;
    private final int damage;

    public Unit(String name, int health, int damage) {
        this.name = name;
        this.health = health;
        this.damage = damage;
    }

    public String getUnitName() { return name; }
    public int getUnitHealth() { return health; }
    public int getUnitDamage() { return damage; }

    public void printUnitInfo() {
        System.out.printf("Unitname: %s | Health: %d | Damage: %d\n", name, health, damage);
    }
}

class SummonUnit extends Unit implements Animation {
    public SummonUnit(String name, int health, int damage) {
        super(name, health, damage);
    }

    @Override
    public void attackAnimation() {
        System.out.print("Attacks with their weapons!\n");
    }

    @Override
    public void movementAnimation() {
        System.out.print("Moves with their feets!\n");
    }
}

class Infernal extends SummonUnit implements Animation {
    public Infernal(String name, int health, int damage) {
        super(name, health, damage);
    }

    @Override
    public void attackAnimation() {
        System.out.print("Attacks with power of fel magic!\n");
    }

    @Override
    public void movementAnimation() {
        System.out.print("Moves while burning the ground!\n");
    }
}

class Dreadlord {
    public static void main(String[] args) {
        final int MAX_UNITS_IN_ARMY = 12;
        List<SummonUnit> army = new ArrayList<SummonUnit>(MAX_UNITS_IN_ARMY);
        Infernal inferno = new Infernal("Inferno", 2000, 75);

        Hero dreadlord = new Hero("Balnazzar", 1000);

        Spell breathoffire = new Spell("Breath of Fire", 100);
        Spell sleep = new Spell("Sleep", 25);
        Spell earthquake = new Spell("Earthquake", 150);
        Spell rainofchaos = new Spell("Rain of Chaos", 250);
        Spell felfire = new Spell("Fel Fire", 100);

        System.out.println("--- Prior Hero Status ---");
        dreadlord.printHeroInfo();

        System.out.println("\n--- Testing: Adding Spells ---");
        dreadlord.addSpell(breathoffire);
        dreadlord.addSpell(sleep);
        dreadlord.addSpell(earthquake);
        dreadlord.addSpell(rainofchaos);

        System.out.println("\n--- Testing: Capacity Limit ---");
        dreadlord.addSpell(felfire);

        System.out.println("\n--- Testing: Successful Casting ---");
        dreadlord.useSpell(earthquake);
        dreadlord.useSpell(rainofchaos);
        dreadlord.summonUnit(army, inferno);

        System.out.println("\n--- Testing: Missing Spell ---");
        dreadlord.useSpell(felfire);

        System.out.println("\n--- Testing: Mana Depletion ---");
        dreadlord.useSpell(rainofchaos);

        dreadlord.useSpell(earthquake);

        System.out.println("\n--- Final Hero Status ---");
        dreadlord.printHeroInfo();

        System.out.println("\n--- Player's Army ---");
        for(SummonUnit unit : army) {
            unit.printUnitInfo();
            unit.attackAnimation();
            unit.movementAnimation();
        }
    }
}
