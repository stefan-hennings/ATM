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
            FileInputStream fileIn = new FileInputStream("formerEmployees.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            formerEmployeeList = (List<Employee>) in.readObject();
            in.close();
            fileIn.close();
        } catch (Exception e) {
            System.out.println("Former Employee list not found");
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
            Utility.println("\nVälj vad du vill göra:\n" +
                    "1. Lägg till ny kund\n" +
                    "2. Personal\n" +
                    "3. Konto\n" +
                    "4. Lån\n" +
                    "5. Byt inloggning\n" +
                    "6. Avsluta\n" +
                    "7. Spara alla ändringar");

            input = in.nextLine();

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

        });
        for (var e : employeeList) {
            Utility.println("Namn: " + e.getName() + " Personnr: " + e.getPersonalId() + " Lön: " + e.getSalary() + "kr");
        }*/
    }

    public static void deleteEmployee() {
        employee = Utility.findEmployee();
        formerEmployeeList.add(employee);
        employeeList.remove(employee);
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
            // TODO: 01-Oct-20 Reverse logic
            try {
                Utility.getCustomer(personalNumber);
                Utility.println("Kunden finns redan! ");
            } catch (CustomerNotFoundException e) {
                customerList.add(new Customer(name, personalNumber));
                break;
            }
        }

    }

    public static void createEmployee() {
        String name = Utility.getString("Mata in den anställdas namn: ");
        String personalNumber = Utility.getString("Mata in den anställdas personnummer: ");
        double salary = Utility.getDouble("Mata in den anställdas lön: ");

        employeeList.add(new Employee(name, personalNumber, salary));
        Utility.println("Ny anställd skapad");

    }

    private static void accountMenu() {
        String input;
        Customer customer = Utility.findCustomer();
        Utility.println(customer.getName() + " har valts. ");
        while (true) {
            Utility.println("\nVälj vad du vill göra:\n" +
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
            Utility.println("\nVälj vad du vill göra:\n" +
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
                    customer = Utility.findCustomer();
                    Utility.println(customer.getName() + " har valts. ");
                }
                case "7" -> {
                    return;
                }
                default -> Utility.println("Ange ett giltigt val! (1-7)");
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

