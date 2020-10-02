/**
 * Created by Julia Wigenstedt
 * Date: 2020-10-02
 * Time: 16:58
 * Project: ATM
 * Copyright: MIT
 */
public class CustomerMain {

    public void welcomeMenu(){

        Bank.deSerialize();
        Customer customer = Utility.customerSearchForTheirUserAccount();
        boolean running = true;
        Utility.println(String.format("\nVälkommen %s!", customer.getName()));
        while (running) {
            if (customer.getAccountList().isEmpty()) {
                Utility.println(String.format("\nDu har inte öppnat något konto hos oss.\n" +
                        "Besök vårt närmaste bankkontor för att öppna ett konto.", customer.getName()));
                running = false;
            } else {
                String input = Utility.getString(String.format("\nVad vill du göra?\n\n" +
                        "1. Uttag\n" +
                        "2. Insättning\n" +
                        "3. Visa saldo\n" +
                        "4. Avsluta\n", customer.getName()));

                switch (input) {
                    case "1" -> {
                        try {
                            customer.accountWithdraw();
                        } catch (Exception e) {
                            Utility.println("\nKontot finns inte.");
                        }
                    }
                    case "2" -> {
                        try {
                            customer.accountDeposit();
                        } catch (Exception e){
                            Utility.println("\nKontot finns inte.");
                        }
                    }
                    case "3" -> {
                        customer.viewAllAccounts();
                    }
                    case "4" -> {
                        boolean isQuitting = Bank.exitMenu();
                        if(!isQuitting){
                            Utility.println("\n");
                            Bank.serialize();
                        }
                        running = isQuitting;
                    }
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
