import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Account implements Serializable {
    private final int accountNumber;
    private double accountBalance;
    private final List<InterestHistory> accountHistory = new ArrayList<>();
    
    public Account(double accountBalance, int accountNumber, Employee employee) {
        this.accountBalance = accountBalance;
        this.accountNumber = accountNumber;
        accountHistory.add(new InterestHistory(employee, 0));
    }
    
    public void changeBalance(double change) {
        if (accountBalance + change >= 0) {
            accountBalance += change;
            Bank.serialize();
        } else {
            throw new IllegalArgumentException("Ansök om lån!");
        }
    }
    
    public void changeInterestRate(Employee employee, double newInterest) {
        accountHistory.add(new InterestHistory(employee, newInterest));
        Bank.serialize();
    }
    
    public int getAccountNumber() {
        return accountNumber;
    }
    
    public double getAccountBalance() {
        return accountBalance;
    }
    
    public InterestHistory getCurrentInterestStatus() {
        return accountHistory.get(accountHistory.size() - 1);
    }
    
    @Override
    public String toString() {
        return String.format("Konto: %d\n" +
                        "Saldo: %.2f\n" +
                        "Ränta: %.2f%%\n" +
                        "Ansvarig: %s\n",
                accountNumber, accountBalance, getCurrentInterestStatus().getInterestRate(), getCurrentInterestStatus().getManager());
    }
}
