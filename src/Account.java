import java.util.ArrayList;
import java.util.List;

public class Account {
    private final int accountNumber;
    private double accountBalance;
    private final List<InterestHistory> accountHistory = new ArrayList<>();

//    Customer customer;
    
    public Account(double accountBalance, int accountNumber) {
        this.accountBalance = accountBalance;
        this.accountNumber = accountNumber;
        accountHistory.add(new InterestHistory(null, 0));
    }
    
    public void changeBalance(double change) {
        if (accountBalance + change >= 0) {
            accountBalance += change;
        } else {
            throw new IllegalArgumentException("Ansök om lån!");
        }
    }
    public void changeInterestRate(Employee employee, double newInterest){
        accountHistory.add(new InterestHistory(employee, newInterest));
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public double getAccountBalance() {
        return accountBalance;
    }
}
