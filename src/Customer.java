import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Customer extends Person {
    private List<Account> accountList = new ArrayList<>();
    private List<Loan> loanList = new ArrayList<>();
    private final UUID customerId;
    
    public Customer(String name, String personalId) {
        super(name, personalId);
        this.customerId = UUID.randomUUID();
    }
    
    public void addAccount(Account account) {
        accountList.add(account);
    }
    
    public void addLoan(Loan loan) {
        loanList.add(loan);
    }

    public Loan getLoan(){
        return loanList.get(loanList.size()-1);
    }
}
