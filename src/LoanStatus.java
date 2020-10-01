import java.time.*;

public class LoanStatus {
    private final Employee manager;
    private double interestRate;
    private LocalDate startDate;
    private String listOfChanges;
    
    public LoanStatus(Employee manager, double interestRate) {
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
