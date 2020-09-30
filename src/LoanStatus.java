import java.time.*;

public class LoanStatus {
    private final Employee manager;
    private final double interestRate;
    private Customer customer;
    protected LocalDate startDate;

    public LoanStatus(Employee manager) {
        this.manager = manager;
        this.interestRate = 3;
        startDate = LocalDate.now();

    }
    
    public LoanStatus(Employee manager, double interestRate) {
        this.manager = manager;
        this.interestRate = interestRate;
        startDate = LocalDate.now();
    }
    
    public Employee getManager() {
        return manager;
    }
    
    public double getInterestRate() {
        return interestRate;
    }
}
