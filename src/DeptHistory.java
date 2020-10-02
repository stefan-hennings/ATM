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
    private double newDept;
    private final LocalDate startDate;
    private String listOfChanges;

    public DeptHistory(double newDept, Employee manager) {
        this.newDept = newDept;
        this.manager = manager;
        this.startDate = LocalDate.now();
        this.listOfChanges = setListOfChanges();
    }

    public Employee getManager() {
        return manager;
    }

    public String getListOfChanges() {
        return listOfChanges;
    }

    public String setListOfChanges() {
        return listOfChanges =
                "\nAnställd som bevilja: " + manager.getName() +
                        "\nNytt belopp efter inbeltalning: " + newDept +
                        "\nDatum för ändring: " + startDate;
    }
}
