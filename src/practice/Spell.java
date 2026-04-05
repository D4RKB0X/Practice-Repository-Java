package practice;

import java.util.ArrayList;
import java.util.List;

class Spell {
    private final String name;
    private final int mana;

    public Spell(String n, int m) {
        this.name = n;
        this.mana = m;
    }

    public String getSpellName() { return name; }
    public int getSpellMana() { return mana; }

    public void castSpell() {
        System.out.printf("%s is casted!\n", name);
    }
}

class Unit {
    private final String name;
    private int mana;
    private final List<Spell> spellList;

    public Unit(String n, int m) {
        this.name = n;
        this.mana = m;
        this.spellList = new ArrayList<Spell>(3);
    }

    public String getUnitName() { return name; }
    public int getUnitMana() { return mana; }

    public void addSpell(Spell aS) {
        if(aS == null) {
            System.out.printf("Cannot add null spell for %s!\n", name);
            return;
        }
        else if(spellList.size() >= 3) {
            System.out.printf("Spell capacity full! Cannot add %s!\n", aS.getSpellName());
            return;
        }
        else {
            spellList.add(aS);
            System.out.printf("%s has been added to %s!\n", aS.getSpellName(), name);
        }
    }

    public void removeSpell(Spell rS) {
        if(spellList.remove(rS)) {
            System.out.printf("%s has been removed from %s!\n", rS.getSpellName(), name);
        }
        else {
            System.err.print("Already removed or it doesn't exist!\n");
        }
    }

    public void unitCast(Spell uC) {
        if(!spellList.contains(uC)) {
            System.out.printf("%s doesn't have the spell %s!\n", name, uC.getSpellName());
            return;
        }
        else if(mana < uC.getSpellMana()) {
            System.out.printf("%s doesn't have enough mana to cast %s", name, uC.getSpellName());
            return;
        }
        else {
            uC.castSpell();
            mana -= uC.getSpellMana();
            System.out.printf("%s has casted %s, remaining mana is %d\n", name, uC.getSpellName(), mana);
        }
    }

    public void printUnitInfo() {
        System.out.printf("Name: %s | Mana: %d\n", name, mana);

        for(Spell sList : spellList) {
            System.out.print(sList.getSpellName() + " \n");
        }
    }
}

class MainSpell {
    public static void main(String[] args) {
        Unit priest = new Unit("Priest", 100);

        Spell healChain = new Spell("Heal Chain", 30);
        Spell holyLight = new Spell("Holy Light", 50);

        priest.addSpell(healChain);
        priest.addSpell(holyLight);

        priest.printUnitInfo();

        priest.unitCast(healChain);
        priest.unitCast(holyLight);

        priest.removeSpell(healChain);
        priest.printUnitInfo();
    }
}
