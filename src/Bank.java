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
        
        oscar.grantLoan(25000, stefan, 2); //Loan 1
        oscar.grantLoan(35000, stefan, 3); //Loan 2
        patrik.grantLoan(10000, julia, 2); //Loan 1
        patrik.grantLoan(15000, julia, 4); //Loan 2
        
        oscar.addAccount(30000, stefan);
        patrik.addAccount(100000, julia);
    }
    
    private static final List<Customer> customerList = new ArrayList<>();
    private static final List<Employee> employeeList = new LinkedList<>();
    
    
    public static void welcomeMenu() {
        String input;
        boolean running = true;
        while (running) {
            println("\nVälj vad du vill göra:\n" +
                    "1. Lägg till ny kund\n" +
                    "2. Anställ någon\n" +
                    "3. Konto\n" +
                    "4. Lån\n" +
                    "5. Avsluta");
            
            input = in.nextLine();
            
            switch (input) {
                case "1" -> createCustomer();
                case "2" -> createEmployee();
                case "3" -> accountMenu();
                case "4" -> loanMenu();
                case "5" -> running = exitMenu();
                default -> println("Ange ett giltigt val! (1-5)");
            }
        }
    }
    
    public static void createCustomer() {
        while (true) {
            String name = getString("Mata in kundens namn: ");
            String personalNumber = getString("Mata in kundens personnummer: ");
            // TODO: 01-Oct-20 Reverse logic
            try {
                getCustomer(personalNumber);
                println("Kunden finns redan! ");
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
        println("Ny anställd skapad");
        
    }
    
    private static void accountMenu() {
        String input;
        Customer customer = findCustomer();
        while (true) {
            println("\nVälj vad du vill göra:\n" +
                    "1. Öppna nytt konto\n" +
                    "2. Gör insättning\n" +
                    "3. Gör uttag\n" +
                    "4. Saldo\n" +
                    "5. Ändra ränta\n" +
                    "6. Visa alla konton\n" +
                    "7. Ändra kund\n" +
                    "8. Återgå till huvudmenyn");
            input = in.nextLine();
            
            switch (input) {
                case "1" -> customer.createAccount();
                case "2" -> customer.accountDeposit();
                case "3" -> customer.accountWithdraw();
                case "4" -> customer.viewAccountBalance();
                case "5" -> customer.changeAccountInterestRate();
                case "6" -> customer.viewAllAccounts();
                case "7" -> customer = findCustomer();
                case "8" -> {return;}
                default -> println("Ange ett giltigt val! (1-7)");
            }
        }
    }
    
    public static void loanMenu() {
        
        String input;
        while (true) {
            println("\nVälj vad du vill göra:\n" +
                    "1. Ansök om nytt lån\n" +
                    "2. Ändra räntan på ett befintligt lån lån\n" +
                    "3. Skriva ut lista med ändringar för ett lån\n" +
                    "4. Skriva ut lista för kundens alla lån\n" +
                    "5. Gå till huvudmenyn");
            
            input = in.nextLine();
            
            switch (input) {
                case "1" -> createLoan();
                case "2" -> changeInterestRateOnLoan();
                case "3" -> printListOfRateChanges();
                case "4" -> allLoansForACustomer();
                case "5" -> {return;}
                default -> println("Ange ett giltigt val! (1-5)");
            }
        }
    }
    
    
    public static void createLoan() {
        Employee employee = findEmployee();
        Customer customer = findCustomer();
        
        double loanAmount = getDouble("Ange lånets storlek: ");
        double interest = getDouble("Ange lånets räntesats: ");
        
        customer.grantLoan(loanAmount, employee, interest);
        println("Lån på " + customer.getLatestLoan().getDebt() + " skapat till " + customer.getName());
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
            println("Finns inget lån med det ID");
        } else {
            loan.updateInterestRate(newInterestRate, employee);
        }
        
        println("\nRäntan är ändrad till " + newInterestRate + "% för lån " + loanId + "\n");
        
    }
    
    /**
     * Writes a list of the history of changes to a loan
     */
    public static void printListOfRateChanges() {
        String loanId = getString("Mata in lånID: ");
        Customer customer = findCustomer();
        
        Loan loan = searchForLoanInListOfLoan(customer, loanId);
        if (loan == null) {
            println("Finns inget lån med det ID");
        } else {
            for (int i = 0; i < loan.getLoanHistory().size(); i++) {
                print(loan.getLoanHistory().get(i).getListOfChanges() + "\n");
            }
        }
        
    }
    
    /**
     * Writes a list of all loans for a customer
     */
    public static void allLoansForACustomer() {
        Customer customer = findCustomer();
        println(customer.getName() + " har totalt " + customer.getLoanList().size() + " lån hos banken");
        for (int i = 0; i < customer.getLoanList().size(); i++) {
            Loan currentLoan = customer.getLoanList().get(i);
            println(
                    "\nLån: " + currentLoan.getLoanID() +
                            "\nTotal skuld: " + currentLoan.getDebt() +
                            "\nRänta på lån: " + currentLoan.getInterestRate() +
                            "\nAnsvarig på banken: " + currentLoan.getManager().getName() +
                            "\n");
        }
    }
    
    public static boolean exitMenu() {
        String input = getString("Är du säker på att du vill avsluta? (j/n) ");
        if (input.equalsIgnoreCase("j")) {
            println("Tack för besöket! Välkommen åter!");
            return false;
        } else {
            println("Stanna så länge du vill!");
            return true;
        }
    }
    
    public static Customer findCustomer() {
        while (true) {
            try {
                return getCustomer(getString("Ange kundens personnummer: "));
            } catch (CustomerNotFoundException e) {
                println(e.getMessage());
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
                return getEmployee(getString("Ange ditt personnummer: "));
            } catch (EmployeeNotFoundException e) {
                println(e.getMessage());
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
    
    
    public static void print(String output) {
        System.out.print(output);
    }
    
    public static void println(String output) {
        System.out.println(output);
    }
    
    public static String getString(String question) {
        String response = null;
        while (response == null || response.isEmpty()) {
            print(question);
            response = in.nextLine();
        }
        return response;
    }
    
    public static double getDouble(String question) {
        while (true) {
            try {
                print(question);
                return Double.parseDouble(in.nextLine().replace(',', '.'));
            } catch (NumberFormatException e) {
                println("Ogiltigt värde! ");
            }
        }
    }
    
    public static int getInt(String question) {
        while (true) {
            try {
                print(question);
                return Integer.parseInt(in.nextLine());
            } catch (NumberFormatException e) {
                println("Ogiltigt värde! ");
            }
        }
    }
    
    /**
     * Search for a loan in a list of loans
     *
     * @param customer Customer of the the loan
     * @param loanId   ID number of the loan
     *
     * @return Loan
     */
    public static Loan searchForLoanInListOfLoan(Customer customer, String loanId) {
        for (Loan l : customer.getLoanList()) {
            if (l.getLoanID() == Integer.parseInt(loanId)) {
                return l;
            }
        }
        return null;
    }
}

