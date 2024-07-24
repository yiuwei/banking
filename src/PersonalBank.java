// Identify properties for PersonalBank
public class PersonalBank {
    private final String accountNumber;
    private double balance;

    // Create banking constructor
    public PersonalBank(String accountNumber, String accountHolder, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    // Get account number
    public String getNumber() {
        return accountNumber;
    }

    // Get balance info
    public double getBalance() {
        return balance;
    }

    // Get deposit info
    public void deposit(double cost) {
        if (cost > 0) {
            balance += cost;
        }
    }

    // Get withdraw data
    public boolean withdraw(double cost) {
        if (cost > 0 && cost <= balance) {
            balance -= cost;
            return true;
        }
        return false;
    }

    // Get transfer data
    public boolean transfer(PersonalBank userAccount, double cost) {
        if (this.withdraw(cost)) {
            userAccount.deposit(cost);
            return true;
        }
        return false;
    }
}
