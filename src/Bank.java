import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Bank {
    static Scanner in = new Scanner(System.in);
    
    private static List<Customer> customerList = new ArrayList<>();
    private static List<Employee> employeeList = new LinkedList<>();
    
    
    public static void welcomeMenu() {
        String input;
        System.out.println("Välj vad du vill göra:\n" +
                "1. Lägg till ny kund\n" +
                "2. Anställ någon\n" +
                "3. Öppna konto\n" +
                "4. Öppna lån\n" +
                "5. Avsluta");
        
        input = in.nextLine();
        
        switch (input) {
            case "1" -> createCustomer();
            case "2" -> createEmployee();
            
            case "3" -> createLoan();

//            case "4":
//                break;
//            case "5":
        }
        
    }
    
    public static void createCustomer() { //TODO: Felhantering, inga duplicates.
        
        String name = getString("Mata in kundens namn: ");
        
        String personalNumber = getString("Mata in kundens personnummer: ");
        customerList.add(new Customer(name, personalNumber));
        
    }
    
    public static void testCode() {
        customerList.add(new Customer("Oscar", "x"));
        customerList.add(new Customer("Patrik", "x"));
        employeeList.add(new Employee("Stefan", "xx", 60000));
        employeeList.add(new Employee("Julia", "x", 25000));
        
    }
    
    public static void createEmployee() {
        String name = getString("Mata in den anställdas namn: ");
        String personalNumber = getString("Mata in den anställdas personnummer: ");
        double salary = Double.parseDouble("Mata in den anställdas lön: ");
        
        employeeList.add(new Employee(name, personalNumber, salary));
        
    }
    
    public static void createLoan() {
        String employeePersonalNumber = getString("Ange ditt personnummer: ");
        String customerName = getString("Ange kundens namn: ");
        String customerPersonalNumber = getString("Ange kundens personnummer: ");
        
        double loanAmount = getDouble("Ange lånets storlek: ");
        double interest = getDouble("Ange lånets räntesats: ");
        
        Customer c = getCustomer(customerName, customerPersonalNumber);
        Employee e = getEmployee(employeePersonalNumber);
        Loan loan = new Loan(loanAmount, c, e, interest);
        assert c != null;
        c.addLoan(loan);
        System.out.println("Lån på " + c.getLoan().getDebt() + " skapat till " + c.getName());
    }
    
    private static String getString(String question) {
        String response = null;
        while (response == null || response.isEmpty()) {
            System.out.print(question);
            response = in.nextLine();
        }
        return response;
    }
    
    private static Double getDouble(String question) {
        while (true) {
            try {
                System.out.println(question);
                return Double.parseDouble(in.nextLine().replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.println("Ogiltigt värde! ");
            }
        }
    }
    
    public static Customer getCustomer(String name, String personalNumber) {
        for (var c : customerList) {
            if (c.getName().equalsIgnoreCase(name.trim()) && c.getPersonalId().equalsIgnoreCase(personalNumber.trim())) {
                return c;
            }
        }
        return null;
    }
    
    public static Employee getEmployee(String personalNumber) {
        for (var e : employeeList) {
            if (e.getPersonalId().equalsIgnoreCase(personalNumber)) {
                return e;
            }
        }
        return null;
    }
}
