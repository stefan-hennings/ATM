import java.io.*;
import java.util.*;

public class Bank implements Serializable {
    static List<Customer> customerList = new ArrayList<>();
    static List<Employee> employeeList = new LinkedList<>();
    static List<Employee> formerEmployeeList = new LinkedList<>();
    static Employee employee;
    static Scanner in = new Scanner(System.in);
    
    public static void serialize() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("employees.ser"));
            out.writeObject(employeeList);
            out.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
        try {
            FileOutputStream fileOut = new FileOutputStream("formerEmployees.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(formerEmployeeList);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in formerEmployee.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("customers.ser"));
            out.writeObject(customerList);
            out.close();
            System.out.println("Files updated");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
    
    public static void deSerialize() {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("employees.ser"));
            employeeList = (List<Employee>) in.readObject();
            in.close();
        } catch (Exception e) {
            System.out.println("Employee list not found");
        }
        try {
            FileInputStream fileIn = new FileInputStream("formerEmployees.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            formerEmployeeList = (List<Employee>) in.readObject();
            in.close();
            fileIn.close();
        } catch (Exception e) {
            System.out.println("Former Employee list not found");
        }
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("customers.ser"));
            customerList = (List<Customer>) in.readObject();
            in.close();
        } catch (Exception e) {
            System.out.println("Customer list not found");
        }
    }
    
    public static void welcomeMenu() {
        String input;
        if (employeeList.isEmpty()) {
            Utility.println("Det finns inga anställda! Anställ någon NU! ");
            createEmployee();
        }
        employee = Utility.findEmployee();
        Utility.println("Välkommen " + employee.getName() + "!");
        
        boolean running = true;
        while (running) {
            Utility.println("\n\nVälj vad du vill göra:\n\n" +
                    "1. Lägg till ny kund\n" +
                    "2. Personal\n" +
                    "3. Konto\n" +
                    "4. Lån\n" +
                    "5. Byt inloggning\n" +
                    "6. Avsluta\n" +
                    "7. Spara alla ändringar");
            
            input = in.nextLine();
            // TODO: 02-Oct-20 use getString here?
            switch (input) {
                case "1" -> createCustomer();
                case "2" -> employeeMenu();
                case "3" -> accountMenu();
                case "4" -> loanMenu();
                case "5" -> {
                    employee = Utility.findEmployee();
                    Utility.println("Välkommen " + employee.getName() + "!");
                }
                case "6" -> running = exitMenu();
                // TODO: 02-Oct-20 Automate file updates
                case "7" -> serialize();
                default -> Utility.println("Ange ett giltigt val! (1-6)");
            }
        }
    }

    public static void employeeMenu() {
        String input;

        while (true) {
            Utility.println("\nVälj vad du vill göra:\n" +
                    "1. Lägg till en anställd\n" +
                    "2. Skriv ut lista på anställda\n" +
                    "3. Ta bort en anställd\n" +
                    "4. Skriv ut lista på föredetta anställda\n" +
                    "5. Återgå till huvudmenyn");

            input = in.nextLine();

            switch (input) {
                case "1" -> createEmployee();
                case "2" -> printListOfEmployee();
                case "3" -> deleteEmployee();
                case "4" -> printListOfFormerEmployee();
                case "5" -> {
                    return;
                }
                default -> Utility.println("Ange ett giltigt val! (1-5)");
            }
        }
    }

    public static void printListOfEmployee() {
        //TODO Sortera listan efter namn
        /*Collections.sort(employeeList, new Comparator() {

        });*/
        for (var e : employeeList) {
            Utility.println("Namn: " + e.getName() + " Personnr: " + e.getPersonalId() + " Lön: " + e.getSalary() + "kr");
        }
    }

    public static void deleteEmployee() {
        Employee employee = Utility.findEmployee();
        formerEmployeeList.add(employee);
        employeeList.remove(employee);
        serialize();
    }

    public static void printListOfFormerEmployee() {
        //TODO Sortera listan efter namn
   /* Collections.sort(employeeList, new Comparator<Employee>() {
        @Override
        public int compare(Employee o1, Employee o2) {
            return 0;
        }

    });*/
        for (var e : formerEmployeeList) {
            Utility.println("Namn: " + e.getName() + " Personnr: " + e.getPersonalId() + " Lön: " + e.getSalary() + "kr");
        }
    }

    public static void createCustomer() {
        while (true) {
            String name = Utility.getString("Mata in kundens namn: ");
            String personalNumber = Utility.getString("Mata in kundens personnummer: ");
            boolean isFound = false;
            for (Customer customer : Bank.customerList) {
                if (customer.getPersonalId().equalsIgnoreCase(personalNumber)) {
                    Utility.println("Kunden finns redan! ");
                    isFound = true;
                }
            }
            if (!isFound) {
                customerList.add(new Customer(name, personalNumber));
                serialize();
                break;
            } else {
                if (Utility.getString("Vill du försöka igen? (j/n) ").equalsIgnoreCase("n")) {
                    break;
                }
            }
        }
        
    }

    public static void createEmployee() {
        String name = Utility.getString("Mata in den anställdas namn: ");
        String personalNumber = Utility.getString("Mata in den anställdas personnummer: ");
        double salary = Utility.getDouble("Mata in den anställdas lön: ");

        employeeList.add(new Employee(name, personalNumber, salary));
        serialize();
        Utility.println("Ny anställd skapad");

    }

    private static void accountMenu() {
        String input;
        Customer customer = Utility.findCustomer();
        Utility.println(customer.getName() + " har valts. ");
        while (true) {
            Utility.println("\n\nVälj vad du vill göra:\n\n" +
                    "1. Öppna nytt konto\n" +
                    "2. Gör insättning\n" +
                    "3. Gör uttag\n" +
                    "4. Saldo\n" +
                    "5. Ändra ränta\n" +
                    "6. Visa alla konton\n" +
                    "7. Byt kund\n" +
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
                    customer = Utility.findCustomer();
                    Utility.println(customer.getName() + " har valts. ");
                }
                case "8" -> {
                    return;
                }
                default -> Utility.println("Ange ett giltigt val! (1-8)");
            }
        }
    }

    public static void loanMenu() {
        Customer customer = Utility.findCustomer();
        Utility.println(customer.getName() + " har valts. ");
        String input;
        while (true) {
            Utility.println("\n\nVälj vad du vill göra:\n\n" +
                    "1. Ansök om nytt lån\n" +
                    "2. Ändra räntan på ett befintligt lån lån\n" +
                    "3. Lista med ändringar av ränta för ett lån\n" +
                    "4. Lista för kundens alla lån\n" +
                    "5. Betala tillbaka lån\n" +
                    "6. Lista med skuld kvar på ett lån\n" +
                    "7. Byt kund\n" +
                    "8. Gå till huvudmenyn");
            
            input = in.nextLine();

            switch (input) {
                case "1" -> customer.applyForLoan();
                case "2" -> customer.changeInterestRateOnLoan();
                case "3" -> customer.printListOfInterestRateChanges();
                case "4" -> customer.printAllLoans();
                case "5" -> customer.repayLoan();
                case "6" -> customer.printListOfRepayLoanHistory();
                case "7" -> {
                    customer = Utility.findCustomer();
                    Utility.println(customer.getName() + " har valts. ");
                }
                case "8" -> {
                    return;
                }
                default -> Utility.println("Ange ett giltigt val! (1-8)");
            }
        }
    }
    
    
    public static boolean exitMenu() {
        String input = Utility.getString("Är du säker på att du vill avsluta? (j/n) ");
        if (input.equalsIgnoreCase("j")) {
            Utility.println("Tack för besöket! Välkommen åter!");
            return false;
        } else {
            Utility.println("Stanna så länge du vill!");
            return true;
        }
    }
    
    
}

