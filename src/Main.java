public class Main {
    public static void main(String[] args) {
        Bank.createCustomer();
        Bank.testCode();
        boolean running= true;
        while(running)
        running = Bank.welcomeMenu();


    }

}
