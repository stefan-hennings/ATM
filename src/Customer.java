import java.util.ArrayList;
import java.util.List;

public class Customer extends Person {
    private List<Account> accountList = new ArrayList<>();
    private List<Loan> loanList = new ArrayList<>();
    
    public Customer(String name, String personalId) {
        super(name, personalId);
    }
    
    public void addAccount(Account account) {
        accountList.add(account);
    }
    
    public void addLoan(Loan loan) {
        loanList.add(loan);
    }
}
