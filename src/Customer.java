import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Customer extends Person {
    private final List<Account> accountList = new ArrayList<>();
    private final List<Loan> loanList = new ArrayList<>();
    private final String customerId;
    
    public Customer(String name, String personalId) {
        super(name, personalId);
        this.customerId = makeRandomCustomerId();
    }
    
    public void createAccount() {
        Employee employee = Bank.findEmployee();
        double depositAmount = Bank.getDouble("Ange belopp att sätta in: ");
        addAccount(depositAmount, employee);
        Bank.println("Nytt konto skapat med " + accountList.get(accountList.size()-1).getAccountBalance());
    }
    
    public void accountDeposit() {
        Account account = findAccount();
        double changeBalance = Bank.getDouble("Ange belopp du vill ta sätta in: ");
        account.changeBalance(changeBalance);
        Bank.println("Kontobalans är nu " + account.getAccountBalance());
    }
    
    public void accountWithdraw() {
        Account account = findAccount();
        double changeBalance = -1 * Bank.getDouble("Ange belopp du vill ta ut: ");
        try {
            account.changeBalance(changeBalance);
        } catch (IllegalArgumentException e) {
            Bank.println(e.getMessage());
        }
        Bank.println("Kontobalans är nu " + account.getAccountBalance());
    }
    
    public void viewAccountBalance() {
        Account account = findAccount();
        Bank.println("Saldo: " + account.getAccountBalance());
    }
    
    public void changeAccountInterestRate() {
        Account account = findAccount();
        Employee employee = Bank.findEmployee();
        double changeInterest = Bank.getDouble("Ange den nya räntan: ");
        account.changeInterestRate(employee, changeInterest);
    }
    
    public void viewAllAccounts() {
        for (Account account : accountList) {
            Bank.println("" + account);
        }
    }
    
    public void addAccount(double accountBalance, Employee employee) {
        accountList.add(new Account(accountBalance, accountList.size()+1, employee));
    }

    /**
     * Generate random numbers and put them in to a String
     * @return String of numbers
     */
    private String makeRandomCustomerId(){
        Random random = new Random();
        StringBuilder customerID = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            customerID.append(random.nextInt(9));
        }
        return customerID.toString();
    }

    public Loan getLatestLoan(){
        return loanList.get(loanList.size()-1);
    }
    
    public void grantLoan(double loanAmount, Employee employee, double interestRate){
        loanList.add(new Loan(loanAmount, employee, interestRate, loanList.size()+1));
    }
    
    public List<Loan> getLoanList(){
        return loanList;
    }

    public String  getCustomerId() {
        return customerId;
    }
    
    public Account findAccount(){
        while (true) {
            try {
                return getAccount(Bank.getInt("Ange kontonummer: "));
            } catch (CustomerNotFoundException e) {
                Bank.println(e.getMessage());
            }
        }
    }
    
    public List<Account> getAccountList() {
        return accountList;
    }
    
    public Account getAccount (int accountNumber){
        for (Account account : accountList) {
            if (account.getAccountNumber() == accountNumber)
            return account;
        }
        throw new ObjectNotFoundException("Kontot finns inte. ");
    }
}
