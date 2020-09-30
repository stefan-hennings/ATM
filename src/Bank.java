import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Bank {
    static Scanner in = new Scanner(System.in);
    
    private static List<Customer> customerList = new ArrayList<>();
    private static List<Employee> employeeList = new LinkedList<>();
    
    
    public static boolean welcomeMenu() {
        String input;
        System.out.println("Välj vad du vill göra:\n" +
                "1. Lägg till ny kund\n" +
                "2. Anställ någon\n" +
                "3. Öppna konto\n" +
                "4. Öppna lån\n" +
                "5. Avsluta");
        
        input = in.nextLine();
        
        switch (input) {
            case "1" -> {
                createCustomer();
                return true;
            }
            case "2" -> {
                createEmployee();
                return true;
            }
            case "3" -> {
                createAccount();
                return true;
            }
            case "4" -> {
                createLoan();
                return true;
            }
            case "5" -> {
                return !exitMenu();
            }
            default -> {
                System.out.println("Ange ett giltigt val! (1-5)");
                return true;
            }
        }
    }
    
    public static void createCustomer() { //TODO: Felhantering, inga duplicates.
        Customer customer;
        String name = getString("Mata in kundens namn: ");
        String personalNumber = getString("Mata in kundens personnummer: ");
        
        customerList.add(new Customer(name, personalNumber));
        
    }
    
    public static void createTestUsers() {
        customerList.add(new Customer("Oscar", "x"));
        customerList.add(new Customer("Patrik", "x"));
        employeeList.add(new Employee("Stefan", "xx", 60000));
        employeeList.add(new Employee("Julia", "x", 25000));
        
    }
    
    public static void createEmployee() {
        
        String name = getString("Mata in den anställdas namn: ");
        String personalNumber = getString("Mata in den anställdas personnummer: ");
        double salary = getDouble("Mata in den anställdas lön: ");
        
        employeeList.add(new Employee(name, personalNumber, salary));
        
    }
    
    public static void createAccount() {
        Customer customer;
        while (true) {
            try {
                customer = getCustomer(getString("Ange kundens personnummer: "));
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        double insertAmount = getDouble("Ange belopp att sätta in: ");
        customer.addAccount(new Account(insertAmount, customer));
    }
    
    public static void createLoan() {
        Employee employee = getEmployee(getString("Ange ditt personnummer: "));
        Customer customer;
        while (true) {
            try {
                customer = getCustomer(getString("Ange kundens personnummer: "));
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        
        double loanAmount = getDouble("Ange lånets storlek: ");
        double interest = getDouble("Ange lånets räntesats: ");
        
        Loan loan = new Loan(loanAmount, customer, employee, interest);
        
        customer.addLoan(loan);
        System.out.println("Lån på " + customer.getLoan().getDebt() + " skapat till " + customer.getName());
    }
    
    public static boolean exitMenu() {
        System.out.print("Är du säker på att du vill avsluta? (j/n)  ");
        String input = in.nextLine();
        if (input.equalsIgnoreCase("j")) {
            System.out.println("Tack för besöket! Välkommen åter!");
            return true;
        } else {
            System.out.println("Stanna så länge du vill!\n");
            return false;
        }
    }
    
    public static Customer findCustomer() {
        while (true) {
            try {
                return getCustomer(getString("Ange ditt personnummer: "));
            } catch (CustomerNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    public static Customer getCustomer(String personalNumber) {
        for (Customer customer : customerList) {
            if (customer.getPersonalId().equalsIgnoreCase(personalNumber)) {
                return customer;
            }
        }
        throw new CustomerNotFoundException("Det finns ingen kund med det personnummret. ");
    }
    
    public static Employee findEmployee() {
        Employee employee = null;
        while (employee == null) {
            employee = getEmployee(getString("Ange ditt personnummer: "));
            if (employee == null) {
                System.out.println("Ogiltigt personnummer! ");
            }
        }
        return employee;
    }
    
    public static Employee getEmployee(String personalNumber) {
        for (Employee employee : employeeList) {
            if (employee.getPersonalId().equalsIgnoreCase(personalNumber)) {
                return employee;
            }
        }
        return null;
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
                System.out.print(question);
                return Double.parseDouble(in.nextLine().replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.println("Ogiltigt värde! ");
            }
        }
    }
}