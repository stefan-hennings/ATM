public class Main {
    public static void main(String[] args) {
        Bank.createCustomer();
        Bank.createTestUsers();
        boolean running= true;
        while(running)
        running = Bank.welcomeMenu();


    }

}
