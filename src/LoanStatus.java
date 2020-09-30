import java.util.List;

public class LoanStatus {
    private final Employee manager;
    private final double interestRate;
    
    public LoanStatus(Employee manager) {
        this.manager = manager;
        this.interestRate = 3;
    }
    
    public LoanStatus(Employee manager, double interestRate) {
        this.manager = manager;
        this.interestRate = interestRate;
    }
    
    public Employee getManager() {
        return manager;
    }
    
    public double getInterestRate() {
        return interestRate;
    }
}
