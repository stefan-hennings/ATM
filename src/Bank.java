import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Bank {
    static Scanner in = new Scanner(System.in);
    
    public static void testCode() {
        Customer oscar = new Customer("Oscar", "xxx");
        Customer patrik = new Customer("Patrik", "x");
        customerList.add(oscar);
        customerList.add(patrik);
        Employee stefan = new Employee("Stefan", "xx", 60000);
        Employee julia = new Employee("Julia", "x", 25000);
        employeeList.add(stefan);
        employeeList.add(julia);
        
        Loan loan1 = new Loan(25000, oscar, stefan, 2, 1);
        Loan loan2 = new Loan(35000, oscar, stefan, 3, 2);
        Loan loan3 = new Loan(10000, patrik, julia, 2, 1);
        Loan loan4 = new Loan(15000, patrik, julia, 4, 2);
        
        oscar.addLoan(loan1);
        oscar.addLoan(loan2);
        patrik.addLoan(loan3);
        patrik.addLoan(loan4);
    }
    
    static List<Customer> customerList = new ArrayList<>();
    static List<Employee> employeeList = new LinkedList<>();
    
    
    public static void welcomeMenu() {
        String input;
        System.out.println("\nVälj vad du vill göra:\n" +
                "1. Lägg till ny kund\n" +
                "2. Anställ någon\n" +
                "3. Öppna konto\n" +
                "4. Öppna lån\n" +
                "5. Hantera lån\n" +
                "6. Avsluta");
        
        input = in.nextLine();
        
        switch (input) {
            case "1" -> createCustomer();
            case "2" -> createEmployee();
            case "3" -> createAccount();
            case "4" -> createLoan();
            case "5" -> handleLoanMenu();
            case "6" -> exitMenu();
            default -> System.out.println("Ange ett giltigt val! (1-5)");
        }
        welcomeMenu();
    }
    
    public static void createCustomer() {
        while (true) {
            String name = getString("Mata in kundens namn: ");
            String personalNumber = getString("Mata in kundens personnummer: ");
            // TODO: 01-Oct-20 Reverse logic
            try {
                getCustomer(personalNumber);
                System.out.println("Kunden finns redan! ");
            } catch (CustomerNotFoundException e) {
                customerList.add(new Customer(name, personalNumber));
                break;
            }
        }
        
    }
    
    public static void createEmployee() {
        
        String name = getString("Mata in den anställdas namn: ");
        String personalNumber = getString("Mata in den anställdas personnummer: ");
        double salary = getDouble("Mata in den anställdas lön: ");
        
        employeeList.add(new Employee(name, personalNumber, salary));
        System.out.println("Ny anställd skapad");
        
    }
    
    public static void createAccount() {
        Customer customer = findCustomer();
        double insertAmount = getDouble("Ange belopp att sätta in: ");
        customer.addAccount(new Account(insertAmount, customer));
    }
    
    public static void createLoan() {
        Employee employee = findEmployee();
        Customer customer = findCustomer();
    
        double loanAmount = getDouble("Ange lånets storlek: ");
        double interest = getDouble("Ange lånets räntesats: ");
        
        Loan loan = new Loan(loanAmount, customer, employee, interest);
        customer.addLoan(loan);
        System.out.println("Lån på " + customer.getLatestLoan().getDebt() + " skapat till " + customer.getName());
        welcomeMenu();
    }
    
    public static void handleLoanMenu() {
        String input;
        System.out.println("Vad vill du göra?\n" +
                "1. Ändra räntan på lån\n" +
                "2. Skriva ut lista med ändringar för ett lån\n" +
                "3. Skriva ut lista för kundens alla lån\n" +
                "4. Gå till huvudmenyn");
        
        input = in.nextLine();
        
        switch (input) {
            case "1" -> changeInterestRateOnLoan();
            case "2" -> printListOfRateChanges();
            case "3" -> allLoansForACustomer();
            case "4" -> welcomeMenu();
            default -> System.out.println("Ange ett giltigt val! (1-3)");
        }
        handleLoanMenu();
    }
    
    /**
     * Method that changes the value of the loan rate
     */
    public static void changeInterestRateOnLoan() {
        String loanId = getString("Mata in lånId: ");
        
        double newInterestRate = getDouble("Mata in nya räntan: ");
        Employee employee = findEmployee();
        Customer customer = findCustomer();
        Loan loan = searchForLoanInListOfLoan(customer, loanId);
        if (loan == null) {
            System.out.println("Finns inget lån med det ID");
        } else {
            loan.updateInterestRate(newInterestRate, employee);
        }
        
        System.out.println("\nRäntan är ändrad till " + newInterestRate + "% för lån " + loanId + "\n");
        
    }
    
    /**
     * Writes a list of the history of changes to a loan
     */
    public static void printListOfRateChanges() {
        String loanId = getString("Mata in lånID: ");
        Customer customer = findCustomer();
        
        Loan loan = searchForLoanInListOfLoan(customer, loanId);
        if (loan == null) {
            System.out.println("Finns inget lån med det ID");
        } else {
            for (int i = 0; i < loan.getLoanHistory().size(); i++) {
                System.out.print(loan.getLoanHistory().get(i).getListOfChanges() + "\n");
            }
        }
    }
    
    /**
     * Writes a list of all loans for a customer
     */
    public static void allLoansForACustomer() {
        Customer customer = findCustomer();
        System.out.println(customer.getName() + " har totalt " + customer.getLoanList().size() + " lån hos banken");
        for (int i = 0; i < customer.getLoanList().size(); i++) {
            Loan currentLoan = customer.getLoanList().get(i);
            System.out.println(
                    "\nLån: " + currentLoan.getLoanID() +
                            "\nTotal skuld: " + currentLoan.getDebt() +
                            "\nRänta på lån: " + currentLoan.getInterestRate() +
                            "\nAnsvarig på banken: " + currentLoan.getManager().getName());
        }
        System.out.println();
        handleLoanMenu();
    }
    
    public static void exitMenu() {
        String input = getString("Är du säker på att du vill avsluta? (j/n)");
        if (input.equalsIgnoreCase("j")) {
            System.out.println("Tack för besöket! Välkommen åter!");
            System.exit(0);
        } else {
            System.out.println("Stanna så länge du vill!");
            welcomeMenu();
        }
    }
    
    public static Customer findCustomer() {
        while (true) {
            try {
                return getCustomer(getString("Ange kundens personnummer: "));
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
        while (true) {
            try {
                return getEmployee(getString("Ange kundens personnummer: "));
            } catch (EmployeeNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    public static Employee getEmployee(String personalNumber) {
        for (Employee employee : employeeList) {
            if (employee.getPersonalId().equalsIgnoreCase(personalNumber)) {
                return employee;
            }
        }
        throw new EmployeeNotFoundException("Det finns ingen anställd med det person numret. ");
    }
    
    
    private static String getString(String question) {
        String response = null;
        while (response == null || response.isEmpty()) {
            System.out.print(question);
            response = in.nextLine();
        }
        return response;
    }
    
    private static double getDouble(String question) {
        while (true) {
            try {
                System.out.print(question);
                return Double.parseDouble(in.nextLine().replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.println("Ogiltigt värde! ");
            }
        }
    }
    
    private static int getInt(String question) {
        while (true) {
            try {
                System.out.print(question);
                return Integer.parseInt(in.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ogiltigt värde! ");
            }
        }
    }
    
    /**
     * Search for a loan in a list of loans
     *
     * @param c      Customer of the the loan
     * @param loanId ID number of the loan
     *
     * @return Loan
     */
    public static Loan searchForLoanInListOfLoan(Customer c, String loanId) {
        for (Loan l : c.getLoanList()) {
            if (l.getLoanID() == Integer.parseInt(loanId)) {
                return l;
            }
        }
        return null;
    }
}

