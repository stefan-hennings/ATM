import java.io.Serializable;

public class Employee extends Person implements Serializable {
    private double salary;
    
    public Employee(String name, String personalId, double salary) {
        super(name, personalId);
        this.salary = salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getSalary() {
        return salary;
    }
    
    @Override
    public String toString() {
        return getName();
    }
}
