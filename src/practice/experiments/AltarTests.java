package practice.experiments;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

abstract class Building {
    private final String name;
    private final String keyID;

    public Building(String n, String k) {
        this.name = n;
        this.keyID = k;
    }

    public String getName() { return name; }
    public String getKeyID() { return keyID; }
}

class Altar extends Building {
    Map<String, String> heroes = new HashMap<>();
    Map<String, String> hideHeroes = new HashMap<>();
    private final Scanner input;

    public Altar(Scanner input) {
        super("Altar of Heroes", "Halt");
        this.input = input;
    }

    public void initializeAltar() {
        int countHeroes = 0;

        System.out.print("Enter how many <Heroes> you want to add: ");
        countHeroes = input.nextInt();
        input.nextLine();

        if(countHeroes <= 0) {
            countHeroes = 4;
            System.out.print("\n<Error!> Cannot be zero or negative!\n");
            System.out.print("<Error!> Setting up count to default of 4...\n");
            addHeroes(countHeroes);
        }
        else {
            addHeroes(countHeroes);
        }
    }

    public void addHeroes(int countHeroes) {
        for(int i = 0; i < countHeroes; i++) {
            System.out.printf("\nEnter Hero #%d Name: ", i + 1);
            String heroName = input.nextLine();

            System.out.printf("Enter Hero #%d ID: ", i + 1);
            String heroID = input.nextLine();

            if(heroes.containsKey(heroName)) {
                System.out.println(heroName + " already exists in the Altar! Try again.");
                i--;
            }
            else {
                heroes.put(heroName, heroID);
                hideHeroes.put(heroName, heroID);
            }
        }
    }

    public void trainHeroes() {
        System.out.print("\nAvailable Heroes:\n");
        for(var hList : heroes.keySet()) {
            System.out.printf("- %s\n", hList);
        }

        System.out.print("Enter Hero's Name you want to Train: ");
        String heroTrainName = input.nextLine();

        if(heroes.containsKey(heroTrainName)) {
            heroes.remove(heroTrainName);
            System.out.printf("%s has been trained and sent to the battlefield!\n", heroTrainName);
        }
        else {
            System.out.printf("%s is already out in the battlefield!\n", heroTrainName);
        }
    }
}

class Barracks extends Building {
    Map<String, String> units = new HashMap<>();
    private final Scanner input;

    public Barracks(Scanner input) {
        super("Barracks", "Hbar");
        this.input = input;
    }

    public void initializeBarracks() {
        int countUnits = 0;

        System.out.print("Enter how many <Units> you want to add: ");
        countUnits = input.nextInt();
        input.nextLine();

        if(countUnits <= 0) {
            countUnits = 3;
            System.out.print("\n<Error!> Cannot be zero or negative!\n");
            System.out.print("<Error!> Setting up count to default of 3...\n");
            addUnits(countUnits);
        }
        else {
            addUnits(countUnits);
        }
    }

    public void addUnits(int countUnits) {
        for(int i = 0; i < countUnits; i++) {
            System.out.printf("\nEnter Unit #%d Name: ", i + 1);
            String unitName = input.nextLine();

            System.out.printf("Enter Unit #%d ID: ", i + 1);
            String unitID = input.nextLine();

            if(!units.containsKey(unitName)) {
                units.put(unitName, unitID);
            }
            else {
                System.out.println(unitName + " already exists in the Barracks! Try again.");
                i--;
            }
        }
    }

    public void trainUnits() {
        System.out.print("\nAvailable Units:\n");
        for(var uList : units.keySet()) {
            System.out.printf("- %s\n", uList);
        }

        System.out.print("Enter Units's Name you want to Train: ");
        String unitTrainName = input.nextLine();

        if(units.containsKey(unitTrainName)) {
            System.out.printf("%s has been trained and sent to the battlefield!\n", unitTrainName);
        }
        else {
            System.out.printf("%s does not exist in the Barracks!\n", unitTrainName);
        }
    }
}

class Random {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        Altar altarHeroes = new Altar(input);
        Barracks brcksHumans = new Barracks(input);

        altarHeroes.initializeAltar();
        brcksHumans.initializeBarracks();

        altarHeroes.trainHeroes();
        brcksHumans.trainUnits();

        input.close();
    }
}
