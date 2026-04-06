package practice.interfaces;

interface Animations {
    void attackAnimation();
    void movementAnimation();
}

abstract class Unit {
    private final String name;
    private final int maxHealth;
    boolean isDemon;

    public Unit(String n, int h, boolean initStatus) {
        this.name = n;
        this.maxHealth = h;
        this.isDemon = initStatus;
    }

    public String getUnitName() { return name; }
    public int getUnitHealth() { return maxHealth; }
    public boolean isOn() { return isDemon; }

    public void printUnitInfo() {
        System.out.printf("Name: %s | Health: %d\n", name, maxHealth);
    }
}

class DemonHunter extends Unit implements Animations {
    private int bonusHealth = 500;
    public DemonHunter(String n, int h, boolean iD) {
        super(n, h, iD);
    }

    @Override
    public void attackAnimation() {
        if(isOn()) {
            bonusHealth += getUnitHealth();
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
            System.out.printf("%s is in its normal form!\n- Walking on its feet!\n", getUnitName());
        }
    }
}

class Illidan {
    public static void main(String[] args) {
        DemonHunter dh = new DemonHunter("Illidan", 2000, true);
        dh.printUnitInfo();
        dh.attackAnimation();
        dh.movementAnimation();
    }
}
