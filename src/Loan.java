import java.util.List;
import java.util.UUID;

public class Loan {
    private UUID loanNumber;
    private double interestRate = 3;
    private double debt;
    Customer customer;
    private Employee manager;
    private List<Double> interestHistory;
    
    public Loan(double debt, Customer customer, Employee manager, double interestRate) {
        this.debt = debt;
        this.customer = customer;
        this.manager = manager;
        this.interestRate = interestRate;
        loanNumber = UUID.randomUUID();
    
    }
    
    public Loan(double debt) {
        this.debt = debt;
    }
    
    public void pay(double amountPaid) {
        this.debt -= amountPaid;
    }
    
    public UUID getLoanNumber() {
        return loanNumber;
    }
    
    public double getInterestRate() {
        return interestRate;
    }
    
    public double getDebt() {
        return debt;
    }
    
    public Employee getManager() {
        return manager;
    }
    
    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }
    
    public void changeManager(Employee manager) {
        this.manager = manager;
    }
}
