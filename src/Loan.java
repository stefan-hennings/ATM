import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Loan implements Serializable {
    private final int loanID;
    private double debt;
    private double startDebt;
    private final List<InterestHistory> loanHistory = new ArrayList<>();
    private final List<DebtHistory> debtHistory = new ArrayList<>();

    public Loan(double debt, Employee manager, double interestRate, int loanID) {
        this.startDebt = debt;
        this.debt = debt;
        this.loanID = loanID;
        loanHistory.add(new InterestHistory(manager, interestRate));
    }

    public int getLoanID() {
        return loanID;
    }
    
    public void repay(double amountPaid) {
        this.debt = debt - amountPaid;
        debtHistory.add(new DebtHistory(debt, amountPaid));
        Bank.serialize();
    }

    public double getStartDebt() {
        return startDebt;
    }

    public double getDebt() {
        return debt;
    }

    public void updateInterestRate(double interestRate) {
        loanHistory.add(new InterestHistory(Bank.activeEmployee, interestRate));
        Bank.serialize();
    }
    
    public double getInterestRate() {
        return loanHistory.get(loanHistory.size() - 1).getInterestRate();
    }
    
    public Employee getManager() {
        return loanHistory.get(loanHistory.size() - 1).getManager();
    }

    public List<InterestHistory> getLoanHistory() {
        return loanHistory;
    }
    public List<DebtHistory> getDeptHistory() {
        return debtHistory;
    }
}
