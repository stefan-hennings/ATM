import java.io.Serializable;
import java.time.*;

public class InterestHistory implements Serializable {
    private final Employee manager;
    private final double interestRate;
    private final LocalDate startDate;
    
    public InterestHistory(Employee manager, double interestRate) {
        this.manager = manager;
        this.interestRate = interestRate;
        this.startDate = LocalDate.now();
    }
    
    public Employee getManager() {
        return manager;
    }
    
    public double getInterestRate() {
        return interestRate;
    }
    
    public void printChanges() {
        Utility.println("\nNy ränta: " + interestRate +
                "\nDatum för ändring: " + startDate +
                "\nBeviljat av: " + manager.getName());
    }
}
