import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Bank {
    static Scanner in = new Scanner(System.in);

    public static void testCode(){
        Customer oscar = new Customer("Oscar", "xxx");
        Customer patrik = new Customer("Patrik", "x");
        customerList.add(oscar);
        customerList.add(patrik);
        Employee stefan = new Employee("Stefan", "xx", 60000);
        Employee julia = new Employee("Julia", "x", 25000);
        employeeList.add(stefan);
        employeeList.add(julia);

        Loan loan1 = new Loan(25000,oscar, stefan,2, 1);
        Loan loan2 = new Loan(35000, oscar, stefan, 3,2);
        Loan loan3 = new Loan(10000, patrik, julia, 2, 1);
        Loan loan4 = new Loan(15000, patrik, julia, 4, 2);

        oscar.addLoan(loan1);
        oscar.addLoan(loan2);
    }

     static List<Customer> customerList = new ArrayList<>();
     static List<Employee> employeeList = new LinkedList<>();


    public static void welcomeMenu(){
        String input;
        System.out.println("\nVälj vad du vill göra:\n" +
                "1. Lägg till ny kund\n" +
                "2. Anställ någon\n" +
                "3. Öppna konto\n" +
                "4. Öppna lån\n" +
                "5. Hantera lån\n" +
                "6. Avsluta");

        input = in.nextLine();

        switch (input){
            case "1" -> createCustomer();
            case "2" -> createEmployee();
            case "3" -> createAccount();
            case "4" -> createLoan();
            case "5" -> handleLoan();
            case "6" -> exitMenu();
            default -> System.out.println("Ange ett giltigt val! (1-5)");
        }
        welcomeMenu();
    }

    public static void handleLoan(){
        String input;
        System.out.println("Vad vill du göra?\n" +
                "1. Ändra räntan på lån\n" +
                "2. Skriva ut lista med ändringar för ett lån\n" +
                "3. Gå till huvudmenyn");

        input = in.nextLine();

        switch (input){
            case "1" -> changeInterestRateOnLoan();
            case "2" -> printListOfRateChanges();
            case "3" -> welcomeMenu();
            default -> System.out.println("Ange ett giltigt val! (1-3)");
        }
        handleLoan();
    }

    public static void createCustomer() { //TODO: Felhantering, inga duplicates.

        String name = getString("Mata in kundens namn: ");

        String personalNumber = getString("Mata in kundens personnummer: ");
        customerList.add(new Customer(name, personalNumber));
        System.out.println("Ny kund skapad");

    }

    public static void createEmployee(){

        String name = getString("Mata in den anställdas namn: ");
        String personalNumber = getString("Mata in den anställdas personnummer: ");
        double salary = getDouble("Mata in den anställdas lön: ");

        employeeList.add(new Employee(name, personalNumber, salary));
        System.out.println("Ny anställd skapad");

    }

    public static void createAccount(){
        String customerName = getString("Ange kundens namn: ");
        String customerPersonalNumber = getString("Ange kundens personnummer: ");

        double insertAmount = getDouble("Ange belopp att sätta in: ");

        Customer c = getCustomer(customerName, customerPersonalNumber);
        //TODO: fixa nullpointer på addAccount
        c.addAccount(new Account(insertAmount, c));
        System.out.println("Nytt konto skapat");
    }

    public static void createLoan() {
        String employeePersonalNumber = getString("Ange ditt personnummer: ");
        String customerName = getString("Ange kundens namn: ");
        String customerPersonalNumber = getString("Ange kundens personnummer: ");

        double loanAmount = getDouble("Ange lånets storlek: ");
        double interest = getDouble("Ange lånets räntesats: ");

        Customer c = getCustomer(customerName, customerPersonalNumber);
        Employee e = getEmployee(employeePersonalNumber);
        Loan loan = new Loan(loanAmount, c, e, interest );
        assert c != null;
        c.addLoan(loan);
        System.out.println("Lån på " + c.getLoan().getDebt() +" skapat till " + c.getName());
        welcomeMenu();
    }

    public static void exitMenu(){
        String input = getString("Är du säker på att du vill avsluta? (j/n)");
        if(input.equalsIgnoreCase("j")){
            System.out.println("Tack för besöket! Välkommen åter!");
            System.exit(0);
        } else {
            System.out.println("Stanna så länge du vill!");
            welcomeMenu();
        }
    }

    public static Customer getCustomer(String name, String personalNumber){
        for(var c: customerList){
            if(c.getName().equalsIgnoreCase(name.trim()) && c.getPersonalId().equalsIgnoreCase(personalNumber.trim())){
                return c;
            }
        }
        return null;
    }

    public static Employee getEmployee(String personalNumber){
        for (var e: employeeList){
            if(e.getPersonalId().equalsIgnoreCase(personalNumber)){
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
     * Method that changes the value of the loan rate
     */
    public static void changeInterestRateOnLoan(){
        String name = getString("Mata in lånetagarens namn: ");
        String perNum = getString("Mata in lånepersonnummer: ");
        String loanId = getString("Mata in lånId: ");
        String employeeId = getString("Mata in anställdast persnummer: ");
        String newInterestString = getString("Mata in nya räntan: ");
        int newInterestRate;

        newInterestRate = Integer.parseInt(newInterestString);
        Employee e = getEmployee(employeeId);
        Customer c = getCustomer(name, perNum);
        if (c != null) {
            Loan l = searchForLoanInListOfLoan(c, loanId);
            if (l == null){
                System.out.println("Finns inget lån med det ID");
            }else {
                l.updateInterestRate(newInterestRate, e);
            }
        }
        System.out.println("\nRäntan är ändrad till " + newInterestString + "% för lån " + loanId + "\n");

    }

    /**
     * Search for a loan in a list of loans
     * @param c Customer of the the loan
     * @param loanId ID number of the loan
     * @return Loan
     */
    public static Loan searchForLoanInListOfLoan(Customer c, String loanId) {
        for (Loan l: c.getLoanList()){
            if (l.getLoanID() == Integer.parseInt(loanId))
                return l;
        }
        return null;

    }

    /**
     * Writes a list of the history of changes to a loan
     */
    public static void printListOfRateChanges(){
        String loan = getString("Mata in lånID: ");
        String customer = getString("Mata in lånetagare: ");
        String personalNumber = getString("Mata in lånetagarens personnummer: ");

        Customer c = getCustomer(customer,personalNumber);

        if (c != null) {
            Loan l = searchForLoanInListOfLoan(c, loan);
            if (l == null){
                System.out.println("Finns inget lån med det ID");
            }else {
                for (int i = 0; i < l.getLoanHistory().size() ; i++) {
                    System.out.print(l.getLoanHistory().get(i).getListOfChanges() + "\n");
                }

            }
        }

    }
}
