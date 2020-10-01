import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Customer extends Person {
    private List<Account> accountList = new ArrayList<>();
    private List<Loan> loanList = new ArrayList<>();
    private String customerId;
    
    public Customer(String name, String personalId) {
        super(name, personalId);
        this.customerId = makeRandomCustomerId();
    }
    //så man kan hårdkoda in customerID för vi ej har databas att spara de i än.
    public Customer(String name, String personalId, String customerId) {
        super(name, personalId);
        this.customerId = customerId;
    }

    
    public void addAccount(double accountBalance) {
        accountList.add(new Account(accountBalance, accountList.size()+1));
    }
    
    public void addLoan(Loan loan) {
        loanList.add(loan);
        loan.setLoanID(loanList.size());
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
