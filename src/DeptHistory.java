import java.io.Serializable;
import java.time.LocalDate;

/**
 * Created by Oscar Forss
 * Date: 2020-10-02
 * Time: 11:27
 * Project: ATM
 * Copyright: MIT
 */
public class DeptHistory implements Serializable {
    private final Employee manager;
    private double debt;
    private final LocalDate startDate;
    private String listOfChanges;

    public DeptHistory(double debt, Employee manager) {
        this.manager = manager;
        this.debt = debt;
        this.startDate = LocalDate.now();
        this.listOfChanges = setListOfChanges();
    }

    public Employee getManager() {
        return manager;
    }

    public double getDebt(){ return debt;}

    public String getListOfChanges() {
        return listOfChanges;
    }

    public String setListOfChanges() {
        return listOfChanges =
                "\nAnställd som bevilja: " + manager.getName() +
                        "\nNytt belopp efter inbeltalning: " +  debt +
                        "\nDatum för ändring: " + startDate;
    }
}
