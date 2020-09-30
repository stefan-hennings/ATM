public class Employee extends Person {
    private double salary;
    
    public Employee(String name, String personalId, double salary) {
        super(name, personalId);
        this.salary = salary;
    }
}
