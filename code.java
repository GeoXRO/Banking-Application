import java.util.HashMap;
import java.util.Scanner;

class BankAccount {
    private String accountNumber;
    private String holderName;
    private String pin;
    private double balance;

    public BankAccount(String accountNumber, String holderName, String pin) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.pin = pin;
        this.balance = 0.0;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getHolderName() {
        return holderName;
    }

    public boolean authenticate(String inputPin) {
        return this.pin.equals(inputPin);
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited $" + amount + ". New balance: $" + balance);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrew $" + amount + ". New balance: $" + balance);
        } else {
            System.out.println("Invalid withdrawal amount or insufficient balance.");
        }
    }

    public void transfer(BankAccount recipient, double amount) {
        if (amount > 0 && amount <= balance) {
            this.balance -= amount;
            recipient.deposit(amount);
            System.out.println("Transferred $" + amount + " to " + recipient.getHolderName() + ".");
        } else {
            System.out.println("Invalid transfer amount or insufficient balance.");
        }
    }

    public void displayBalance() {
        System.out.println("Account Balance: $" + balance);
    }
}

class Bank {
    private HashMap<String, BankAccount> accounts;

    public Bank() {
        accounts = new HashMap<>();
    }

    public void registerAccount(String accountNumber, String holderName, String pin) {
        if (accounts.containsKey(accountNumber)) {
            System.out.println("Account number already exists.");
        } else {
            accounts.put(accountNumber, new BankAccount(accountNumber, holderName, pin));
            System.out.println("Account for " + holderName + " created successfully.");
        }
    }

    public BankAccount authenticate(String accountNumber, String pin) {
        BankAccount account = accounts.get(accountNumber);
        if (account != null && account.authenticate(pin)) {
            return account;
        }
        System.out.println("Invalid account number or PIN.");
        return null;
    }
}

public class BankingApplication {
    public static void main(String[] args) {
        Bank bank = new Bank();
        Scanner scanner = new Scanner(System.in);

        // Add sample accounts
        bank.registerAccount("ACC001", "Alice", "1234");
        bank.registerAccount("ACC002", "Bob", "5678");

        while (true) {
            System.out.println("\nBanking Application");
            System.out.println("1. Log in");
            System.out.println("2. Register Account");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Account Number: ");
                    String accountNumber = scanner.next();
                    System.out.print("Enter PIN: ");
                    String pin = scanner.next();
                    BankAccount account = bank.authenticate(accountNumber, pin);

                    if (account != null) {
                        while (true) {
                            System.out.println("\nWelcome, " + account.getHolderName());
                            System.out.println("1. Deposit");
                            System.out.println("2. Withdraw");
                            System.out.println("3. Transfer");
                            System.out.println("4. Check Balance");
                            System.out.println("5. Log Out");
                            System.out.print("Enter your choice: ");
                            int transactionChoice = scanner.nextInt();

                            if (transactionChoice == 1) {
                                System.out.print("Enter amount to deposit: ");
                                double amount = scanner.nextDouble();
                                account.deposit(amount);
                            } else if (transactionChoice == 2) {
                                System.out.print("Enter amount to withdraw: ");
                                double amount = scanner.nextDouble();
                                account.withdraw(amount);
                            } else if (transactionChoice == 3) {
                                System.out.print("Enter recipient account number: ");
                                String recipientAccountNumber = scanner.next();
                                BankAccount recipient = bank.authenticate(recipientAccountNumber, "");
                                if (recipient != null) {
                                    System.out.print("Enter amount to transfer: ");
                                    double amount = scanner.nextDouble();
                                    account.transfer(recipient, amount);
                                } else {
                                    System.out.println("Recipient account not found.");
                                }
                            } else if (transactionChoice == 4) {
                                account.displayBalance();
                            } else if (transactionChoice == 5) {
                                System.out.println("Logging out.");
                                break;
                            } else {
                                System.out.println("Invalid choice. Try again.");
                            }
                        }
                    }
                    break;

                case 2:
                    System.out.print("Enter Account Number: ");
                    String newAccountNumber = scanner.next();
                    System.out.print("Enter Holder Name: ");
                    String holderName = scanner.next();
                    System.out.print("Enter PIN: ");
                    String newPin = scanner.next();
                    bank.registerAccount(newAccountNumber, holderName, newPin);
                    break;

                case 3:
                    System.out.println("Exiting application.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}

