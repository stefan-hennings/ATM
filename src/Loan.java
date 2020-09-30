import java.util.UUID;

public class Loan {
    private UUID loanNumber;
    private double debt;
    
    public Loan(double debt) {
        this.debt = debt;
    }
    
    public void pay(double amountPaid) {
        this.debt -= amountPaid;
    }
}
