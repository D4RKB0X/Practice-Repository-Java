package bank;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    Scanner input = new Scanner(System.in);
    private final ArrayList<BankAccount> userAccounts;

    public Menu(ArrayList<BankAccount> userAccounts) {
        if(userAccounts == null) {
            this.userAccounts = new ArrayList<>();
        }
        else {
            this.userAccounts = new ArrayList<>(userAccounts);
        }
    }

    public BankAccount selectAccount() {
        if(userAccounts.isEmpty()) {
            System.out.println("No Accounts Available!\n");
            return null;
        }

        for(int i = 0; i < userAccounts.size(); i++) {
            BankAccount acc = userAccounts.get(i);
            System.out.println("[" + i + "] Account Number: " + acc.getAccountNumber() + " | Owner: " + acc.getOwnerName() + "\n");
        }

        System.out.print("Select Account by Index: ");
        if(!input.hasNextInt()) {
            System.out.println("Invalid Input!\n");
            input.nextLine();
            return null;
        }

        int choice = input.nextInt();
        input.nextLine();

        if(choice < 0 || choice >= userAccounts.size()) {
            System.out.println("Invalid Selection!\n");
            return null;
        }

        return userAccounts.get(choice);
    }

    public void startMenu() {
        int mainChoice;
        do {
            System.out.println("\n--- Banking System ---\n");
            System.out.println("1. Create Account\n");
            System.out.println("2. Select Account\n");
            System.out.println("3. Exit\n");

            System.out.println("Enter your Choice: ");
            mainChoice = input.nextInt();
            input.nextLine();

            switch (mainChoice) {
                case 1: {
                    createAccount();
                    break;
                }
                case 2: {
                    accountMenu();
                    break;
                }
                case 3: {
                    System.out.println("Exiting System. Goodbye!\n");
                    break;
                }
                default: {
                    System.out.println("Invalid Option!\n");
                    break;
                }
            }
        } while (mainChoice != 3);
    }

    public void createAccount() {
        int accNumber;
        String userName;
        double initialBalance;

        System.out.println("Enter Owner Name: ");
        userName = input.nextLine();

        System.out.println("Enter Account Number: ");
        accNumber = input.nextInt();
        input.nextLine();

        System.out.println("Enter Initial Balance: ");
        initialBalance = input.nextDouble();
        input.nextLine();

        BankAccount newAcc = new BankAccount(userName, accNumber, initialBalance);
        addAccount(newAcc);
        System.out.println("Account Created Successfully!");
    }

    public void addAccount(BankAccount account) {
        if(account == null) {
            System.out.println("Cannot Add Null Account!\n");
        }
        else {
            userAccounts.add(account);
        }
    }

    public void accountMenu() {
        BankAccount selected = selectAccount();

        if(selected != null) {
            int menuAction;
            do {
                System.out.println("\n--- Account bank Menu ---\n");
                System.out.println("1. Deposit\n");
                System.out.println("2. Withdraw\n");
                System.out.println("3. Display Info\n");
                System.out.println("4. Back to Main bank.Menu\n");

                System.out.println("Enter your Choice: ");
                menuAction = input.nextInt();
                input.nextLine();

                switch (menuAction) {
                    case 1: {
                        double amount;
                        System.out.println("Enter Amount to Deposit: ");
                        amount = input.nextDouble();
                        input.nextLine();
                        selected.depositCash(amount);
                        break;
                    }
                    case 2: {
                        double amount;
                        System.out.println("Enter Amount to Withdraw: ");
                        amount = input.nextDouble();
                        input.nextLine();
                        selected.withdrawCash(amount);
                        break;
                    }
                    case 3: {
                        selected.displayInfo();
                        break;
                    }
                    case 4: {
                        System.out.println("Returning to Main bank.Menu...\n");
                        break;
                    }
                    default: {
                        System.out.println("Invalid Option!\n");
                        break;
                    }
                }

            }while (menuAction != 4);
        }
    }
}
