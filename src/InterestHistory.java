import java.time.*;

public class InterestHistory {
    private final Employee manager;
    private final double interestRate;
    private final LocalDate startDate;
    private String listOfChanges;
    
    public InterestHistory(Employee manager, double interestRate) {
        this.manager = manager;
        this.interestRate = interestRate;
        this.startDate = LocalDate.now();
        this.listOfChanges = setListOfChanges();
    }
    
    public Employee getManager() {
        return manager;
    }
    
    public double getInterestRate() {
        return interestRate;
    }
    
    // TODO: 01-Oct-20 Do we want to be able to call up a specific change? As it stands, we can only view all or none 
    public String getListOfChanges() {
        return listOfChanges;
    }
    
    public String setListOfChanges() {
        return listOfChanges =
                "\nAnställd som bevilja: " + manager.getName() +
                "\nNy ränta: " + interestRate +
                "\nDatum för ändring: " + startDate;
    }
    

}
