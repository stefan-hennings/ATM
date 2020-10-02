import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Customer extends Person implements Serializable {
    private final List<Account> accountList = new ArrayList<>();
    private final List<Loan> loanList = new ArrayList<>();
    private final String customerId;
    
    public Customer(String name, String personalId) {
        super(name, personalId);
        this.customerId = makeRandomCustomerId();
    }
    
    public void applyForLoan() {
        double loanAmount = Bank.getDouble("Ange lånets storlek: ");
        double interest = Bank.getDouble("Ange lånets räntesats: ");
        
        grantLoan(loanAmount, Bank.employee, interest);
        Bank.println("Lån på " + getLatestLoan().getDebt() + " skapat till " + getName());
    }
    
    /**
     * Method that changes the value of the loan rate
     */
    public void changeInterestRateOnLoan() {
        Loan loan = findLoan();
        double newInterestRate = Bank.getDouble("Mata in nya räntan: ");
        loan.updateInterestRate(newInterestRate, Bank.employee);
        Bank.println("\nRäntan är ändrad till " + newInterestRate + "% för lån " + loan.getLoanID() + "\n");
    }
    
    /**
     * Writes a list of the history of changes to a loan
     */
    public void printListOfInterestRateChanges() {
        Loan loan = findLoan();
        for (InterestHistory currentLoan : loan.getLoanHistory()) {
            Bank.println(currentLoan.getListOfChanges());
        }
    }
    
    public Loan findLoan() {
        while (true) {
            try {
                return getLoanFromList(Bank.getInt("Mata in lånID: "));
            } catch (ObjectNotFoundException e) {
                Bank.println(e.getMessage());
            }
        }
    }
    
    /**
     * Search for a loan in a list of loans
     *
     * @param loanId ID number of the loan
     *
     * @return Loan
     */
    public Loan getLoanFromList(int loanId) {
        for (Loan loan : loanList) {
            if (loan.getLoanID() == loanId) {
                return loan;
            }
        }
        throw new ObjectNotFoundException("Den här kunden har inget lån med det ID numret. ");
    }
    
    /**
     * Writes a list of all loans for a customer
     */
    public void printAllLoans() {
        Bank.println(getName() + " har totalt " + loanList.size() + " lån hos banken");
        for (Loan currentLoan : loanList) {
            Bank.println("\nLån: " + currentLoan.getLoanID() +
                    "\nTotal skuld: " + currentLoan.getDebt() +
                    "\nRänta på lån: " + currentLoan.getInterestRate() +
                    "\nAnsvarig på banken: " + currentLoan.getManager().getName() + "\n");
        }
    }
    
    public void repayLoan() {
        Loan loan = findLoan();
        loan.repay(Bank.getDouble("Hur mycket ska betalas tillbaka? "));
    }
    
    public void createAccount() {
        double depositAmount = 0;
        do {
            depositAmount = Bank.getDouble("Ange belopp att sätta in: ");
        } while (depositAmount < 0);
        addAccount(depositAmount, Bank.employee);
        Bank.println("Nytt konto skapat med " + accountList.get(accountList.size() - 1).getAccountBalance());
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
        double changeInterest = Bank.getDouble("Ange den nya räntan: ");
        account.changeInterestRate(Bank.employee, changeInterest);
    }
    
    public void viewAllAccounts() {
        for (Account account : accountList) {
            Bank.println("" + account);
        }
    }
    
    public void addAccount(double accountBalance, Employee employee) {
        accountList.add(new Account(accountBalance, accountList.size() + 1, employee));
    }
    
    /**
     * Generate random numbers and put them in to a String
     *
     * @return String of numbers
     */
    private String makeRandomCustomerId() {
        Random random = new Random();
        StringBuilder customerID = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            customerID.append(random.nextInt(9));
        }
        return customerID.toString();
    }
    
    public Loan getLatestLoan() {
        return loanList.get(loanList.size() - 1);
    }
    
    public void grantLoan(double loanAmount, Employee employee, double interestRate) {
        loanList.add(new Loan(loanAmount, employee, interestRate, loanList.size() + 1));
    }
    
    public List<Loan> getLoanList() {
        return loanList;
    }
    
    public String getCustomerId() {
        return customerId;
    }
    
    public Account findAccount() {
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
    
    public Account getAccount(int accountNumber) {
        for (Account account : accountList) {
            if (account.getAccountNumber() == accountNumber) {
                return account;
            }
        }
        throw new ObjectNotFoundException("Kontot finns inte. ");
    }
}
