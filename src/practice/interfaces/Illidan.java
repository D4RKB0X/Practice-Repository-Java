import java.util.ArrayList;
import java.util.List;

interface Animations {
    void attackAnimation();
    void movementAnimation();
}

class Spell {
    private final String name;
    private final int mana;

    public Spell(String n, int m) {
        this.name = n;
        this.mana = m;
    }

    public String getSpellName() { return name; }
    public int getSpellMana() { return mana; }

    public void printSpellInfo() {
        System.out.printf("Spellname: %s | Mana Cost: %d\n", name, mana);
    }

    public void castSpell() {
        System.out.printf("%s is casted! Costs %d mana!\n", name, mana);
    }
}

abstract class Unit {
    private final String name;
    private final int maxHealth;
    private int mana;
    boolean isDemon;

    public Unit(String n, int h, int m,boolean initStatus) {
        this.name = n;
        this.maxHealth = h;
        this.mana = m;
        this.isDemon = initStatus;
    }

    public String getUnitName() { return name; }
    public int getUnitHealth() { return maxHealth; }
    public int getUnitMana() { return mana; }
    public boolean isOn() { return isDemon; }

    public void useMana(int amount) {
        this.mana -= amount;
    }

    public void printUnitInfo() {
        System.out.printf("Unitname: %s | Health: %d | Mana: %d\n", name, maxHealth, mana);
    }
}

class DemonHunter extends Unit implements Animations {
    private final List<Spell> unitSpells;
    private int bonusHealth = 500;
    private boolean transformed = false;
    public DemonHunter(String n, int h, int m, boolean iD) {
        super(n, h, m, iD);
        this.unitSpells = new ArrayList<Spell>(4);
    }

    public void addSpell(Spell aS) {
        if (aS == null) {
            throw new IllegalArgumentException("Spell cannot be Null!\n");
        }
        else if (unitSpells.size() >= 4) {
            throw new IllegalStateException("Max spell capacity reached!\n");
        }
        else {
            unitSpells.add(aS);
            System.out.printf("- %s spell has been added to %s!\n", aS.getSpellName(), getUnitName());
        }
    }

    public void unitCastSpell(Spell cS) {
        if (!unitSpells.contains(cS)) {
            throw new IllegalArgumentException( getUnitName() + " doesn't have the spell: " + cS.getSpellName());
        }
        else if (getUnitMana() < cS.getSpellMana()) {
            throw new IllegalStateException( getUnitName() + " doesn't have enough mana!");
        }
        else {
            cS.castSpell();
            useMana(cS.getSpellMana());
            System.out.printf("Remaining mana: %d\n", getUnitMana());
        }
    }

    public void printSpells() {
        for(Spell sList : unitSpells) {
            sList.printSpellInfo();
        }
    }

    @Override
    public void attackAnimation() {
        if(isOn()) {
            if(!transformed) {
                bonusHealth += getUnitHealth();
                transformed = true;
            }
            System.out.printf("Metamorphosis! %s is now in its demon form!\n- Attacking with fel fireballs!\n- Total Health is now: %dHP!\n", getUnitName(), bonusHealth);
        }
        else {
            System.out.printf("%s is in its normal form!\n- Attacking with the Blades of Azzinoth!\n", getUnitName());
        }
    }

    @Override
    public void movementAnimation() {
        if(isOn()) {
            System.out.printf("Metamorphosis! %s is now in its demon form!\n- %s can fly now!\n", getUnitName(), getUnitName());
        }
        else {
            System.out.printf("\n%s is in its normal form!\n- Walking on its feet!\n", getUnitName());
        }
    }
}

class Illidan {
    public static void main(String[] args) {
        DemonHunter dh = new DemonHunter("Illidan", 2000, 500, false);

        dh.printUnitInfo();

        Spell mb = new Spell("Mana Burn", 100);
        Spell im = new Spell("Immolation", 25);
        Spell ev = new Spell("Evasion", 0);
        Spell me = new Spell("Metamorphosis", 250);

        dh.addSpell(mb);
        dh.addSpell(im);
        dh.addSpell(ev);
        dh.addSpell(me);

        System.out.println("\n=== SPELL LIST ===");
        dh.printSpells();

        System.out.println("\n=== SPELL ACTIONS ===");
        dh.unitCastSpell(mb);

        System.out.println("\n=== ACTIONS ===");
        dh.attackAnimation();
        dh.movementAnimation();
    }
}