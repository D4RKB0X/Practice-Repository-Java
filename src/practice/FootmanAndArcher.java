package practice;

abstract class Hero {
    private final String name;
    private int health;
    private final int damage;

    public Hero(String n, int h, int d) {
        this.name = n;
        this.health = h;
        this.damage = d;
    }

    public String getName() { return name; }
    public int getHealth() { return health; }
    public int getDamage() { return damage; }

    public abstract void attackAnimation();
    public void attackHero(Hero target) {
        if(getHealth() <= 0 || target.getHealth() <= 0) {
            System.out.printf("%s is dead!\n", getName());
        }
        else {
            attackAnimation();
            System.out.printf("%s attacks %s!\n", getName(), target.getName());
            target.takeDamage(getDamage());
        }
    }

    public void takeDamage(int dmg) {
        health -= dmg;

        if(health < 0) {
            health = 0;
            System.out.printf("%s is dead!\n", name);
        }
        else {
            System.out.printf("%s now has %d HP!\n", name, health);
        }
    }

    public void printHeroInfo() {
        System.out.printf("Name: %s | Health: %d | Damage: %d\n", name, health, damage);
    }
}

class DeathKnight extends Hero {
    public DeathKnight(String n, int h, int d) {
        super(n, h, d);
    }

    @Override
    public void attackAnimation() {
        System.out.printf("%s swings their sword!\n", getName());
    }
}

class GeneralRanger extends Hero {
    public GeneralRanger(String n, int h, int d) {
        super(n, h, d);
    }

    @Override
    public void attackAnimation() {
        System.out.printf("%s shoots an arrow!\n", getName());
    }
}

class FootmanAndArcherTest {
    static void main(String[] args) {
        DeathKnight dk = new DeathKnight("Arthas", 150, 10);
        GeneralRanger gr = new GeneralRanger("Sylvanas", 125, 15);

        System.out.print("\n---Battle Simulation---\n");
        dk.printHeroInfo();
        gr.printHeroInfo();

        System.out.print("\n---Battle Started!---\n");
        int numRounds = 1;

        while(dk.getHealth() > 0 && gr.getHealth() > 0) {
            System.out.println("\n--------------------");
            System.out.printf("Round: %d!\n", numRounds);

            dk.attackHero(gr);
            if(gr.getHealth() <= 0) {
                break;
            }
            else {
                System.out.println();
                gr.attackHero(dk);
            }
            numRounds++;
        }

        System.out.println("--------------------\n");
        if (dk.getHealth() <= 0) {
            System.out.printf("%s wins!\n", gr.getName());
        } else {
            System.out.printf("%s wins!\n", dk.getName());
        }
    }
}