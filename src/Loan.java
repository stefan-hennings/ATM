import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Loan {
    private UUID loanNumber;
//    private double interestRate = 3;
    private double debt;
    Customer customer;
//    private Employee manager;
    private List<LoanStatus> loanHistory = new ArrayList<>();
    
    public Loan(double debt, Customer customer, Employee manager, double interestRate) {
        this.debt = debt;
        this.customer = customer;
        loanHistory.add(new LoanStatus(manager, interestRate));
        loanNumber = UUID.randomUUID();
    
    }
    
    public Loan(double debt) {
        this.debt = debt;
    }
    
    public void pay(double amountPaid) {
        debt -= amountPaid;
    }
    
    public UUID getLoanNumber() {
        return loanNumber;
    }
    
    public double getInterestRate() {
        return loanHistory.get(loanHistory.size()-1).getInterestRate();
    }
    
    public double getDebt() {
        return debt;
    }
    
    public Employee getManager() {
        return loanHistory.get(loanHistory.size()-1).getManager();
    }
    
    public void updateInterestRate(double interestRate, Employee manager) {
        loanHistory.add(new LoanStatus(manager, interestRate));
    }
}
