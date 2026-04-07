package practice.experiments;

class Worker {
    private final String name;
    private int health;
    private int carriedGold;
    private boolean alive = true;

    public Worker(String name, int health) {
        this.name = name;
        this.health = health;
        this.carriedGold = 0;
    }

    public String getWorkerName() { return name; }
    public int getWorkerHealth() { return health; }
    public int getWorkerCarriedGold() { return carriedGold; }
    public boolean isAlive() { return alive; }

    public void getGold(int amount) { carriedGold += amount; }
    public void emptyHands() { carriedGold = 0; }

    public void printWorkerInfo() {
        System.out.printf("Worker: %s | Health: %d | Carried Gold: %d\n", name, health, carriedGold);
    }
}

class Building {
    private final String name;
    private int currentGold;

    public Building(String name, int currentGold) {
        this.name = name;
        this.currentGold = currentGold;
    }

    public String getBuildingName() { return name; }
    public int getBuildingCurrentGold() { return currentGold; }

    public void reduceGold(Worker peasant, int amount) {
        if (amount <= 0) {
            System.err.println("Gold harvested must be greater than 0!");
            return;
        }
        else if (amount > currentGold) {
            System.err.println("Not enough gold in the mine!");
            return;
        }
        else {
            currentGold -= amount;
            peasant.getGold(amount);
            System.out.printf("%s harvested %d gold!\n", peasant.getWorkerName(), amount);
        }
    }

    public void receiveGold(Worker peasant) {
        int amount = peasant.getWorkerCarriedGold();
        if (amount <= 0) {
            System.err.println("Worker has no gold to deposit!");
            return;
        }
        else {
            currentGold += amount;
            System.out.printf("%s deposited %d gold into %s!\n", peasant.getWorkerName(), amount, this.name);
            peasant.emptyHands();
        }
    }

    public void printBuildingInfo() {
        System.out.printf("Building: %s | Current Gold: %d\n", name, currentGold);
    }
}

class Peasant {
    public static void main(String[] args) {
        Worker peasant = new Worker("Peasant", 100);
        Building goldmine = new Building("Goldmine", 100);
        Building townhall = new Building("Town Hall", 0);

        final int defaultGoldFarm = 10;

        peasant.printWorkerInfo();
        goldmine.printBuildingInfo();
        townhall.printBuildingInfo();

        System.out.println("\n------------------------------\n");

        goldmine.reduceGold(peasant, defaultGoldFarm);
        townhall.receiveGold(peasant);

        System.out.println("\n------------------------------\n");

        peasant.printWorkerInfo();
        goldmine.printBuildingInfo();
        townhall.printBuildingInfo();
    }
}
