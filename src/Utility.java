public class Utility {
    public static Customer findCustomer() {
        if (Bank.customerList.isEmpty()) {
            println("Det finns inga kunder! Skapa en nu.");
            Bank.createCustomer();
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
        for (Customer customer : Bank.customerList) {
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
        for (Employee employee : Bank.employeeList) {
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
            response = Bank.in.nextLine();
        }
        return response;
    }
    
    public static double getDouble(String question) {
        while (true) {
            try {
                print(question);
                return Double.parseDouble(Bank.in.nextLine().replace(',', '.'));
            } catch (NumberFormatException e) {
                println("Ogiltigt värde! ");
            }
        }
    }
    
    public static int getInt(String question) {
        while (true) {
            try {
                print(question);
                return Integer.parseInt(Bank.in.nextLine());
            } catch (NumberFormatException e) {
                println("Ogiltigt värde! ");
            }
        }
    }
}
