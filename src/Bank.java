import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Bank {
    static Scanner in = new Scanner(System.in);

    private static List<Customer> customerList = new ArrayList<>();
    private static List<Employee> employeeList = new LinkedList<>();


    public static boolean welcomeMenu(){
        String input;
        System.out.println("\nVälj vad du vill göra:\n\n" +
                "1. Lägg till ny kund\n" +
                "2. Anställ någon\n" +
                "3. Öppna konto\n" +
                "4. Öppna lån\n" +
                "5. Avsluta");

        input = in.nextLine();

        switch (input){
            case "1" -> {
                 return createCustomer();
            }
            case "2" -> {
                return createEmployee();

            }

            case "3" -> {
                return createAccount();

            }
            case "4" -> {
               return createLoan();

            }

            case "5"-> {
                return !exitMenu();
            }

            default -> {
                System.out.println("Ange ett giltigt val! (1-5)");
                return true;
            }
        }

    }

    public static boolean createCustomer(){ //TODO: Felhantering, inga duplicates.

        String name = null;
        String personalNumber = null;

        while(name==null){
        System.out.print("Mata in kundens namn: ");
        name = in.nextLine();
        }

        while (personalNumber == null) {
            System.out.print("Mata in kundens personnummer: ");
            personalNumber = in.nextLine();
        }
        customerList.add(new Customer(name, personalNumber));
        return true;

    }

    public static void testCode(){
        customerList.add(new Customer("Oscar", "x"));
        customerList.add(new Customer("Patrik", "x"));
        employeeList.add(new Employee("Stefan", "xx", 60000));
        employeeList.add(new Employee("Julia", "x", 25000));

    }
    public static boolean createEmployee(){
        String name = null;
        String personalNumber = null;
        String salaryString = null;
        double salary;

        while(name==null){
            System.out.print("Mata in den anställdas namn: ");
            name = in.nextLine();
        }

        while (personalNumber == null) {
            System.out.print("Mata in den anställdas personnummer: ");
            personalNumber = in.nextLine();
        }

        while (salaryString == null || !tryParse(salaryString)) {
            System.out.print("Mata in den anställdas lön: ");
            salaryString = in.nextLine();
            salaryString = salaryString.replace(',', '.');
        }
        salary = Double.parseDouble(salaryString);

        employeeList.add(new Employee(name, personalNumber, salary));
        return true;

    }
    public static boolean createAccount(){
        String  customerPersonalNumber=null;
        String customerName=null;
        String insertAmountString=null;
        double insertAmount;

        while(customerName==null) {
            System.out.print("Ange kundens namn: ");
            customerName = in.nextLine();
        }
        while (customerPersonalNumber==null) {
            System.out.print("Ange kundens personnummer: ");
            customerPersonalNumber = in.nextLine();
        }
        while (insertAmountString==null || !tryParse(insertAmountString)) {
            System.out.print("Ange belopp att sätta in: ");
            insertAmountString = in.nextLine();
        }
        insertAmount = Double.parseDouble(insertAmountString);
        Customer c = getCustomer(customerName, customerPersonalNumber);

        c.addAccount(new Account(insertAmount, c));
        return true;
    }

    public static boolean createLoan(){

        String  customerPersonalNumber=null;
        String customerName=null;
        String employeePersonalNumber=null;
        String interestString=null;
        String loanAmountString=null;
        double interest;
        double loanAmount;

        while (employeePersonalNumber==null) {
            System.out.print("Ange ditt personnummer: ");
            employeePersonalNumber = in.nextLine();
        }
        while(customerName==null) {
            System.out.print("Ange kundens namn: ");
            customerName = in.nextLine();
        }
        while (customerPersonalNumber==null) {
            System.out.print("Ange kundens personnummer: ");
            customerPersonalNumber = in.nextLine();
        }
        while (loanAmountString==null || !tryParse(loanAmountString)) {
            System.out.print("Ange lånets storlek: ");
            loanAmountString = in.nextLine();
        }
        loanAmount= Double.parseDouble(loanAmountString);
        while (interestString==null || !tryParse(interestString)) {
            System.out.print("Ange lånets räntesats: ");
            interestString = in.nextLine();
        }
        interest = Double.parseDouble(interestString);
        Customer c = getCustomer(customerName, customerPersonalNumber);
        Employee e = getEmployee(employeePersonalNumber);
        Loan loan = new Loan(loanAmount, c, e, interest );
        assert c != null;
        c.addLoan(loan);
        System.out.println("\nLån på " + c.getLoan().getDebt() +" skapat till " + c.getName());
        return true;
    }

    public static boolean exitMenu(){
        System.out.print("Är du säker på att du vill avsluta? (j/n)  ");
        String input = in.nextLine();
        if(input.equalsIgnoreCase("j")){
            System.out.println("Tack för besöket! Välkommen åter!");
            return true;
        } else {
            System.out.println("Stanna så länge du vill!\n");
            return false;
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

    public static boolean tryParse(String input){
        try{
            double parsed = Double.parseDouble(input);
            return true;
        } catch (Exception e){
            System.out.println("\nLönen måste bestå av siffror!!!");
            return false;
        }
    }

}
