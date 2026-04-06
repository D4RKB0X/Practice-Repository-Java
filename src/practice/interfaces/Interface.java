package practice.interfaces;

interface Animations{
    void attackAnimation();
    void movementAnimation();
}

abstract class Unit {
    private final String name;
    private final int armor;
    private final int damage;
    boolean isLand;

    public Unit(String n, int a, int d, boolean initStatus) {
        this.name = n;
        this.armor = a;
        this.damage = d;
        this.isLand = initStatus;
    }

    public String getUnitName() { return name; }
    public int getUnitArmor() { return armor; }
    public int getUnitDamage() { return damage; }

    public void printUnitInfo() {
        System.out.printf("Name: %s | Armor: %d | Damage: %d\n", name, armor, damage);
    }
}

class MeleeUnit extends Unit implements Animations {
    public MeleeUnit(String n, int a, int d, boolean iL) { super(n, a, d, iL); }

    @Override
    public void attackAnimation() {
        System.out.printf("%s attacks with a melee weapon!\n", getUnitName());
    }

    @Override
    public void movementAnimation() {
        if(isLand) {
            System.out.printf("%s is slithering to move!\n", getUnitName());
        }
        else {
            System.out.printf("%s is swimming to move!\n", getUnitName());
        }
    }
}

class Interface {
    public static void main(String[] args) {
        MeleeUnit nmyrm = new MeleeUnit("Naga Myrmidon", 2, 36, true);

        nmyrm.printUnitInfo();
        nmyrm.attackAnimation();
        nmyrm.movementAnimation();
    }
}
