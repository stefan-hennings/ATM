import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Loan {
    private int loanID;
    private double interestRate;
    private double debt;
    private Employee manager;
    private List<LoanStatus> loanHistory = new ArrayList<>();

    public Loan(double debt, Employee manager, double interestRate, int loanID) {
        this.debt = debt;
        this.interestRate = interestRate;
        this.manager = manager;
        this.loanID = loanID;
        loanHistory.add(new LoanStatus(manager, interestRate));
    }

    public int getLoanID() {
        return loanID;
    }

    public void setLoanID(int loanID) {
        this.loanID = loanID;
    }
    
    public void pay(double amountPaid) {
        debt -= amountPaid;
    }
    
    public double getDebt() {
        return debt;
    }
    
    public void updateInterestRate(double interestRate, Employee manager) {
        loanHistory.add(new LoanStatus(manager, interestRate));
    }
    
    public double getInterestRate() {
        return loanHistory.get(loanHistory.size()-1).getInterestRate();
    }
    
    public Employee getManager() {
        return loanHistory.get(loanHistory.size()-1).getManager();
    }

    public List<LoanStatus> getLoanHistory() {
        return loanHistory;
    }

}
