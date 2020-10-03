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
    private final double amountPaid;
    private final double debtRemaining;
    private final LocalDate startDate;
    
    public DebtHistory(double debtRemaining, double amountPaid) {
        this.debtRemaining = debtRemaining;
        this.amountPaid = amountPaid;
        this.startDate = LocalDate.now();
    }
    
    public double getDebtRemaining() {
        return debtRemaining;
    }
    
    public void printChanges() {
        Utility.println(
                "Inbetalningsdag: " + startDate +
                "\nInbetalat: " + amountPaid +
                "\nKvarstående skuld: " + debtRemaining);
    }
    
}
