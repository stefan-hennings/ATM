import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Loan implements Serializable {
    private final int loanID;
    private double debt;
    private final List<InterestHistory> loanHistory = new ArrayList<>();

    public Loan(double debt, Employee manager, double interestRate, int loanID) {
        this.debt = debt;
        this.loanID = loanID;
        loanHistory.add(new InterestHistory(manager, interestRate));
    }

    public int getLoanID() {
        return loanID;
    }
    
    public void repay(double amountPaid) {
        debt -= amountPaid;
    }
    
    public double getDebt() {
        return debt;
    }
    
    public void updateInterestRate(double interestRate, Employee manager) {
        loanHistory.add(new InterestHistory(manager, interestRate));
    }
    
    public double getInterestRate() {
        return loanHistory.get(loanHistory.size()-1).getInterestRate();
    }
    
    public Employee getManager() {
        return loanHistory.get(loanHistory.size()-1).getManager();
    }

    public List<InterestHistory> getLoanHistory() {
        return loanHistory;
    }

}
