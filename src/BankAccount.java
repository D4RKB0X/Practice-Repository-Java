class BankAccount {
    private final String ownerName;
    private final int accountNumber;
    private double balance;

    public BankAccount(String ownerName, int accountNumber, double balance) {
        if(accountNumber <= 0) {
            throw new IllegalArgumentException("Account number must be positive");
        }
        if(balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }

        this.ownerName = ownerName;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public BankAccount(String ownerName, int accountNumber) {
        this(ownerName, accountNumber, 0.0);
    }

    public String getOwnerName() { return ownerName; }
    public int getAccountNumber() { return accountNumber; }
    public double getBalance() { return balance; }

    public void depositCash(double amount) {
        if(amount <= 0) {
            System.out.println("Deposit must be positive!\n");
        }
        else {
            balance += amount;
            System.out.println("Deposited: " + amount + ".\nNew Balance: " + balance + ".\n");
        }
    }

    public void withdrawCash(double amount) {
        if(amount <= 0) {
            System.out.println("Withdrawal must be positive!\n");
        }
        else {
            if(amount > balance) {
                System.out.println("Insufficient Funds!\n");
            }
            else {
                balance -= amount;
                System.out.println("Withdraw: " + amount + ".\nNew Balance: " + balance + ".\n");
            }
        }
    }

    public void displayInfo() {
        System.out.printf("Account Number: %d | Owner: %s | Balance: %.2f%n", accountNumber, ownerName, balance);
    }
}