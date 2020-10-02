import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Bank implements Serializable {
    private static List<Customer> customerList = new ArrayList<>();
    private static List<Employee> employeeList = new LinkedList<>();
//    private static final List<Employee> employeeList = new LinkedList<>();
    static Employee employee;
    static Scanner in = new Scanner(System.in);
    
    public static void serialize() {
        try {
            FileOutputStream fileOut = new FileOutputStream("employees.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(employeeList);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in employee.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
        try {
            FileOutputStream fileOut = new FileOutputStream("customers.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(customerList);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in customers.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
    
    public static void deSerialize() {
        try {
            FileInputStream fileIn = new FileInputStream("employees.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            employeeList = (List<Employee>) in.readObject();
            in.close();
            fileIn.close();
        } catch (Exception e) {
            System.out.println("Employee list not found");
        }
        try {
            FileInputStream fileIn = new FileInputStream("customers.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            customerList = (List<Customer>) in.readObject();
            in.close();
            fileIn.close();
        } catch (Exception e) {
            System.out.println("Customer list not found");
        }
    }
    
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
    
    
    public static void welcomeMenu() {
        String input;
        if (employeeList.isEmpty()) {
            println("Det finns inga anställda! Anställ någon NU! ");
            createEmployee();
        }
        employee = findEmployee();
        println("Välkommen " + employee.getName() + "!");
        
        boolean running = true;
        while (running) {
            println("\nVälj vad du vill göra:\n" +
                    "1. Lägg till ny kund\n" +
                    "2. Anställ någon\n" +
                    "3. Konto\n" +
                    "4. Lån\n" +
                    "5. Byt inloggning\n" +
                    "6. Avsluta");
            
            input = in.nextLine();
            
            switch (input) {
                case "1" -> createCustomer();
                case "2" -> createEmployee();
                case "3" -> accountMenu();
                case "4" -> loanMenu();
                case "5" -> {
                    employee = findEmployee();
                    println("Välkommen " + employee.getName() + "!");
                }
                case "6" -> running = exitMenu();
                case "7" -> serialize();
                default -> println("Ange ett giltigt val! (1-6)");
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
        println(customer.getName() + " har valts. ");
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
                case "7" -> {
                    customer = findCustomer();
                    println(customer.getName() + " har valts. ");
                }
                case "8" -> {
                    return;
                }
                default -> println("Ange ett giltigt val! (1-8)");
            }
        }
    }
    
    public static void loanMenu() {
        Customer customer = findCustomer();
        println(customer.getName() + " har valts. ");
        String input;
        while (true) {
            println("\nVälj vad du vill göra:\n" +
                    "1. Ansök om nytt lån\n" +
                    "2. Ändra räntan på ett befintligt lån lån\n" +
                    "3. Skriva ut lista med ändringar för ett lån\n" +
                    "4. Skriva ut lista för kundens alla lån\n" +
                    "5. Betala tillbaka lån\n" +
                    "6. Byt kund\n" +
                    "7. Gå till huvudmenyn");
            
            input = in.nextLine();
            
            switch (input) {
                case "1" -> customer.applyForLoan();
                case "2" -> customer.changeInterestRateOnLoan();
                case "3" -> customer.printListOfInterestRateChanges();
                case "4" -> customer.printAllLoans();
                case "5" -> customer.repayLoan();
                case "6" -> {
                    customer = findCustomer();
                    println(customer.getName() + " har valts. ");
                }
                case "7" -> {
                    return;
                }
                default -> println("Ange ett giltigt val! (1-7)");
            }
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
        if (customerList.isEmpty()) {
            println("Det finns inga kunder! Skapa en nu.");
            createCustomer();
        }
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
    
}

