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
        String employeePersonalNumber = getString("Ange ditt personnummer: ");
        String customerName = getString("Ange kundens namn: ");
        String customerPersonalNumber = getString("Ange kundens personnummer: ");
        
        double loanAmount = getDouble("Ange lånets storlek: ");
        double interest = getDouble("Ange lånets räntesats: ");
        
        
        Customer c = getCustomer(customerPersonalNumber);
        Employee e = getEmployee(employeePersonalNumber);
        Loan loan = new Loan(loanAmount, c, e, interest);
        assert c != null;
        c.addLoan(loan);
        System.out.println("Lån på " + c.getLatestLoan().getDebt() + " skapat till " + c.getName());
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
        String perNum = getString("Mata in lånepersonnummer: ");
        String loanId = getString("Mata in lånId: ");
        String employeeId = getString("Mata in anställdast persnummer: ");
        String newInterestString = getString("Mata in nya räntan: ");
        int newInterestRate;
        
        newInterestRate = Integer.parseInt(newInterestString);
        Employee e = getEmployee(employeeId);
        Customer c = getCustomer(perNum);
        if (c != null) {
            Loan l = searchForLoanInListOfLoan(c, loanId);
            if (l == null) {
                System.out.println("Finns inget lån med det ID");
            } else {
                l.updateInterestRate(newInterestRate, e);
            }
        }
        System.out.println("\nRäntan är ändrad till " + newInterestString + "% för lån " + loanId + "\n");
        
    }
    
    /**
     * Writes a list of the history of changes to a loan
     */
    public static void printListOfRateChanges() {
        String loan = getString("Mata in lånID: ");
        String personalNumber = getString("Mata in lånetagarens personnummer: ");
        
        Customer customer = getCustomer(personalNumber);
        
        if (customer != null) {
            Loan l = searchForLoanInListOfLoan(customer, loan);
            if (l == null) {
                System.out.println("Finns inget lån med det ID");
            } else {
                for (int i = 0; i < l.getLoanHistory().size(); i++) {
                    System.out.print(l.getLoanHistory().get(i).getListOfChanges() + "\n");
                }
                
            }
        }
        
    }
    
    /**
     * Writes a list of all loans for a customer
     */
    public static void allLoansForACustomer() {
        String customerName = getString("Mata in kundens namn: ");
        String customerNumber = getString("Mata in personnummer: ");
        
        Customer c = getCustomer(customerNumber);
        assert c != null;
        System.out.println(c.getName() + " har totalt " + c.getLoanList().size() + " lån hos banken");
        for (int i = 0; i < c.getLoanList().size(); i++) {
            System.out.println(
                    "\nLån: " + c.getLoanList().get(i).getLoanID() +
                            "\nTotal skuld: " + c.getLoanList().get(i).getDebt() +
                            "\nRänta på lån: " + c.getLoanList().get(i).getInterestRate() +
                            "\nAnsvarig på banken: " + c.getLoanList().get(i).getManager().getName());
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
        Customer customer = null;
        while (customer == null) {
            customer = getCustomer(getString("Ange ditt personnummer: "));
            if (customer == null) {
                System.out.println("Ogiltigt personnummer! ");
            }
        }
        return customer;
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
        for (var e : employeeList) {
            if (e.getPersonalId().equalsIgnoreCase(personalNumber)) {
                return e;
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

