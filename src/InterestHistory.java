import java.io.Serializable;
import java.time.*;

public class InterestHistory implements Serializable {
    private final Employee manager;
    private final double interestRate;
    private final LocalDate startDate;
    private String listOfChanges;
    
    public InterestHistory(Employee manager, double interestRate) {
        this.manager = manager;
        this.interestRate = interestRate;
        this.startDate = LocalDate.now();
        setListOfChanges();
    }
    
    public Employee getManager() {
        return manager;
    }
    
    public double getInterestRate() {
        return interestRate;
    }
    
    public String getListOfChanges() {
        return listOfChanges;
    }
    
    public void setListOfChanges() {
        listOfChanges = "\nNy ränta: " + interestRate +
                "\nDatum för ändring: " + startDate +
                "\nBeviljat av: " + manager.getName();
    }
}
