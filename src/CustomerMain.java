/**
 * Created by Julia Wigenstedt
 * Date: 2020-10-02
 * Time: 16:58
 * Project: ATM
 * Copyright: MIT
 */
public class CustomerMain {
    
    public void welcomeMenu() {
        
        Bank.deSerialize();
        boolean running = true;
        while (running) {
            Customer customer = Utility.customerSearchForTheirUserAccount();
            Utility.println(String.format("\nVälkommen %s!", customer.getName()));
            if (customer.getAccountList().isEmpty()) {
                Utility.println("\nDu har inte öppnat något konto hos oss.\n" +
                        "Besök vårt närmaste bankkontor för att öppna ett konto.");
                return;
            } else {
                String input = Utility.getString("\nVad vill du göra?\n\n" +
                        "1. Uttag\n" +
                        "2. Insättning\n" +
                        "3. Visa saldo\n" +
                        "4. Avsluta\n");
                
                switch (input) {
                    case "1" -> customer.accountWithdraw();
                    case "2" -> customer.accountDeposit();
                    case "3" -> customer.viewAllAccounts();
                    // TODO: 02-Oct-20 Require employee authorization to shut down the machine
                    case "4" -> running = Bank.exitMenu();
                    default -> Utility.println("Var god ange ett giltigt val (1-4)\n");
                }
            }
        }
    }
    
    public static void main(String[] args) {
        CustomerMain run = new CustomerMain();
        run.welcomeMenu();
    }
}
