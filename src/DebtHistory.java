import java.io.Serializable;
import java.time.LocalDate;

/**
 * Created by Oscar Forss
 * Date: 2020-10-02
 * Time: 11:27
 * Project: ATM
 * Copyright: MIT
 */
public class DebtHistory implements Serializable {
    private final Employee manager;
    private double debt;
    private final LocalDate startDate;
    private String listOfChanges;
    
    public DebtHistory(double debt, Employee manager) {
        this.manager = manager;
        this.debt = debt;
        this.startDate = LocalDate.now();
        setListOfChanges();
    }
    
    public double getDebt() {
        return debt;
    }
    
    public String getListOfChanges() {
        return listOfChanges;
    }
    
    public void setListOfChanges() {
        listOfChanges = "Skuld efter inbetalning: " + debt +
                "\nDatum för ändring: " + startDate +
                "\nBeviljat av: " + manager.getName();
    }
}
