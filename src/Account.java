import java.util.UUID;

public class Account {
    private UUID accountNumber;
    private double accountBalance;
    
    public Account(double accountBalance) {
        this.accountBalance = accountBalance;
    }
    
    public void changeBalance(double change) {
        if (accountBalance + change >= 0) {
            accountBalance += change;
        } else {
            throw new IllegalArgumentException("Ansök om lån!");
        }
    }
}
