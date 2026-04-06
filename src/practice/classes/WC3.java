package practice.classes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

abstract class Buildings {
    private final String name;
    public Buildings(String name) { this.name = name; }
    public String getName() { return name; }

    public abstract void printInfo();
}

class AltarOfHeroes extends practice.experiments.Buildings {
    private final HashMap<String, Heroes> availableHeroes = new HashMap<>();
    public AltarOfHeroes(String name) { super(name); }

    public void registerHero(String ID, Heroes hero) {
        availableHeroes.put(ID, hero);
    }

    public void summonHero(String ID, List<Heroes> army) {
        Heroes hero = availableHeroes.get(ID);
        if(hero != null) {
            System.out.printf("%s is summoned into the battlefield!\n", hero.getName());
            army.add(hero);
        }
        else {
            System.out.println("Hero not available.");
        }
    }

    @Override
    public void printInfo() {
        System.out.printf("%s is here it trains heroes!\n\n", getName());
    }
}

abstract class Heroes {
    private final String name;
    private final int damage;

    public Heroes(String name, int damage) {
        this.name = name;
        this.damage = damage;
    }

    public String getName() { return name; }
    public int getDamage() { return damage; }

    public abstract void attackAnimation();
    public void printInfo() {
        System.out.printf("Name: %s | Damage: %d\n", getName(), getDamage());
    }
}

class DeathKnights extends Heroes {
    public DeathKnights(String name, int damage) { super(name, damage); }

    @Override
    public void attackAnimation() {
        System.out.printf("%s swings their blade!\n", getName());
    }
}

class Paladins extends Heroes {
    public Paladins(String name, int damage) { super(name, damage); }

    @Override
    public void attackAnimation() {
        System.out.printf("%s slams their hammers!\n", getName());
    }
}

class Archmages extends Heroes {
    public Archmages(String name, int damage) { super(name, damage); }

    @Override
    public void attackAnimation() {
        System.out.printf("%s casts fireball!\n", getName());
    }
}

class WC3 {
    public static void main(String[] args) {
        DeathKnights Arthas = new DeathKnights("Arthas", 25);
        Paladins Uther = new Paladins("Uther", 20);
        Archmages Antonidas = new Archmages("Antonidas", 20);
        AltarOfHeroes Altar = new AltarOfHeroes("Altar of Heroes");

        Altar.printInfo();

        Altar.registerHero("U000", Arthas);
        Altar.registerHero("Hpal", Uther);
        Altar.registerHero("7885", Antonidas);

        List<Heroes> army = new ArrayList<>();

        Altar.summonHero("U000", army);
        Altar.summonHero("Hpal", army);
        Altar.summonHero("7885", army);

        System.out.print("\n---Army ready!---\n");
        for (Heroes hero : army) {
            hero.printInfo();
            hero.attackAnimation();
            System.out.println();
        }
    }
}
