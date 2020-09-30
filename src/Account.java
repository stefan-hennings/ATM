import java.util.UUID;

public class Account {
    private UUID accountNumber;
    private double accountBalance;
    private double interestRate = 0.03;
    Customer customer;
    
    public Account(double accountBalance, Customer customer) {
        this.accountBalance = accountBalance;
        this.interestRate = interestRate;
        accountNumber = UUID.randomUUID();
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
}
