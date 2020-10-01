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

    
    public void addAccount(Account account) {
        accountList.add(account);
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
        String customerID = "";
        for (int i = 0; i < 6; i++) {
            customerID += random.nextInt(9);
        }
        return customerID;
    }

    public Loan getLatestLoan(){
        return loanList.get(loanList.size()-1);
    }


    public List<Loan> getLoanList(){
        return loanList;
    }

    public String  getCustomerId() {
        return customerId;
    }

    public static Account getAccount (Customer customer, String accountId){
        for (Account a : customer.accountList) {
            if (a.getAccountId().equals(accountId))
            return a;
        }

        return null;
    }
}
