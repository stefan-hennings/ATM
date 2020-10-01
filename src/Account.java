import java.util.UUID;

public class Account {
    private String accountId;
    private double accountBalance;
    private double interestRate = 0;
    Customer customer;
    
    public Account(double accountBalance, Customer customer, String accountNumber) {
        this.accountBalance = accountBalance;
        this.accountId = accountNumber;
        this.customer = customer;
    }
    
    public void changeBalance(double change) {
        if (accountBalance + change >= 0) {
            accountBalance += change;
        } else {
            throw new IllegalArgumentException("Ansök om lån!");
        }
    }
    public void changeInterestRate(double newInterest){
        interestRate = newInterest;
    }

    public String getAccountId() {
        return accountId;
    }

    public double getAccountBalance() {
        return accountBalance;
    }
}
