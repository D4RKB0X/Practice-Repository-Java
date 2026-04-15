package practice.experiments;

import java.util.ArrayList;
import java.util.List;

class Unit {
    private final String name;
    private int mana;
    private final List<Spell> unitSpells;
    private static final int MAX_UNIT_SPELLS = 4;

    public Unit(String name, int mana) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Unit name cannot be null or empty!");
        }
        if (mana < 0) {
            throw new IllegalArgumentException("Mana cannot be negative!");
        }

        this.name = name;
        this.mana = mana;
        this.unitSpells = new ArrayList<>(MAX_UNIT_SPELLS);
    }

    public String getName() { return name; }
    public int getMana() { return mana; }
    public List<Spell> getSpells() { return List.copyOf(unitSpells); }

    public boolean useMana(int amount) {
        if (mana < amount) {
            System.err.printf("Not enough mana! (%d/%d)%n", mana, amount);
            return false;
        }
        mana -= amount;
        System.out.printf("[%s] Mana: %d / %d%n", name, mana + amount, mana);
        return true;
    }

    public void addSpell(Spell spell) {
        if (spell == null) {
            System.err.println("Cannot add a null spell!");
            return;
        }
        if (unitSpells.size() >= MAX_UNIT_SPELLS) {
            System.err.printf("Cannot add %s — max capacity reached!%n", spell.getName());
            return;
        }
        unitSpells.add(spell);
        System.out.printf("%s learned %s!%n", name, spell.getName());
    }

    public void printInfo() {
        System.out.printf("Unit: %s | Mana: %d | Spells: %d/%d%n",
                name, mana, unitSpells.size(), MAX_UNIT_SPELLS);
    }
}

class Spell {
    private final String name;
    private final int manaCost;
    private final int cooldown;
    private int cooldownTimer;

    public Spell(String name, int manaCost, int cooldown) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Spell name cannot be null or empty!");
        if (manaCost < 0 || cooldown < 0)
            throw new IllegalArgumentException("Mana cost and cooldown cannot be negative!");

        this.name = name;
        this.manaCost = manaCost;
        this.cooldown = cooldown;
        this.cooldownTimer = 0;
    }

    public String getName() { return name; }
    public int getManaCost() { return manaCost; }
    public int getCooldown() { return cooldown; }
    public int getTimer() { return cooldownTimer; }
    public boolean isReady() { return cooldownTimer == 0; }

    public boolean tryCast(Unit caster) {
        if (!isReady()) {
            System.err.printf("%s is on cooldown! (%d turns left)%n", name, cooldownTimer);
            return false;
        }
        boolean spent = caster.useMana(manaCost);
        if (!spent) return false;

        cooldownTimer = cooldown;
        System.out.printf("%s cast %s!%n", caster.getName(), name);
        return true;
    }

    public void tick() {
        if (cooldownTimer > 0) {
            cooldownTimer--;
        }
    }

    public void printInfo() {
        System.out.printf("Spell: %s | Cost: %d | CD: %d | Ready: %b%n",
                name, manaCost, cooldown, isReady());
    }
}

class RandomPt3 {
    public static void main(String[] args) {
        Unit arthas = new Unit("Arthas", 200);
        Spell frostbolt = new Spell("Frostbolt", 50, 3);
        Spell blink = new Spell("Blink", 30, 2);

        arthas.addSpell(frostbolt);
        arthas.addSpell(blink);
        arthas.printInfo();

        System.out.println("\n-- Round 1 --");
        frostbolt.tryCast(arthas);
        frostbolt.tryCast(arthas);

        System.out.println("\n-- Tick 3 Seconds --");
        for (int i = 0; i < 3; i++) frostbolt.tick();

        System.out.println("\n-- Round 2 --");
        frostbolt.tryCast(arthas);
        arthas.printInfo();
    }
}
